package com.example.gs_eco_dica

import EcoDicasAdapter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.gs_eco_dica.viewmodel.EcoDicasViewModel
import com.example.gs_eco_dica.viewmodel.EcoDicasViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: EcoDicasViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Eco Dicas"


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val ecoDicasAdapter = EcoDicasAdapter { dica ->
            viewModel.removeEcoDica(dica)
        }
        recyclerView.adapter = ecoDicasAdapter

        val button = findViewById<Button>(R.id.button)
        val buttonIntegrantes = findViewById<Button>(R.id.buttonIntegrantes)
        val editTextTitle = findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = findViewById<EditText>(R.id.editTextDescription)

        button.setOnClickListener {
            if (editTextTitle.text.isEmpty()) {
                editTextTitle.error = "Preencha um valor"
                return@setOnClickListener
            }

            if (editTextDescription.text.isEmpty()) {
                editTextDescription.error = "Preencha um valor"
                return@setOnClickListener
            }

            viewModel.addEcoDica(editTextTitle.text.toString(), editTextDescription.text.toString())
            editTextTitle.text.clear()
            editTextDescription.text.clear()
        }

        buttonIntegrantes.setOnClickListener {
            showIntegrantesDialog()
        }

        val viewModelFactory = EcoDicasViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EcoDicasViewModel::class.java)

        viewModel.ecoDicasLiveData.observe(this) { dicas ->
            ecoDicasAdapter.updateEcoDicas(dicas)
        }
    }

    private fun showIntegrantesDialog() {
        val integrantes = listOf("Vitor RM: 93226")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Integrantes")
        builder.setItems(integrantes.toTypedArray()) { _, which ->
            val selectedIntegrante = integrantes[which]
        }

        builder.setNegativeButton("Fechar") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }
}