package com.glowplants.app.view.fragment

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.glowplants.app.R
import com.glowplants.app.databinding.FragmentDetailBinding
import com.glowplants.app.helper.viewBinding
import com.glowplants.app.model.response.ArticelResponse
import com.glowplants.app.view.activity.HomeActivity
import com.glowplants.app.view.base.BaseFragment
import me.ibrahimsn.lib.NiceBottomBar

class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private var dataPenyakit : ArticelResponse.Data?= null

    private var sbPencegahan = StringBuilder()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataPenyakit = arguments?.getParcelable("dataPenyakit")

        setView()
        setListener()
    }

    private fun setView(){
        if (dataPenyakit != null){
            binding.tvNamaPenyakit.text = dataPenyakit?.name
            binding.tvDeskripsi.text = dataPenyakit?.description
//            binding.tvPencegahan.text = dataPenyakit?.prevention.toString()

            if (dataPenyakit?.prevention?.isNotEmpty() == true){
                for (i in dataPenyakit?.prevention!!){
                    sbPencegahan.append("- $i \n")
                }
            }

            binding.tvPencegahan.text = sbPencegahan.toString()

            Glide.with(requireContext())
                .load(dataPenyakit?.imageUrl)
                .into(binding.imgPenyakit)

        }

    }

    private fun setListener(){

    }

    override fun onResume() {
        super.onResume()

        (activity as HomeActivity?)!!.setActionBarTitle("Detail")
        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navBar: NiceBottomBar = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.GONE
    }
}