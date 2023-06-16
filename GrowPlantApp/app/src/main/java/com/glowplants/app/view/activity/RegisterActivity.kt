package com.glowplants.app.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.glowplants.app.R
import com.glowplants.app.databinding.ActivityHomeBinding
import com.glowplants.app.helper.viewBinding
import com.glowplants.app.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.btn_login
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class RegisterActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    private lateinit var fullNameText: EditText
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        progressBar = findViewById(R.id.progressBar2)
        emailText = findViewById(R.id.et_username)
        passwordText = findViewById(R.id.et_password)
        fullNameText = findViewById(R.id.et_name)
        buttonRegister = findViewById(R.id.btn_login)

        btn_login.setOnClickListener{
            val username = emailText.text.toString()
            val password = passwordText.text.toString()
            val fullname = passwordText.text.toString()
            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Data Tidak Lengkap", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(username, password, fullname) { success, message ->
                    if (success) {
                        // Registrasi berhasil
                        showProgress(false)
                        if (message != null) {
                            showToast(message)
                        }
                        gotoLogin()
                    } else {
                        // Registrasi gagal
                        showProgress(false)
                        if (message != null) {
                            showToast(message)
                        }
                    }
                }
            }
        }

    }


    // Fungsi untuk melakukan registrasi pengguna
    fun registerUser(username: String, password: String, fullName: String, callback: (Boolean, String?) -> Unit) {
        val client = OkHttpClient()
        val url = "https://grow-plants-ruca2dm4pa-et.a.run.app/api/v1/auth/signup" // Ganti dengan URL API yang sesuai

        showProgress(true)

        val requestBody = FormBody.Builder()
            .add("email", username)
            .add("password", password)
            .add("name", fullName)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Koneksi gagal atau ada kesalahan jaringan
                callback(false, "Connection error")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()

                try {

                    val jsonObject = JSONObject(responseData)
                    Log.e("result",jsonObject.toString())
                    val error = jsonObject.getBoolean("error")

                    if (!error) {
                        callback(true, "Registrasi berhasil, silakan login untuk melanjutkan")
                    } else {
                        callback(false, "register gagal!")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    callback(false, "JSON parsing error")
                }
            }
        })
    }

    fun showToast(message: String){
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun showProgress(value: Boolean){
        Handler(Looper.getMainLooper()).post {
            if(value)
                progressBar.visibility = View.VISIBLE
            else{
                progressBar.visibility = View.GONE
            }
        }
    }

    fun gotoLogin(){
        Handler(Looper.getMainLooper()).post {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}