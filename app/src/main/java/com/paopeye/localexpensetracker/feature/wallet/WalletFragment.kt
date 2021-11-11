package com.paopeye.localexpensetracker.feature.wallet

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.paopeye.localexpensetracker.feature.MainActivity
import com.paopeye.localexpensetracker.R
import com.paopeye.localexpensetracker.data.model.Wallet
import com.paopeye.localexpensetracker.data.viewmodel.WalletViewModel
import com.skydoves.colorpickerview.ColorEnvelope
import kotlinx.android.synthetic.main.fragment_wallet.*
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import com.skydoves.colorpickerview.ColorPickerDialog
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import com.paopeye.localexpensetracker.feature.wallet.adapter.WalletAdapter
import java.util.*
import kotlin.collections.ArrayList


class WalletFragment : Fragment() {

    companion object {
        val TAG: String = WalletFragment::class.java.simpleName
        fun newInstance(): WalletFragment {
            val fragment = WalletFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


    private lateinit var mWalletViewModel: WalletViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var ourData = ArrayList<Wallet>()
    private var adapter = WalletAdapter(ourData)

    override fun onCreate(savedInstanceState: Bundle?) {
        mWalletViewModel = ViewModelProvider(requireActivity())[WalletViewModel::class.java]
        super.onCreate(savedInstanceState)
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
        setupRecyclerView()
        getWalletFromDatabase()
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(context)
        wallet_recycler_view.layoutManager = linearLayoutManager
        adapter.onPalleteClick= { wallet, idx ->
            ColorPickerDialog.Builder(context)
                .setTitle(R.string.select_color)
                .setPositiveButton(getString(R.string.select),
                    ColorEnvelopeListener { envelope, fromUser ->
                        updateWalletColor(envelope.color,idx)
                    })
                .setNegativeButton(
                    getString(R.string.cancel)
                ) { dialogInterface, i -> dialogInterface.dismiss() }
                .attachAlphaSlideBar(true) // the default value is true.
                .attachBrightnessSlideBar(true) // the default value is true.
                .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
                .show()
        }
        adapter.onSaveClick = { wallet ->
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(R.string.dialog_title_update_wallet)
            builder.setMessage(R.string.dialog_detail_update_wallet)

            builder.setPositiveButton(R.string.yes) { dialog, which ->
                saveWallet(wallet)
                dialog.dismiss()
            }

            builder.setNegativeButton(R.string.no) { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }
        adapter.onItemClick = {idx ->
            (wallet_recycler_view.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(idx,0)
        }
        adapter.onRemoveClick = {wallet ->
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(R.string.dialog_title_delete_wallet)
            builder.setMessage(R.string.dialog_detail_delete_wallet)

            builder.setPositiveButton(R.string.yes) { dialog, which ->
                deleteWallet(wallet)
                dialog.dismiss()
            }

            builder.setNegativeButton(R.string.no) { dialog, which ->
                dialog.dismiss()
            }
            builder.show() }
        wallet_recycler_view.adapter = adapter
    }

    private fun setupButton() {
        wallet_btnPallete.setOnClickListener() {
            ColorPickerDialog.Builder(context)
                .setTitle(R.string.select_color)
                .setPositiveButton(getString(R.string.select),
                    ColorEnvelopeListener { envelope, fromUser ->
                        setLayoutColor(envelope.color)
                    })
                .setNegativeButton(
                    getString(R.string.cancel)
                ) { dialogInterface, i -> dialogInterface.dismiss() }
                .attachAlphaSlideBar(true) // the default value is true.
                .attachBrightnessSlideBar(true) // the default value is true.
                .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
                .show()
        }
        wallet_btn_add.setOnClickListener() {
            if (btnAddState) {
                processBtnAddGone()
                btnAddState = false
            } else {
                processBtnAddVisible()
                btnAddState = true
            }
        }
        wallet_btnAddAccount.setOnClickListener() {
            addWalletFromUI()
        }
    }

    private fun setupEditText() {
        (activity as MainActivity).setupEditTextNumber(wallet_acc_balance, "Rp ")
    }

//region CRUD
    private fun deleteWallet(wallet: Wallet) {
        justDeleteWallet(wallet)
        ourData.clear()
    }

    private fun justDeleteWallet(wallet: Wallet) {
        try {
            mWalletViewModel.deleteWallet(wallet)
            (activity as MainActivity).hideKeyboard(layout_wallet)
            Toast.makeText(context, R.string.delete_msg, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Toast.makeText(context, ex.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveWallet(wallet: Wallet) {
        if(wallet.sWalletName.isEmpty()){
            Toast.makeText(context, R.string.wallet_name_empty_error, Toast.LENGTH_SHORT).show()
            getWalletFromDatabase()
            return
        }
        if(wallet.sWalletBalance.toString().isEmpty()){
            Toast.makeText(context, R.string.wallet_balance_empty_error, Toast.LENGTH_SHORT).show()
            getWalletFromDatabase()
            return
        }
        val currentDate = Calendar.getInstance().timeInMillis
        wallet.dLastUpdated = currentDate
        justUpdateWallet(wallet)
        ourData.clear()
    }

    private fun justUpdateWallet(wallet: Wallet){
        try {
            mWalletViewModel.updateWallet(wallet)
            (activity as MainActivity).hideKeyboard(layout_wallet)
            Toast.makeText(context, R.string.update_msg, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Toast.makeText(context, ex.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateWalletColor(iWalletColor: Int,position:Int) {
        ourData[position].iWalletColor = iWalletColor
        adapter.notifyItemChanged(position)
    }

    private fun getWalletFromDatabase() {
        ourData.clear()
        mWalletViewModel.readWalletData.observe(viewLifecycleOwner) {
            for (data in it) {
                ourData.add(data)
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun addWalletFromUI() {
        if (!validateWallet()) {
            return
        }
        val wallet: Wallet = getWalletDataFromUI() ?: return
        justAddWallet(wallet)
        ourData.clear()
    }

    private fun getWalletDataFromUI(): Wallet? {
        try {
            val walletName = wallet_acc_name.text.toString()
            val walletBalance =
                wallet_acc_balance.text.toString().filter { it.isDigit() }.toDouble()
            val walletDrawable = wallet_relativeLayout.background as ColorDrawable
            val walletColor = walletDrawable.color
            val currentDate = Calendar.getInstance().timeInMillis
            return Wallet(0,
                walletName,
                walletBalance,
                walletColor,
                currentDate,
                currentDate
            )
        } catch (ex: Exception) {
            Toast.makeText(context, ex.message.toString(), Toast.LENGTH_SHORT).show()
            return null
        }
    }

    private fun justAddWallet(wallet: Wallet) {
        try {
            mWalletViewModel.addWallet(wallet)
            resetUI()
            wallet_btn_add.callOnClick()
            (activity as MainActivity).hideKeyboard(layout_wallet)
            Toast.makeText(context, R.string.save_msg, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Toast.makeText(context, ex.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateWallet(): Boolean {
        if (wallet_acc_name.text.isEmpty()) {
            Toast.makeText(context, R.string.wallet_name_empty_error, Toast.LENGTH_SHORT).show()
            return false
        }

        if (wallet_acc_balance.text.isEmpty()) {
            Toast.makeText(context, R.string.wallet_balance_empty_error, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    //endregion

    private fun processBtnAddVisible() {
        wallet_cardview_add_account.visibility = View.VISIBLE
        wallet_btn_add.setImageResource(R.drawable.ic_baseline_remove_24)
    }

    private fun processBtnAddGone() {
        wallet_cardview_add_account.visibility = View.GONE
        wallet_btn_add.setImageResource(R.drawable.ic_baseline_add_24)
    }

    private fun setLayoutColor(colorId : Int) {
        wallet_relativeLayout.setBackgroundColor(colorId)
    }

    private fun resetUI(){
        wallet_acc_name.setText("")
        wallet_acc_balance.setText("")
        setLayoutColor(-1)//set to white
    }
}