package com.paopeye.localexpensetracker.feature.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.paopeye.localexpensetracker.feature.MainActivity
import com.paopeye.localexpensetracker.R
import com.paopeye.localexpensetracker.data.model.User
import com.paopeye.localexpensetracker.data.viewmodel.UserViewModel
import com.paopeye.localexpensetracker.feature.wallet.WalletFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_signup.*

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

    private lateinit var mUserViewModel: UserViewModel

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
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        setupButton()
    }

    private fun showBottomNav() {
        (activity as MainActivity).showBottomNav()
    }
    private fun setupButton(){
        signup_btn.setOnClickListener {
            if(insertToDatabase()){
                val fragment= WalletFragment.newInstance()
                (activity as MainActivity).addFragmentMain(fragment,null)
                showBottomNav()
            }
        }
    }

    private fun insertToDatabase():Boolean{
        val sInput = signup_editText_name.text.toString()
        val result = true
        if(sInput.isEmpty()){
            Snackbar.make(layout_signup, R.string.name_empty_error, Snackbar.LENGTH_SHORT)
                .show()
            return false
        }

        val user = User(0,sInput)
        mUserViewModel.addUser(user)

        return result
    }
}