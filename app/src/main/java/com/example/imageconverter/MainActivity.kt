package com.example.imageconverter

import android.os.Bundle
import com.example.converteripg.R
import com.example.converteripg.databinding.ActivityMainBinding
import com.example.imageconverter.presenter.converter.MainPresenter
import com.example.imageconverter.view.MainView
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private val navigator = AppNavigator(this@MainActivity, R.id.container)

    private val presenter by moxyPresenter {
        MainPresenter(App.instance.router)
    }

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        App.instance.navigatorHolder.removeNavigator()
        super.onPause()
    }
}