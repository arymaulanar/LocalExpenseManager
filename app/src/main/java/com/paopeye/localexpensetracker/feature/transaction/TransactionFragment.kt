package com.paopeye.localexpensetracker.feature.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.paopeye.localexpensetracker.feature.MainActivity
import com.paopeye.localexpensetracker.R
import kotlinx.android.synthetic.main.fragment_transaction.*

class TransactionFragment :Fragment() {

    companion object {
        val TAG: String = TransactionFragment::class.java.simpleName
        fun newInstance(): TransactionFragment {
            val fragment = TransactionFragment()
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
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        setupSpinner()
        setupEditText()
        setupButton()
    }

    private fun setupButton() {

    }

    private fun setupEditText() {
        (activity as MainActivity).setupEditTextNumber(transaction_edittext_balance,"Rp ")
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(requireContext(),R.array.income_expense_arr,R.layout.support_simple_spinner_dropdown_item).also {
            adapter ->  adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            transaction_spinner_expense.adapter = adapter
        }
    }
}