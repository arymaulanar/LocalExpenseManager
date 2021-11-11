package com.paopeye.localexpensetracker.feature.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.paopeye.localexpensetracker.feature.MainActivity
import com.paopeye.localexpensetracker.R
import com.paopeye.localexpensetracker.data.model.User
import com.paopeye.localexpensetracker.data.viewmodel.MainViewModel
import com.paopeye.localexpensetracker.data.viewmodel.SettingViewModel
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.activity_main.*


class SettingFragment : Fragment() {

    private var btnUserProfileState = 0F
    private var btnNotificationState = 0F
    private var btnCategoryState = 0F
    private var sNameCurrent = ""
    private lateinit var mSettingViewModel: MainViewModel

    companion object {
        val TAG: String = SettingFragment::class.java.simpleName
        fun newInstance(): SettingFragment {
            val fragment = SettingFragment()
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
        mSettingViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        setupButton()
        getAllSettingData()
    }

    //region ui
    private fun setupButton() {
        setting_userProfile.setOnClickListener() { processUserProfile() }
        setting_Categories.setOnClickListener() { processCategories() }
        setting_Notification.setOnClickListener() { processNotification() }

        setting_btnUserProfile.setOnClickListener() { processUserProfile() }
        setting_btnCategories.setOnClickListener() { processCategories() }
        setting_btnNotification.setOnClickListener() { processNotification() }

        setting_btnSave.setOnClickListener() { saveSettingData() }
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
    private fun saveSettingData() {
        (activity as MainActivity).hideKeyboard(layout_setting)
        val sName = setting_content_userProfile_editText_Name.text.toString()
        if (sName.isNotEmpty() && sNameCurrent.lowercase().trim() != sName.lowercase().trim())
            updateSettingUserProfile(sName)
    }

    private fun updateSettingUserProfile(sName: String) {
        val snack = Snackbar.make(layout_setting,R.string.save_cnf, Snackbar.LENGTH_LONG)
        snack.setAction(R.string.save) {
            justUpdateUserProfile(sName)
        }
        snack.anchorView = bottomNavigationView
        snack.show()
    }

    private fun justUpdateUserProfile(sName: String){
        try {
            val user = User(1,sName)
            mSettingViewModel.updateUser(user)
            Toast.makeText(context, R.string.save_msg, Toast.LENGTH_SHORT).show()
        }catch (ex:Exception){
            Toast.makeText(context, ex.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    //endregion
}