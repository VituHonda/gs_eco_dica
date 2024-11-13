package com.example.gs_eco_dica

import EcoDicasAdapter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.gs_eco_dica.model.EcoDicaModel
import com.example.gs_eco_dica.viewmodel.EcoDicasViewModel
import com.example.gs_eco_dica.viewmodel.EcoDicasViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: EcoDicasViewModel
    private lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchView = findViewById(R.id.searchView)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText ?: "")
                return true
            }
        })


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

    private fun filterList(text: String) {
        val filteredList = mutableListOf<EcoDicaModel>()
        
        for (item in itemList) {
            if (item.getItemName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
        } else {
            // Handle the non-empty filtered list here
        }
    }


    private fun showIntegrantesDialog() {
        val integrantes = listOf("Vitor Tanabe RM: 93226", "Thomas Pflug RM: 92915")

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