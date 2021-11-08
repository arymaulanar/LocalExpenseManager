package com.paopeye.localexpensetracker.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.cottacush.android.currencyedittext.CurrencyInputWatcher
import com.google.android.material.snackbar.Snackbar
import com.paopeye.localexpensetracker.R
import com.paopeye.localexpensetracker.feature.dashboard.DashboardFragment
import com.paopeye.localexpensetracker.feature.setting.SettingFragment
import com.paopeye.localexpensetracker.feature.transaction.TransactionFragment
import com.paopeye.localexpensetracker.feature.util.NumericKeyBoardTransformationMethod
import com.paopeye.localexpensetracker.feature.wallet.WalletFragment
import com.paopeye.localexpensetracker.feature.welcome.WelcomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_transaction.*
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNav()
        initWelcome()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {finish()}

        this.doubleBackToExitPressedOnce = true
        Snackbar.make(layout_main, R.string.exit_msg, Snackbar.LENGTH_SHORT)
            .show()

        Handler(Looper.getMainLooper()).postDelayed(
            { doubleBackToExitPressedOnce = false },
            2000
        )
    }

    //region mainActivityMethod
    private var doubleBackToExitPressedOnce = false
    private var currentFragmentIdx = 0
    private var newFragmentIdx = 1

    private fun setupBottomNav() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_wallet -> {
                    newFragmentIdx = 1
                    val fragment = WalletFragment.newInstance()
                    addFragmentMain(fragment, WalletFragment.TAG)
                    return@setOnItemSelectedListener true
                }
                R.id.menu_dashboard -> {
                    newFragmentIdx = 2
                    val fragment = DashboardFragment.newInstance()
                    addFragmentMain(fragment, DashboardFragment.TAG)
                    return@setOnItemSelectedListener true
                }
                R.id.menu_transaction -> {
                    newFragmentIdx = 3
                    val fragment = TransactionFragment.newInstance()
                    addFragmentMain(fragment, TransactionFragment.TAG)
                    return@setOnItemSelectedListener true
                }
                R.id.menu_setting -> {
                    newFragmentIdx = 4
                    val fragment = SettingFragment.newInstance()
                    addFragmentMain(fragment, SettingFragment.TAG)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun initWelcome() {
        val fragment = WelcomeFragment.newInstance()
        addFragmentMain(fragment, null)
    }
    //endregion

    //region public function
        //region fragment
    fun addFragmentMain(fragment: Fragment, tag: String?) {
        if (tag == null) {
            justAddFragmentNull(fragment)
        } else {
            if (currentFragmentIdx < newFragmentIdx) {
                val fragmentManager = supportFragmentManager
                val fragmentByTag = fragmentManager.findFragmentByTag(tag)
                if (fragmentByTag == null)
                    justAddFragmentLeft(fragment, tag)
                else
                    replaceFragmentLeft(fragment, tag)
            } else if (currentFragmentIdx > newFragmentIdx) {
                val fragmentManager = supportFragmentManager
                val fragmentByTag = fragmentManager.findFragmentByTag(tag)
                if (fragmentByTag == null)
                    justAddFragmentRight(fragment, tag)
                else
                    replaceFragmentRight(fragment, tag)
            }
            currentFragmentIdx = newFragmentIdx
        }
    }

    private fun justAddFragmentNull(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.fade_out_left,
                R.anim.fade_in_left,
                R.anim.slide_out_left
            )
            .replace(R.id.frame_main, fragment)
            .commit()
    }

    private fun justAddFragmentLeft(fragment: Fragment, tag: String?) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.fade_out_left,
                R.anim.fade_in_left,
                R.anim.slide_out_left
            )
            .replace(R.id.frame_main, fragment, tag).addToBackStack(tag)
            .commit()
    }

    private fun justAddFragmentRight(fragment: Fragment, tag: String?) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.fade_out_right,
                R.anim.fade_in_right,
                R.anim.slide_out_right
            )
            .replace(R.id.frame_main, fragment, tag).addToBackStack(tag)
            .commit()
    }

    private fun replaceFragmentRight(fragment: Fragment, tag: String?) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.fade_out_right,
                R.anim.fade_in_right,
                R.anim.slide_out_right
            )
            .replace(R.id.frame_main, fragment, tag)
            .commit()
    }

    private fun replaceFragmentLeft(fragment: Fragment, tag: String?) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.fade_out_left,
                R.anim.fade_in_left,
                R.anim.slide_out_left
            )
            .replace(R.id.frame_main, fragment, tag).commit()
    }
        //endregion
        //region bottomNavVisibility
    fun hideBottomNav() {
        bottomNavigationView.visibility = View.GONE
    }

    fun showBottomNav() {
        bottomNavigationView.visibility = View.VISIBLE
    }
        //endregion
        //region animate
    fun processRotate(_init: Float): Float {
        var init = _init
            init = if (init == 0F) 180F else 0F
        return init
    }
        //endregion
        //region setup
    fun setupEditTextNumber(
        editText: EditText, currency: String
    ) {
        editText.transformationMethod = NumericKeyBoardTransformationMethod()
        editText.addTextChangedListener(
            CurrencyInputWatcher(
                editText, currency,
                Locale.getDefault()
            )
        )
    }
        //endregion
    //endregion
}