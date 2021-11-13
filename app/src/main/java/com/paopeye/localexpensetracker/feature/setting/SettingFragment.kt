package com.paopeye.localexpensetracker.feature.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.paopeye.localexpensetracker.feature.MainActivity
import com.paopeye.localexpensetracker.R
import com.paopeye.localexpensetracker.data.model.Category
import com.paopeye.localexpensetracker.data.model.CategoryChild
import com.paopeye.localexpensetracker.data.model.User
import com.paopeye.localexpensetracker.data.model.relation.CategoryWithChild
import com.paopeye.localexpensetracker.data.viewmodel.SettingViewModel
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.activity_main.*


class SettingFragment : Fragment() {

    private var btnUserProfileState = 0F
    private var btnNotificationState = 0F
    private var btnCategoryState = 0F
    private var btnParentCategoryAddState = 0F
    private var btnChildCategoryAddState = 0F
    private var sNameCurrent = ""
    private var categoryParentList = ArrayList<String>()
    private var categoryChildList = ArrayList<String>()
    private var categoryWithChildList = ArrayList<CategoryWithChild>()
    private var adapterCategoryParent: ArrayAdapter<String>? = null
    private var adapterCategoryChild: ArrayAdapter<String>? = null
    private lateinit var mSettingViewModel: SettingViewModel

    companion object {
        val TAG: String = SettingFragment::class.java.simpleName
        fun newInstance(): SettingFragment {
            val fragment = SettingFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mSettingViewModel = ViewModelProvider(requireActivity())[SettingViewModel::class.java]
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        setupButton()
        setupSpinner()
        getAllSettingData()
    }

    private fun setupSpinner() {
        clearAndInitDataCategoriesParent()
        clearAndInitDataCategoriesChild()

        adapterCategoryParent = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item, categoryParentList
        )
        setting_content_categories_spinner_parent.adapter = adapterCategoryParent

        adapterCategoryChild = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item, categoryChildList
        )
        setting_content_categories_spinner_child.adapter = adapterCategoryChild

