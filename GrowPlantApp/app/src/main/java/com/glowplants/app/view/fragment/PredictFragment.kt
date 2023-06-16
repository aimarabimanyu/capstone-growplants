package com.glowplants.app.view.fragment

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProviders
import com.github.dhaval2404.imagepicker.ImagePicker
import com.glowplants.app.R
import com.glowplants.app.data.viewModel.BaseViewModel
import com.glowplants.app.data.viewModel.ViewModelFactory
import com.glowplants.app.databinding.FragmentPredictBinding
import com.glowplants.app.helper.viewBinding
import com.glowplants.app.model.Status
import com.glowplants.app.view.activity.HomeActivity
import com.glowplants.app.view.base.BaseFragment
import me.ibrahimsn.lib.NiceBottomBar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class PredictFragment : BaseFragment(R.layout.fragment_predict) {

    private val binding by viewBinding(FragmentPredictBinding::bind)

//    private var dataPenyakit : ArticelResponse.Data?= null
    private lateinit var baseViewModel: BaseViewModel
    private var file: File?= null
    private var sbPencegahan = StringBuilder()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseViewModel = ViewModelProviders.of(
            this, ViewModelFactory(apiInterface)
        ).get(BaseViewModel::class.java)

        setView()
        setListener()
    }

    private fun setView(){

    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                file = File(fileUri.path)
                binding.imgPredict.setImageURI(fileUri)

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private fun setListener(){
        binding.clCamera.setOnClickListener {
            ImagePicker.with(this)
                .compress(400)
                .start { resultCode, data ->
                    when (resultCode) {
                        Activity.RESULT_OK -> {
                            val fileUri = data?.data
                            binding.imgPredict.setImageURI(fileUri)
                            file = ImagePicker.getFile(data)!!
                        }

                        ImagePicker.RESULT_ERROR -> {
                            toast(ImagePicker.getError(data))
                        }

                        else -> {
                            Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

//            ImagePicker.with(this)
//                .compress(400)         //Final image size will be less than 400 KB(Optional)
////            .maxResultSize(
////                800,
////                800
////            )  //Final image resolution will be less than 200 x 200(Optional)
//                .createIntent { intent ->
//                    startForProfileImageResult.launch(intent)
//                }
        }

        binding.clGenerate.setOnClickListener {
            if (file == null) {
                toast("Lampirkan foto terlebih dahulu!")
                return@setOnClickListener
            } else {
                generateImage()
            }
        }
    }

    private fun generateImage(){
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)

        file?.let {
            builder.addFormDataPart(
                "file", file!!.name, file!!
                    .asRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
        }

        val body = builder.build()

        baseViewModel.predict(body).observe(viewLifecycleOwner){
            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        val dataPenyakit = resource.data?.data?.articles
                        if (dataPenyakit?.isNotEmpty() == true){
                            if (dataPenyakit != null){
                                binding.lyResult.visibility = View.VISIBLE

                                binding.tvNamaPenyakit.text = dataPenyakit[0].name
                                binding.tvDeskripsi.text = dataPenyakit[0].description
//                                binding.tvPencegahan.text = dataPenyakit[0].prevention.toString()

                                if (dataPenyakit[0].prevention.isNotEmpty()){
                                    for (i in dataPenyakit[0].prevention){
                                        sbPencegahan.append("- $i \n")
                                    }
                                }

                                binding.tvPencegahan.text = sbPencegahan.toString()
                            } else {
                                binding.lyResult.visibility = View.GONE
                            }
                        }

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

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity?)!!.setActionBarTitle("Predisct")
//        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navBar: NiceBottomBar = requireActivity().findViewById(R.id.bottomBar)
        navBar.visibility = View.VISIBLE
    }
}