package com.glowplants.app.view.activity

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.glowplants.app.MyApplication.Companion.last_opened_tab
import com.glowplants.app.R
import com.glowplants.app.databinding.ActivityHomeBinding
import com.glowplants.app.helper.viewBinding
import com.glowplants.app.view.base.BaseActivity
import com.glowplants.app.view.fragment.HomeFragment
import com.glowplants.app.view.fragment.PredictFragment
import com.glowplants.app.view.fragment.ProfileFragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission


class HomeActivity : BaseActivity() {
    private val binding by viewBinding(ActivityHomeBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        checkPermissions()
        setTabActive(0)

        setListener()
    }

    override fun onResume() {
        super.onResume()
//        if (intent.hasExtra("tab")) {
//            setTabActive(intent.getIntExtra("tab", 0))
//            binding.bottomBar.setActiveItem(1)
//        } else
//            setTabActive(last_opened_tab)
    }

    private fun setListener() {
        binding.bottomBar.setActiveItem(0)
        binding.bottomBar.onItemSelected = {
            last_opened_tab = it
            when (it) {
                0 -> changeFragmentTo(HomeFragment())
                1 -> changeFragmentTo(PredictFragment())
                2 -> changeFragmentTo(ProfileFragment())

            }
        }

    }

    fun setActionBarTitle(title: String?) {
        supportActionBar!!.title = title
    }

    private fun setTabActive(position: Int) {
        when (position) {
            0 -> changeFragmentTo(HomeFragment())
            1 -> changeFragmentTo(PredictFragment())
            2 -> changeFragmentTo(ProfileFragment())

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        changeFragmentTo(HomeFragment())
    }

    fun changeFragmentTo(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.container_body, fragment).commit()
    }

    fun changeFragmentTo(fragment: Fragment, bundle: Bundle) {
        val fragmentManager = supportFragmentManager
        fragment.arguments = bundle
        fragmentManager.beginTransaction().replace(R.id.container_body, fragment).commit()
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            val permissionlistener = object : PermissionListener {
                override fun onPermissionGranted() {

                }

                override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>) {
                }
            }

            TedPermission(this@HomeActivity)
                .setPermissionListener(permissionlistener)
                .setPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                )
                .check()
        }
    }

}