        setting_content_categories_spinner_parent.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    processParentCategory(position)
                    populateCategoryChild(position, categoryWithChildList)
                }

            }
        setting_content_categories_spinner_child.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    processChildCategory(position)
                }

            }
    }

    private fun setupButton() {
        setting_userProfile.setOnClickListener { processUserProfile() }
        setting_Categories.setOnClickListener { processCategories() }
        setting_Notification.setOnClickListener { processNotification() }

        setting_btnUserProfile.setOnClickListener { processUserProfile() }
        setting_btnCategories.setOnClickListener { processCategories() }
        setting_btnNotification.setOnClickListener { processNotification() }

        setting_content_userProfile_btnSave.setOnClickListener { validateAndSaveUserProfileData() }

        setting_content_categories_btn_add_parent.setOnClickListener {
            processParentCategoryAdd()
        }
        setting_content_categories_btn_add_child.setOnClickListener {
            processChildCategoryAdd()
        }
        setting_content_categories_btn_save_parent.setOnClickListener {
            validateAndSaveCategoryParent()
        }
        setting_content_categories_btn_save_child.setOnClickListener{
            validateAndSaveCategoryChild()
        }
    }
    //region ui

    private fun processParentCategory(position: Int) {
        if (position != 0){
            setting_content_categories_layout_child.visibility = VISIBLE
            val categoryName = categoryParentList[position]
            setting_content_categories_editText_name_parent.setText(categoryName)
            setting_content_categories_editText_name_parent.hint = getString(R.string.update) + " " + categoryName
        }
        else{
            setting_content_categories_editText_name_parent.setText("")
            setting_content_categories_layout_child.visibility = GONE
            setting_content_categories_editText_name_parent.hint = getString(R.string.add_categories)
        }
    }

    private fun processChildCategory(position: Int) {
        if(position != 0){
            val categoryName = categoryChildList[position]
            setting_content_categories_editText_name_child.setText(categoryName)
            setting_content_categories_editText_name_child.hint = getString(R.string.update) + " " + categoryName
        }
        else{
            setting_content_categories_editText_name_child.setText("")
            setting_content_categories_editText_name_child.hint = getString(R.string.add_categories)
        }
    }

    private fun processParentCategoryAdd() {
        btnParentCategoryAddState =
            (activity as MainActivity).processRotate(btnParentCategoryAddState)
        if (btnParentCategoryAddState == 180F){
            setting_content_categories_btn_add_parent.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24)
            setting_content_categories_layout_add_parent.visibility = VISIBLE
        }
        else{
            setting_content_categories_btn_add_parent.setImageResource(R.drawable.ic_baseline_add_circle_outline_24)
            setting_content_categories_layout_add_parent.visibility = GONE
        }
    }

    private fun processChildCategoryAdd() {
        btnChildCategoryAddState =
            (activity as MainActivity).processRotate(btnChildCategoryAddState)
        if (btnChildCategoryAddState == 180F){
            setting_content_categories_btn_add_child.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24)
            setting_content_categories_layout_add_child.visibility = VISIBLE
        }
        else{
            setting_content_categories_btn_add_child.setImageResource(R.drawable.ic_baseline_add_circle_outline_24)
            setting_content_categories_layout_add_child.visibility = GONE
        }
    }

    private fun processUserProfile() {
        btnUserProfileState = (activity as MainActivity).processRotate(btnUserProfileState)
        setting_btnUserProfile.animate().rotation(btnUserProfileState).start()
        if (btnUserProfileState == 180F)
            setting_content_userProfile.visibility = VISIBLE
        else
            setting_content_userProfile.visibility = GONE
    }

    private fun processCategories() {
        btnCategoryState = (activity as MainActivity).processRotate(btnCategoryState)
        setting_btnCategories.animate().rotation(btnCategoryState).start()
        if (btnCategoryState == 180F)
            setting_content_categories.visibility = VISIBLE
        else
            setting_content_categories.visibility = GONE
    }

    private fun processNotification() {
        btnNotificationState = (activity as MainActivity).processRotate(btnNotificationState)
        setting_btnNotification.animate().rotation(btnNotificationState).start()
        if (btnNotificationState == 180F)
            setting_content_notification.visibility = VISIBLE
        else
            setting_content_notification.visibility = GONE
    }
    //endregion

    //region getData
    private fun getAllSettingData() {
        getUserProfileData()
        getCategoryData()
    }

    private fun getCategoryData() {
        clearAndInitDataCategoriesParent()
        clearAndInitDataCategoriesChild()
        mSettingViewModel.readCategoryWithChild.observe(viewLifecycleOwner) { value ->
            for (data in value) {
                categoryWithChildList.add(data)
            }
            populateCategoryParent(categoryWithChildList)
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

    private fun getUserProfileData() {
        mSettingViewModel.readUserData.observe(viewLifecycleOwner, { user ->
            for (data in user) {
                setUserProfileData(sName = data.sName)
                sNameCurrent = data.sName
            }
        })
    }

    private fun setUserProfileData(sName: String) {
        setting_content_userProfile_editText_Name.setText(sName)
    }
    //endregion

    //region saveData
    private fun validateAndSaveUserProfileData() {
        (activity as MainActivity).hideKeyboard(layout_setting)
        val sName = setting_content_userProfile_editText_Name.text.toString()
        if (sName.isNotEmpty() && sNameCurrent.lowercase().trim() != sName.lowercase().trim()) {
            val snack = Snackbar.make(layout_setting, R.string.save_cnf, Snackbar.LENGTH_LONG)
            snack.setAction(R.string.save) {
                justUpdateUserProfile(sName)
            }
            snack.anchorView = bottomNavigationView
            snack.show()
        }else{
            Toast.makeText(requireContext(), R.string.name_empty_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun justUpdateUserProfile(sName: String) {
        try {
            val user = User(1, sName)
            mSettingViewModel.updateUser(user)
            Toast.makeText(context, R.string.save_msg, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Toast.makeText(context, ex.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateAndSaveCategoryParent() {
        (activity as MainActivity).hideKeyboard(layout_setting)
        val sCategoryName = setting_content_categories_editText_name_parent.text.toString()
        val categoryId = setting_content_categories_spinner_parent.selectedItemPosition
        if (sCategoryName.isNotEmpty()) {
            val snack = Snackbar.make(layout_setting, R.string.save_categories_cnf, Snackbar.LENGTH_LONG)
            snack.setAction(R.string.save) {
                justSaveCategoryParent(sCategoryName,categoryId)
            }
            snack.anchorView = bottomNavigationView
            snack.show()
        }else{
            Toast.makeText(requireContext(), R.string.category_empty_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun justSaveCategoryParent(sCategoryName: String,categoryId: Int) {
        try {
            val category = Category(categoryId, sCategoryName)
            mSettingViewModel.saveParentCategory(category)
            Toast.makeText(context, R.string.save_msg, Toast.LENGTH_SHORT).show()
            refreshCategoriesData()
        } catch (ex: Exception) {
            Toast.makeText(context, ex.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    private fun validateAndSaveCategoryChild() {
        (activity as MainActivity).hideKeyboard(layout_setting)
        val sCategoryName = setting_content_categories_editText_name_child.text.toString()
        val categoryParentId = setting_content_categories_spinner_parent.selectedItemPosition
        val categoryChildId = setting_content_categories_spinner_child.selectedItemPosition
        if (sCategoryName.isNotEmpty()) {
            val snack = Snackbar.make(layout_setting, R.string.save_categories_cnf, Snackbar.LENGTH_LONG)
            snack.setAction(R.string.save) {
                justSaveCategoryChild(categoryChildId,sCategoryName,categoryParentId)
            }
            snack.anchorView = bottomNavigationView
            snack.show()
        }else{
            Toast.makeText(requireContext(), R.string.category_empty_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun justSaveCategoryChild(catChildId:Int,sCategoryName: String, categoryParentId:Int) {
        try {
            val categoryChild = CategoryChild(catChildId,categoryParentId, sCategoryName)
            mSettingViewModel.saveChildCategory(categoryChild)
            Toast.makeText(context, R.string.save_msg, Toast.LENGTH_SHORT).show()
            refreshCategoriesData()
        } catch (ex: Exception) {
            Toast.makeText(context, ex.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    //endregion

    //region categories
    private fun refreshCategoriesData(){
        categoryWithChildList.clear()
        clearAndInitDataCategoriesParent()
        clearAndInitDataCategoriesChild()
        hideAllContentCategories()
    }

    private fun hideAllContentCategories(){
        setting_content_categories_layout_add_child.visibility = GONE
        setting_content_categories_layout_add_parent.visibility = GONE
        setting_content_categories_layout_child.visibility = GONE

        btnParentCategoryAddState = 0F
        setting_content_categories_btn_add_parent.setImageResource(R.drawable.ic_baseline_add_circle_outline_24)
        btnChildCategoryAddState = 0F
        setting_content_categories_btn_add_child.setImageResource(R.drawable.ic_baseline_add_circle_outline_24)
        setting_content_categories_spinner_parent.setSelection(0,true)
        setting_content_categories_spinner_child.setSelection(0,true)
    }

    private fun clearAndInitDataCategoriesParent(){
        categoryParentList.clear()
        //initData
        categoryParentList.add(getString(R.string.please_select_one))
    }

    private fun clearAndInitDataCategoriesChild(){
        categoryChildList.clear()
        //initData
        categoryChildList.add(getString(R.string.please_select_one))
    }
    //endregion
}