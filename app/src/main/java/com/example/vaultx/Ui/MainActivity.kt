package com.example.vaultx.Ui

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.vaultx.Adapter.PasswordAdapter
import com.example.vaultx.Local.PasswordDatabse
import com.example.vaultx.R
import com.example.vaultx.Repository.PasswordRepository
import com.example.vaultx.ViewModel.PasswordViewModel
import com.example.vaultx.ViewModel.PasswordViewModelFactory
import com.example.vaultx.security.AuthManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PasswordViewModel
    private lateinit var adapter: PasswordAdapter
    private lateinit var searchBar: EditText
    private lateinit var recyclerView: RecyclerView
    private var scrollTop = false

    private val addPasswordLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                scrollTop = true
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AuthManager(this).authenticate(
            onSuccess = { initUI() },
            onError = { finish() }
        )
    }

    private fun initUI() {

        val dao = PasswordDatabse.getInstance(applicationContext).passwordDao()
        val repo = PasswordRepository(dao)
        val factory = PasswordViewModelFactory(repo)

        viewModel = ViewModelProvider(this, factory)[PasswordViewModel::class.java]

        recyclerView = findViewById(R.id.recyclerView)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        val btnAbout = findViewById<ImageButton>(R.id.btnAbout)
        searchBar = findViewById(R.id.searchBar)

        adapter = PasswordAdapter(
            onClick = { item ->
                val intent = Intent(this, AddEditActivity::class.java)
                intent.putExtra("password_item", item)
                addPasswordLauncher.launch(intent)
            },
            onDelete = { item ->
                viewModel.delete(item)
                Toast.makeText(this, "${item.title} deleted!", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter

        viewModel.passwords.observe(this) {
            adapter.submitList(it)
            if (scrollTop) {
                recyclerView.scrollToPosition(0)
                scrollTop = false
            }
        }

        fabAdd.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            addPasswordLauncher.launch(intent)
        }

        btnAbout.setOnClickListener {
            startActivity(Intent(this, AboutScreen::class.java))
        }

        searchBar.addTextChangedListener { text ->
            val query = text.toString().trim()
            if (text?.startsWith(" ") == true) {
                searchBar.setText(query)
                searchBar.setSelection(searchBar.text.length)
                return@addTextChangedListener
            }
            viewModel.setQuery(query)
        }
    }
}