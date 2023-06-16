package com.glowplants.app.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.glowplants.app.R
import com.glowplants.app.databinding.FragmentProfileBinding
import com.glowplants.app.helper.viewBinding
import com.glowplants.app.view.activity.HomeActivity
import com.glowplants.app.view.activity.WelcomeActivity
import com.glowplants.app.view.base.BaseFragment
import me.ibrahimsn.lib.NiceBottomBar


class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()
        setListener()
    }

    private fun setView(){
        binding.logoutButton.setOnClickListener {
            val sharedPreferences: SharedPreferences = requireContext().applicationContext.getSharedPreferences("GrowSession", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("token", "")
            editor.putBoolean("isLogin", false)
            editor.apply()
            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun setListener(){

    }

    override fun onResume() {
        super.onResume()

        (activity as HomeActivity?)!!.setActionBarTitle("Profile")
        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val navBar: NiceBottomBar = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.VISIBLE
    }
}