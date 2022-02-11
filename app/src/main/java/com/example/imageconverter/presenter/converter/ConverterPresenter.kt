package com.example.imageconverter.presenter.converter

import android.net.Uri
import com.example.imageconverter.data.converter.ConverterJpgToPng
import com.example.imageconverter.scheduler.Schedulers
import com.example.imageconverter.view.ConverterView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter

class ConverterPresenter(
    private val converter: ConverterJpgToPng,
    private val schedulers: Schedulers,
) : MvpPresenter<ConverterView>() {

    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun startConvertingPressed(uri: Uri) {
        viewState.showProgressBar()
        viewState.signWaitingShow()
        viewState.signGetStartedHide()
        viewState.btnAbortConvertEnabled()

        disposables +=
            converter
                .convert(uri)
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.main())
                .subscribe(
                    { t: Uri? ->
                        if (t != null) {
                            onConvertingSuccess(t)
                        }
                    },
                    { e: Throwable? ->
                        onConvertingError(e)
                    })
    }

    private fun onConvertingSuccess(uri: Uri) {
        viewState.showConvertedImage(uri)
        viewState.hideProgressBar()
        viewState.btnAbortConvertDisabled()
        viewState.signAbortConvertHide()
        viewState.signWaitingHide()
    }

    private fun onConvertingError(e: Throwable?) {
        viewState.showProgressBar()
        viewState.hideProgressBar()
        viewState.btnAbortConvertDisabled()
        viewState.signWaitingHide()
    }

    fun abortConvertImagePressed() {
        viewState.hideProgressBar()
        viewState.signWaitingHide()
        viewState.btnAbortConvertDisabled()
        viewState.signAbortConvertShow()
        disposables.clear()
    }

    fun originalImageSelected(imageUri: Uri) {
        viewState.showOriginImage(imageUri)
        viewState.btnStartConvertEnable()
        viewState.signAbortConvertHide()
        viewState.signGetStartedHide()
        viewState.signWaitingShow()
    }

    override fun onDestroy() {
        disposables.clear()
    }
}