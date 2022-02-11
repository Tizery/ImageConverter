package com.example.imageconverter.navigation

import com.example.imageconverter.view.ConverterFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object ConverterScreen {

    fun create(): Screen {
        return FragmentScreen { ConverterFragment.newInstance() }
    }
}