package com.example.imageconverter.view

import android.net.Uri
import com.example.imageconverter.ScreenView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ConverterView : ScreenView {

    fun init()

    fun showProgressBar()

    fun hideProgressBar()

    fun hideErrorBar()

    fun showOriginImage(uri: Uri)

    fun showConvertedImage(uri: Uri)

    fun btnStartConvertEnable()

    fun btnStartConvertDisabled()

    fun btnAbortConvertEnabled()

    fun btnAbortConvertDisabled()

    fun signAbortConvertShow()

    fun signAbortConvertHide()

    fun signGetStartedShow()

    fun signGetStartedHide()

    fun signWaitingShow()

    fun signWaitingHide()
}