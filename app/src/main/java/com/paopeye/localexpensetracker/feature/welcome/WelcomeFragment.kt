package com.paopeye.localexpensetracker.feature.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.paopeye.localexpensetracker.feature.MainActivity
import com.paopeye.localexpensetracker.R
import com.paopeye.localexpensetracker.data.viewmodel.MainViewModel
import com.paopeye.localexpensetracker.data.viewmodel.WelcomeViewModel
import com.paopeye.localexpensetracker.feature.signup.SignUpFragment
import com.paopeye.localexpensetracker.feature.wallet.WalletFragment
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

    private lateinit var mWelcomeViewModel: WelcomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        mWelcomeViewModel = ViewModelProvider(this)[WelcomeViewModel::class.java]
        super.onCreate(savedInstanceState)
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
        getCurrentUser()
    }

    private fun isUserExist(isExist: Boolean) {
        if(isExist){
            val fragment= WalletFragment.newInstance()
            (activity as MainActivity).addFragmentMain(fragment,null)
            showBottomNav()
        }else{
            welcome_btn.visibility = View.VISIBLE
        }
    }

    private fun getCurrentUser(){
        mWelcomeViewModel.isUserExist.observe(viewLifecycleOwner){
            isUserExist(it)
        }
    }

    private fun hideBottomNav() {
        (activity as MainActivity).hideBottomNav()
    }

    private fun showBottomNav() {
        (activity as MainActivity).showBottomNav()
    }

    private fun setupButton(){
        welcome_btn.setOnClickListener {
            val fragment= SignUpFragment.newInstance()
            (activity as MainActivity).addFragmentMain(fragment,null)
        }
    }
}