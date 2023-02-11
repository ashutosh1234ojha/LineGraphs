package com.example.graphs.db

import android.app.ActivityManager
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.graphs.R


class EncryptedSharedPf : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_encrypted_shared_pf
        )

        val btn = findViewById<Button>(R.id.btn)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val tv = findViewById<TextView>(R.id.tv)
        val et = findViewById<EditText>(R.id.et)

        setUp()
        setData(tv)



        btn.setOnClickListener {
            val str = et.text
            sharedPreferences.edit().putString("name", str.toString()).apply()
            val name = sharedPreferences.getString("name", "")
            tv.text = name
        }

        btnClear.setOnClickListener {
            clearData()
        }


    }

    fun setData(tv: TextView) {
        val name = sharedPreferences.getString("name", "")
        tv.text = name
    }

    private fun clearData() {
        (getSystemService(ACTIVITY_SERVICE) as ActivityManager)
            .clearApplicationUserData() // note: it has a return value!


    }

    private fun setUp() {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        // Initialize/open an instance of EncryptedSharedPreferences on below line.
        sharedPreferences = EncryptedSharedPreferences.create(
            // passing a file name to share a preferences
            "preferences",
            masterKeyAlias,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        // on below line creating a variable
        // to get the data from shared prefs.
        val name = sharedPreferences.getString("name", "")
        val age = sharedPreferences.getString("age", "")

        // on below line we are setting data
        // to our name and age edit text.

    }
}