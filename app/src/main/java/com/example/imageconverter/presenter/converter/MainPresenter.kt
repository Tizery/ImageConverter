package com.example.imageconverter.presenter.converter

import com.example.imageconverter.navigation.ConverterScreen
import com.example.imageconverter.view.MainView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class MainPresenter(private val router: Router) :
    MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        router.newRootScreen(ConverterScreen.create())
    }

    fun backClicked() {
        router.exit()
    }
}