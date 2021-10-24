package com.paopeye.localexpensetracker.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paopeye.localexpensetracker.R

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
}