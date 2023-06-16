package com.glowplants.app.view.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.glowplants.app.R
import com.glowplants.app.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.btn_login
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class LoginActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressBar = findViewById(R.id.progressBar)
        emailText = findViewById(R.id.et_username)
        passwordText = findViewById(R.id.et_password)
        buttonLogin = findViewById(R.id.btn_login)

        btn_login.setOnClickListener{
            val username = emailText.text.toString()
            val password = passwordText.text.toString()
            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Data Tidak Lengkap", Toast.LENGTH_SHORT).show()
            } else {
                LoginAsyncTask().execute(username, password)
            }
        }
    }

    private inner class LoginAsyncTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()

            // Menampilkan indikator loading
            progressBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String): String? {
            val username = params[0]
            val password = params[1]

            // Membangun URL login
            val urlString = "https://grow-plants-ruca2dm4pa-et.a.run.app/api/v1/auth/signin"

            try {
                // Membuat koneksi HTTP
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection

                // Mengatur metode HTTP ke POST
                connection.requestMethod = "POST"
                connection.doOutput = true

                // Mengirim data login dalam format POST
                val postData = "email=" + URLEncoder.encode(username, "UTF-8") +
                        "&password=" + URLEncoder.encode(password, "UTF-8")

                // Mengirim data ke server
                val outputStream = DataOutputStream(connection.outputStream)
                outputStream.writeBytes(postData)
                outputStream.flush()
                outputStream.close()

                // Membaca response dari server
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                reader.close()
                connection.disconnect()

                return response.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: String?) {
            // Menyembunyikan indikator loading
            progressBar.visibility = View.GONE

            if (result != null) {
                try {
                    // Mengonversi response menjadi objek JSON
                    Log.e("result",result)
                    val json = JSONObject(result)

                    // Memeriksa status dari response
                    val status = json.optBoolean("error", true)

                    if (!status) {
                        // Login berhasil

                        onLoginSukses(json.getJSONObject("data").getString("token"))
                    } else {
                        // Login gagal
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            } else {
                // Terjadi kesalahan dalam koneksi atau response kosong
            }
        }
    }

    fun onLoginSukses(token: String){
        val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences("GrowSession", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.putBoolean("isLogin", true)
        editor.apply()


        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}