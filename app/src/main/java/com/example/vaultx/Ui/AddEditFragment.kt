package com.example.vaultx.Ui

import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.vaultx.security.EncryptionManager
import com.example.vaultx.Models.PasswordItem
import com.example.vaultx.R
import com.example.vaultx.Util.PasswordStrengthChecker
import com.example.vaultx.ViewModel.PasswordViewModel
import androidx.core.widget.addTextChangedListener
import com.example.vaultx.Local.PasswordDatabse
import com.example.vaultx.Repository.PasswordRepository
import com.example.vaultx.ViewModel.PasswordViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AddEditActivity : AppCompatActivity() {

    private lateinit var viewModel: PasswordViewModel
    private val crypto = EncryptionManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add_edit)

        val dao = PasswordDatabse.getInstance(applicationContext).passwordDao()
        val repo = PasswordRepository(dao)
        val factory = PasswordViewModelFactory(repo)

        viewModel = ViewModelProvider(this, factory)[PasswordViewModel::class.java]

        val etTitle = findViewById<TextInputEditText>(R.id.etTitle)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnCopyPassword = findViewById<MaterialButton>(R.id.btnCopyPassword)
        val btnSavePassword = findViewById<MaterialButton>(R.id.btnSave)
        val btnCopyEmail = findViewById<ImageView>(R.id.btnCopyEmail)
        val tvStrength = findViewById<TextView>(R.id.tvStrength)
        val strengthBar = findViewById<ProgressBar>(R.id.strengthBar)
        val btnGoBack = findViewById<ImageButton>(R.id.btnBack)


        val selected: PasswordItem? = intent.getParcelableExtra("password_item")
        selected?.let {
            etTitle.setText(it.title)
            etEmail.setText(it.email)
            etPassword.setText(crypto.decrypt(it.password))
        }

        btnCopyPassword.setOnClickListener {

            val password = etPassword.text.toString().trim()
            copyToClipboard("Password",password)

        }

        btnCopyEmail.setOnClickListener {

            var email = etEmail.text.toString()
            copyToClipboard("Email",email)
        }

        btnSavePassword.setOnClickListener {

            val title = etTitle.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = crypto.encrypt(etPassword.text.toString())
            val passwordInput = etPassword.text.toString()

            if (title.isEmpty() || email.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selected == null) {

                viewModel.insert(
                    PasswordItem(
                        title = title,
                        email = email,
                        password = password,
                        createdAt = System.currentTimeMillis()
                    )
                )
                Toast.makeText(this,"Your password is safe now", Toast.LENGTH_SHORT).show()

            } else {

                viewModel.update(
                    PasswordItem(
                        id = selected.id,
                        title = title,
                        email = email,
                        password = password,
                        createdAt = selected.createdAt,
                        updatedAt = System.currentTimeMillis()
                    )
                )
                Toast.makeText(this,"Your password is updated and safe now", Toast.LENGTH_SHORT).show()
            }
            setResult(RESULT_OK)
            finish()
        }

        btnGoBack.setOnClickListener {
            onBackPressed()
        }

        val checker = PasswordStrengthChecker()
        etPassword.addTextChangedListener{

            val password = it.toString()
            val strength = checker.checkStrength(password)

            when(strength){

                PasswordStrengthChecker.Strength.WEAK -> {
                    tvStrength.text = "Weak password"
                    tvStrength.setTextColor(android.graphics.Color.parseColor("#D32F2F"))

                    strengthBar.progress = 30
                    strengthBar.progressDrawable.setColorFilter(
                        Color.parseColor("#D32F2F"),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }

                PasswordStrengthChecker.Strength.MEDIUM ->{
                    tvStrength.text = "Medium password"
                    tvStrength.setTextColor(android.graphics.Color.parseColor("#FFA000"))

                    strengthBar.progress = 65
                    strengthBar.progressDrawable.setColorFilter(
                        Color.parseColor("#FFA000"),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }

                PasswordStrengthChecker.Strength.STRONG ->{
                    tvStrength.text = "Strong password"
                    tvStrength.setTextColor(android.graphics.Color.parseColor("#2E7D32"))

                    strengthBar.progress = 100
                    strengthBar.progressDrawable.setColorFilter(
                        Color.parseColor("#2E7D32"),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }
            }
        }
    }
    private fun copyToClipboard(label: String, text: String) {
        if (text.isEmpty()) {
            Toast.makeText(this, "$label field is empty!", Toast.LENGTH_SHORT).show()
            return
        }

        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "$label copied", Toast.LENGTH_SHORT).show()
    }

}