package com.paopeye.localexpensetracker.feature.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.paopeye.localexpensetracker.feature.MainActivity
import com.paopeye.localexpensetracker.R
import com.paopeye.localexpensetracker.data.model.relation.CategoryWithChild
import com.paopeye.localexpensetracker.data.viewmodel.TransactionViewModel
import kotlinx.android.synthetic.main.fragment_transaction.*
import android.widget.DatePicker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.format.DateFormat
import android.widget.TimePicker
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.sign


class TransactionFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    companion object {
        val TAG: String = TransactionFragment::class.java.simpleName
        fun newInstance(): TransactionFragment {
            val fragment = TransactionFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mTransactionViewModel: TransactionViewModel
    private var categoryParentList = ArrayList<String>()
    private var categoryChildList = ArrayList<String>()
    private var categoryWithChildList = ArrayList<CategoryWithChild>()
    private var adapterCategoryParent: ArrayAdapter<String>? = null
    private var adapterCategoryChild: ArrayAdapter<String>? = null
    private var selectedDateTime: LocalDateTime = LocalDateTime.now()
    private var selectedYear: Int = selectedDateTime.year
    private var selectedMonth: Int = selectedDateTime.monthValue
    private var selectedDay: Int = selectedDateTime.dayOfMonth

    override fun onCreate(savedInstanceState: Bundle?) {
        mTransactionViewModel =
            ViewModelProvider(requireActivity())[TransactionViewModel::class.java]
        super.onCreate(savedInstanceState)
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
        setupInitDate()
        getInitData()
    }

    private fun setupInitDate() {
        setDate(LocalDateTime.now())
    }

    private fun getInitData() {
        getCategoryData()
    }

    private fun getCategoryData() {

        clearAndInitDataCategoriesParent()
        clearAndInitDataCategoriesChild()
        mTransactionViewModel.readCategoryWithChild.observe(viewLifecycleOwner) { value ->
            for (data in value) {
                categoryWithChildList.add(data)
            }
            populateCategoryParent(categoryWithChildList)
        }
    }

    private fun setupButton() {
        transaction_layout_date.setOnClickListener {
            showDateTimePicker()
        }
    }

    private fun showDateTimePicker() {
        val calendar: Calendar = Calendar.getInstance()
        val datePickerDialog =
            DatePickerDialog(
                requireContext(),
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        datePickerDialog.show()

    }

    private fun setTextDate(dateOnString: String) {
        transaction_textView_date.text = dateOnString
    }

    private fun setupEditText() {
        (activity as MainActivity).setupEditTextNumber(transaction_edittext_balance, "Rp ")
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.income_expense_arr,
            R.layout.support_simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            transaction_spinner_expense.adapter = adapter
        }


        clearAndInitDataCategoriesParent()
        clearAndInitDataCategoriesChild()

        adapterCategoryParent = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item, categoryParentList
        )
        transaction_spinner_parent_cat.adapter = adapterCategoryParent

        adapterCategoryChild = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item, categoryChildList
        )
        transaction_spinner_child_cat.adapter = adapterCategoryChild

        transaction_spinner_parent_cat.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    processCategoryChild(position)
                    populateCategoryChild(position, categoryWithChildList)
                }

            }
        transaction_spinner_child_cat.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                }

            }
    }

    private fun processCategoryChild(position: Int) {
        if (position != 0) {
            transaction_spinner_child_cat.setSelection(0, true)
            transaction_spinner_child_cat.visibility = View.VISIBLE
        } else {
            transaction_spinner_child_cat.visibility = View.GONE
        }
    }

    private fun populateCategoryParent(categoryWithChildList: ArrayList<CategoryWithChild>) {
        for (data in categoryWithChildList.sortedBy { it.category.catId }) {
            categoryParentList.add(data.category.sCategoryName)
        }
        adapterCategoryParent?.notifyDataSetChanged()
    }

    private fun populateCategoryChild(
        position: Int,
        categoryWithChildList: ArrayList<CategoryWithChild>
    ) {
        clearAndInitDataCategoriesChild()
        for (data in categoryWithChildList.sortedBy { it.category.catId }) {
            if (data.category.catId == position) {
                for (childData in data.childCat) {
                    categoryChildList.add(childData.sCategoryChildName)
                }
            }
        }
        adapterCategoryChild?.notifyDataSetChanged()
    }

    private fun clearAndInitDataCategoriesParent() {
        categoryParentList.clear()
        //initData
        categoryParentList.add(getString(R.string.please_select_one))
    }

    private fun clearAndInitDataCategoriesChild() {
        categoryChildList.clear()
        //initData
        categoryChildList.add(getString(R.string.please_select_one))
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        selectedYear = year
        selectedDay = day
        selectedMonth = month
        val localDateTime: LocalDateTime = LocalDateTime.now()
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            this,
            localDateTime.hour,
            localDateTime.minute,
            DateFormat.is24HourFormat(context)
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        selectedDateTime = LocalDateTime.of(selectedYear,selectedMonth+1,selectedDay,hourOfDay,minute)
        setDate(selectedDateTime)
    }

    private fun setDate(localDateTime: LocalDateTime) {
        var stringDate = ""
        val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        stringDate = localDateTime.format(dateFormatter)
        setTextDate(stringDate)
    }
}