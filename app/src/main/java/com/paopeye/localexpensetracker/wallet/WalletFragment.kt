package com.paopeye.localexpensetracker.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paopeye.localexpensetracker.MainActivity
import com.paopeye.localexpensetracker.R
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.listeners.ColorListener
import kotlinx.android.synthetic.main.fragment_wallet.*
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import com.skydoves.colorpickerview.ColorPickerDialog





class WalletFragment :Fragment() {

    companion object {
        val TAG: String = WalletFragment::class.java.simpleName
        fun newInstance(): WalletFragment {
            val fragment = WalletFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        setupEditText()
        setupButton()
    }

    private fun setupButton() {
        wallet_btnPallete.setOnClickListener(){
            ColorPickerDialog.Builder(context)
                .setTitle("ColorPicker Dialog")
                .setPositiveButton(getString(R.string.select),
                    ColorEnvelopeListener { envelope, fromUser -> setLayoutColor(envelope) })
                .setNegativeButton(
                    getString(R.string.cancel)
                ) { dialogInterface, i -> dialogInterface.dismiss() }
                .attachAlphaSlideBar(true) // the default value is true.
                .attachBrightnessSlideBar(true) // the default value is true.
                .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
                .show()
        }
    }

    private fun setLayoutColor(envelope: ColorEnvelope) {
        wallet_relativeLayout.setBackgroundColor(envelope.color)
    }

    private fun setupEditText() {
        (activity as MainActivity).setupEditTextNumber(wallet_acc_balance,"Rp ")
    }
}