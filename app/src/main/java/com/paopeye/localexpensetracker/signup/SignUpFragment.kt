package com.paopeye.localexpensetracker.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paopeye.localexpensetracker.MainActivity
import com.paopeye.localexpensetracker.R
import com.paopeye.localexpensetracker.wallet.WalletFragment
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.fragment_welcome.*

class SignUpFragment : Fragment() {

    companion object {
        val TAG: String = SignUpFragment::class.java.simpleName
        fun newInstance(): SignUpFragment {
            val fragment = SignUpFragment()
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
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        super.onViewCreated(view, savedInstanceState)
    }
    private fun init(){
        setupButton()
    }

    private fun showBottomNav() {
        (activity as MainActivity).showBottomNav()
    }
    private fun setupButton(){
        signup_btn.setOnClickListener {
            val fragment=WalletFragment.newInstance()
            (activity as MainActivity).addFragmentMain(fragment,null)
            showBottomNav()
        }
    }
}