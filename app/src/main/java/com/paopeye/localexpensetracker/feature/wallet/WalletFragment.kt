package com.paopeye.localexpensetracker.feature.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paopeye.localexpensetracker.feature.MainActivity
import com.paopeye.localexpensetracker.R
import com.skydoves.colorpickerview.ColorEnvelope
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

    private var btnAddState = false

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
        wallet_btn_add.setOnClickListener(){
            if(btnAddState){
                processBtnAddGone()
                btnAddState = false
            }else{
                processBtnAddVisible()
                btnAddState = true
            }
        }
    }

    private fun processBtnAddVisible(){
        wallet_cardview_add_account.visibility = View.VISIBLE
        wallet_btn_add.setImageResource(R.drawable.ic_baseline_remove_24)
    }

    private fun processBtnAddGone(){
        wallet_cardview_add_account.visibility = View.GONE
        wallet_btn_add.setImageResource(R.drawable.ic_baseline_add_24)
    }
    private fun setLayoutColor(envelope: ColorEnvelope) {
        wallet_relativeLayout.setBackgroundColor(envelope.color)
    }

    private fun setupEditText() {
        (activity as MainActivity).setupEditTextNumber(wallet_acc_balance,"Rp ")
    }
}