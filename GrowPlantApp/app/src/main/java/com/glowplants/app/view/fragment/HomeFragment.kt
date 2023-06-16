package com.glowplants.app.view.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import com.glowplants.app.R
import com.glowplants.app.adapter.JenisPenyakitAdapter
import com.glowplants.app.data.viewModel.BaseViewModel
import com.glowplants.app.data.viewModel.ViewModelFactory
import com.glowplants.app.databinding.FragmentHomeBinding
import com.glowplants.app.helper.viewBinding
import com.glowplants.app.model.Status
import com.glowplants.app.model.response.ArticelResponse
import com.glowplants.app.view.activity.HomeActivity
import com.glowplants.app.view.base.BaseFragment
import me.ibrahimsn.lib.NiceBottomBar

class HomeFragment : BaseFragment(R.layout.fragment_home), JenisPenyakitAdapter.CellClickListener {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private lateinit var baseViewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseViewModel = ViewModelProviders.of(
            this,ViewModelFactory(apiInterface)
        ).get(BaseViewModel::class.java)

        loadArticle()
        setView()
        setListener()
    }

    private fun loadArticle(){
        baseViewModel.getArticle().observe(viewLifecycleOwner){
            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        val data = resource.data?.data
                        binding.rvJenisPenyakit.adapter = JenisPenyakitAdapter(requireContext(), data!!, this)
                        pLoading.dismissDialog()
                    }
                    Status.ERROR -> {
                        pLoading.dismissDialog()
                    }
                    Status.LOADING -> {
                        pLoading.showLoading()
                    }
                }
            }
        }
    }

    private fun setListener() {
        binding.btnPredict.setOnClickListener {
//            (activity as HomeActivity).changeFragmentTo(PredictFragment())
        }
    }

    private fun setView(){
    }

    override fun selectJenisPenyakit(data: ArticelResponse.Data) {
        (activity as HomeActivity).changeFragmentTo(DetailFragment(), bundleOf("dataPenyakit" to data))
    }

    override fun onResume() {
        super.onResume()

        (activity as HomeActivity?)!!.setActionBarTitle(getString(R.string.app_name))
        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val navBar: NiceBottomBar = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.VISIBLE
    }

}