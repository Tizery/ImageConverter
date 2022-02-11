package com.example.imageconverter.view

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.converteripg.R
import com.example.imageconverter.data.converter.ConverterJpgToPng
import com.example.converteripg.databinding.ViewConverterBinding
import com.example.imageconverter.presenter.converter.ConverterPresenter
import com.example.imageconverter.scheduler.SchedulersFactory
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ConverterFragment : MvpAppCompatFragment(), ConverterView {

    companion object {
        fun newInstance(): Fragment = ConverterFragment()
    }

    private val presenter by moxyPresenter {
        ConverterPresenter(
            converter = ConverterJpgToPng(requireContext()),
            schedulers = SchedulersFactory.create()
        )
    }

    private var _binding: ViewConverterBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ViewConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.converter_tittle)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            imageUri = data?.data
            imageUri?.let { presenter.originalImageSelected(it) }
        }
    }

    override fun init() {

        hideProgressBar()
        hideErrorBar()
        btnStartConvertDisabled()
        btnAbortConvertDisabled()
        signGetStartedShow()
        signWaitingShow()

        binding.btnImageSelection.setOnClickListener {

            val intent = Intent(ACTION_GET_CONTENT)
            intent.type = "image/*"

            startActivityForResult(intent, 1)
        }

        binding.btnStartConverting.setOnClickListener {
            imageUri?.let(presenter::startConvertingPressed)
        }

        binding.btnAbort.setOnClickListener {
            presenter.abortConvertImagePressed()
        }
    }

    override fun showProgressBar() {
        binding.progressBar2.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar2.visibility = View.GONE
    }

    override fun showOriginImage(uri: Uri) {
        binding.imgViewOriginalImg.setImageURI(uri)
    }

    override fun showConvertedImage(uri: Uri) {
        binding.imgViewConvertedImg.setImageURI(uri)
    }

    override fun btnStartConvertEnable() {
        binding.btnStartConverting.isEnabled = true
    }

    override fun btnStartConvertDisabled() {
        binding.btnStartConverting.isEnabled = false
    }

    override fun btnAbortConvertEnabled() {
        binding.btnAbort.isEnabled = true
    }

    override fun btnAbortConvertDisabled() {
        binding.btnAbort.isEnabled = false
    }

    override fun signAbortConvertShow() {
        binding.imgViewConvertedImg.setImageURI(null)
        binding.imgViewCancelSign.visibility = View.VISIBLE
    }

    override fun signAbortConvertHide() {
        binding.imgViewCancelSign.visibility = View.GONE
    }

    override fun signGetStartedShow() {
        binding.imgViewGetStartedSign.visibility = View.VISIBLE
    }

    override fun signGetStartedHide() {
        binding.imgViewGetStartedSign.visibility = View.GONE
    }

    override fun signWaitingShow() {
        binding.imgViewConvertedImg.setImageURI(null)
        binding.imgViewWaitingSign.visibility = View.VISIBLE
    }

    override fun signWaitingHide() {
        binding.imgViewWaitingSign.visibility = View.GONE
    }

    override fun hideErrorBar() {
        binding.imgViewErrorSign.visibility = View.GONE
    }

    override fun showError(error: Throwable) {
        Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
    }

}