package com.glowplants.app.view.base

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.glowplants.app.R
import com.glowplants.app.data.api.ApiClient
import com.glowplants.app.data.api.ApiInterface
import com.glowplants.app.helper.Loading
import com.glowplants.app.helper.SessionManager
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment(fragment: Int) : Fragment(fragment) {

    lateinit var session: SessionManager
    var disposable: CompositeDisposable? = null

    private var toast: Toast? = null
    lateinit var pLoading: Loading
    lateinit var progressBar: ProgressBar
    lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pLoading = Loading(requireContext())
        disposable = CompositeDisposable()
        session = SessionManager(requireContext())
        progressBar = ProgressBar(requireContext())
        apiInterface = ApiClient.client.create(ApiInterface::class.java)
    }

    fun toast(@StringRes message: Int) {
        toast(getString(message))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.clear()
    }

    fun toast(toastMessage: String?) {
        if (toastMessage != null && toastMessage.isNotEmpty()) {
            if (toast != null) toast!!.cancel()
            toast = Toast.makeText(activity, toastMessage, Toast.LENGTH_LONG)
            toast!!.show()

        }
    }

    fun showProgress(status: Boolean) {
        if (status) {
            pLoading.showLoading(resources.getString(R.string.label_loading_title_dialog), false)
        } else {
            pLoading.dismissDialog()
        }
    }

    fun calculateNoOfColumns(
        context: Context,
        columnWidthDp: Float
    ): Int { // For example columnWidthdp=180
        val displayMetrics: DisplayMetrics = context.getResources().getDisplayMetrics()
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }

}