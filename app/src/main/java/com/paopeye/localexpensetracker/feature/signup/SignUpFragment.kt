package com.paopeye.localexpensetracker.feature.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.paopeye.localexpensetracker.feature.MainActivity
import com.paopeye.localexpensetracker.R
import com.paopeye.localexpensetracker.data.model.User
import com.paopeye.localexpensetracker.data.viewmodel.MainViewModel
import com.paopeye.localexpensetracker.data.viewmodel.SignupViewModel
import com.paopeye.localexpensetracker.feature.wallet.WalletFragment
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

    private lateinit var mSignUpViewModel: SignupViewModel

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
        mSignUpViewModel = ViewModelProvider(this)[SignupViewModel::class.java]
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
        val result = true
        try {
            val sInput = signup_editText_name.text.toString()
            if(sInput.isEmpty()){
                Snackbar.make(layout_signup, R.string.name_empty_error, Snackbar.LENGTH_SHORT)
                    .show()
                return false
            }

            val user = User(0,sInput)
            mSignUpViewModel.addUser(user)
            Toast.makeText(context, R.string.save_msg, Toast.LENGTH_SHORT).show()
        }catch (ex:Exception){
            Toast.makeText(context, ex.message.toString(), Toast.LENGTH_SHORT).show()
            return false
        }
        return result
    }
}