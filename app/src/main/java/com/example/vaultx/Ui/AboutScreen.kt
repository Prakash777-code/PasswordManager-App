package com.example.vaultx.Ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import com.example.vaultx.R
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class AboutScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)

        val btnGoBack = findViewById<MaterialButton>(R.id.btnGoBack)

        btnGoBack.setOnClickListener {
            onBackPressed()
        }
    }
}