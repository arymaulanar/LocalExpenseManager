package com.paopeye.localexpensetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.paopeye.localexpensetracker.welcome.WelcomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNav()
        val fragment = WelcomeFragment.newInstance()
        addFragmentMain(fragment, WelcomeFragment.TAG)
    }

    private fun setupBottomNav() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_wallet -> {

                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }


    fun addFragmentMain(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out
            )
            .replace(R.id.frame_main, fragment, tag).addToBackStack(tag)
            .commit()
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out
            )
            .replace(R.id.frame_main, fragment, tag).addToBackStack(tag).commit()
    }

    fun hideBottomNav() {
        bottomNavigationView.visibility = View.GONE
    }

    fun showBottomNav() {
        bottomNavigationView.visibility = View.VISIBLE
    }
}