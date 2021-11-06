package com.paopeye.localexpensetracker.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.paopeye.localexpensetracker.MainActivity
import com.paopeye.localexpensetracker.R
import com.paopeye.localexpensetracker.signup.SignUpFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment() {
    companion object {
        val TAG: String = WelcomeFragment::class.java.simpleName
        fun newInstance(): WelcomeFragment {
            val fragment = WelcomeFragment()
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
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init(){
        hideBottomNav()
        setupButton()
    }

    private fun hideBottomNav() {
        (activity as MainActivity).hideBottomNav()
    }

    private fun setupButton(){
        welcome_btn.setOnClickListener {
            val fragment=SignUpFragment.newInstance()
            (activity as MainActivity).addFragmentMain(fragment,null)
        }
    }
}