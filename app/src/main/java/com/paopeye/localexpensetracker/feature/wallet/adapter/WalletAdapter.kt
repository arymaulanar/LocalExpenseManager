package com.paopeye.localexpensetracker.feature.wallet.adapter

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.cottacush.android.currencyedittext.CurrencyInputWatcher
import com.paopeye.localexpensetracker.R
import com.paopeye.localexpensetracker.data.model.Wallet
import com.paopeye.localexpensetracker.feature.util.NumericKeyBoardTransformationMethod
import com.paopeye.localexpensetracker.feature.wallet.WalletFragment
import kotlinx.android.synthetic.main.fragment_setting.*
import java.util.*
import kotlin.collections.ArrayList
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import java.math.BigInteger
import javax.inject.Inject


class WalletAdapter(private val dataSet: ArrayList<Wallet>) :
    RecyclerView.Adapter<WalletAdapter.ViewHolder>() {

    var onItemClick: ((Int) -> Unit)? = null
    var onPalleteClick: ((Wallet,Int) -> Unit)? = null
    var onSaveClick: ((Wallet) -> Unit)? = null
    var onRemoveClick: ((Wallet) -> Unit)? = null
    var currentSelectedPosition = RecyclerView.NO_POSITION

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var textContentAccount: EditText? = null
        var textContentBalance : EditText? = null
        var btnPallete : Button? =null
        var btnSave : ImageButton? =null
        var btnRemove : ImageButton? =null
        var layoutContent :RelativeLayout? = null
        var layoutContentButton :LinearLayout? = null
        init{
            textContentAccount = v.findViewById(R.id.recycler_item_wallet_content_acc_name)
            textContentBalance = v.findViewById(R.id.recycler_item_wallet_content_acc_balance)
            btnPallete = v.findViewById(R.id.recycler_item_wallet_content_btnPallete)
            btnSave = v.findViewById(R.id.recycler_item_wallet_content_btnSave)
            btnRemove = v.findViewById(R.id.recycler_item_wallet_content_btnDelete)
            layoutContent = v.findViewById(R.id.recycler_item_wallet_content)
            layoutContentButton = v.findViewById(R.id.recycler_item_wallet_content_buttonAction)

            layoutContentButton!!.visibility = View.GONE
            textContentBalance!!.transformationMethod = NumericKeyBoardTransformationMethod()
            textContentBalance!!.addTextChangedListener(
                CurrencyInputWatcher(
                    textContentBalance!!, "Rp ",
                    Locale.getDefault()
                )
            )
            btnPallete?.setOnClickListener(){
                onPalleteClick?.invoke(dataSet[adapterPosition],adapterPosition)
            }
            btnSave?.setOnClickListener(){
                onSaveClick?.invoke(dataSet[adapterPosition])
            }
            btnRemove?.setOnClickListener(){
                onRemoveClick?.invoke(dataSet[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_item_wallet, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textContentAccount?.setText(dataSet[position].sWalletName)
        viewHolder.textContentBalance?.setText(dataSet[position].sWalletBalance.toLong().toString())
        viewHolder.layoutContent?.setBackgroundColor(dataSet[position].iWalletColor)

        viewHolder.btnPallete?.setOnClickListener(){
            selectPosition(position)
            onPalleteClick?.invoke(dataSet[position],position)
        }
        viewHolder.btnSave?.setOnClickListener(){
            val sWalletName = viewHolder.textContentAccount?.text.toString()
            val sWalletBalance = viewHolder.textContentBalance?.text.toString().filter { it.isDigit() }.toLong()
            val iWalletColor = dataSet[position].iWalletColor
            if(!isThereAnyChange(sWalletName,sWalletBalance,iWalletColor, position))
                return@setOnClickListener
            val wallet = Wallet(dataSet[position].id,
                sWalletName,
                sWalletBalance.toDouble(),
                dataSet[position].iWalletColor,
                dataSet[position].dCreated,
                dataSet[position].dLastUpdated
            )
            onSaveClick?.invoke(wallet)
        }
        viewHolder.btnRemove?.setOnClickListener(){
            val wallet = dataSet[position]
            onRemoveClick?.invoke(wallet)
        }
        viewHolder.layoutContent?.setOnClickListener(){
            selectPosition(position)
        }
        viewHolder.textContentBalance?.setOnFocusChangeListener(){view , focus ->
            if(focus){
                selectPosition(position)
            }
        }
        viewHolder.textContentAccount?.setOnFocusChangeListener(){view , focus ->
            if(focus){
                selectPosition(position)
            }
        }
        if (currentSelectedPosition == position) {
            viewHolder.layoutContentButton!!.visibility = View.VISIBLE
        }else{
            viewHolder.layoutContentButton!!.visibility = View.GONE
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    private fun isThereAnyChange (sWalletName:String, sWalletBalance:Long, iWalletColor:Int, position: Int) :Boolean {
        if(sWalletName != dataSet[position].sWalletName)
            return true
        if(sWalletBalance != dataSet[position].sWalletBalance.toString().filter { it.isDigit() }.toLong())
            return true
        if(iWalletColor != dataSet[position].iWalletColor)
            return true
        return false
    }

    private fun selectPosition(position: Int){
        onItemClick?.invoke(position)
        currentSelectedPosition = position
        notifyItemRangeChanged(0, dataSet.size, true)
    }
}