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
        val btnShare = findViewById<MaterialButton>(R.id.btnShare)

        btnShare.setOnClickListener {

            val link = "https://drive.google.com/file/d/1U05Xc2Dp4lGZmrPY97BsZ4z25RcZikDE/view?usp=drivesdk"

            val message = """
            Hey! Check out this amazing password manager app 🚀
            Download it here:
            $link
            """.trimIndent()

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)

            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }

        btnGoBack.setOnClickListener {
            onBackPressed()
        }
    }
}