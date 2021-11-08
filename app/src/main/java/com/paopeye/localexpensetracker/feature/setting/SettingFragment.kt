package com.paopeye.localexpensetracker.feature.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paopeye.localexpensetracker.feature.MainActivity
import com.paopeye.localexpensetracker.R
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {

    private var btnUserProfileState = 0F
    private var btnNotificationState = 0F
    private var btnCategoryState = 0F

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
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupButton()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupButton() {
        setting_userProfile.setOnClickListener() {processUserProfile()}
        setting_Categories.setOnClickListener() {processCategories()}
        setting_Notification.setOnClickListener() {processNotification()}

        setting_btnUserProfile.setOnClickListener() {processUserProfile()}
        setting_btnCategories.setOnClickListener() {processCategories()}
        setting_btnNotification.setOnClickListener() {processNotification()}
    }

    private fun processUserProfile(){
        btnUserProfileState = (activity as MainActivity).processRotate(btnUserProfileState)
        setting_btnUserProfile.animate().rotation(btnUserProfileState).start()
        if(btnUserProfileState == 180F)
            setting_content_userProfile.visibility = VISIBLE
        else
            setting_content_userProfile.visibility = GONE
    }

    private fun processCategories(){
        btnCategoryState = (activity as MainActivity).processRotate(btnCategoryState)
        setting_btnCategories.animate().rotation(btnCategoryState).start()
        if(btnCategoryState == 180F)
            setting_content_categories.visibility = VISIBLE
        else
            setting_content_categories.visibility = GONE
    }

    private fun processNotification(){
        btnNotificationState = (activity as MainActivity).processRotate(btnNotificationState)
        setting_btnNotification.animate().rotation(btnNotificationState).start()
        if(btnNotificationState == 180F)
            setting_content_notification.visibility = VISIBLE
        else
            setting_content_notification.visibility = GONE
    }
}