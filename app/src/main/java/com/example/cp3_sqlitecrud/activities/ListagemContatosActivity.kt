package com.example.cp3_sqlitecrud.activities

import com.example.cp3_sqlitecrud.adapter.ContatoAdapter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cp3_sqlitecrud.R
import com.example.cp3_sqlitecrud.database.ContatoDAO

class ListagemContatosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var contatoAdapter: ContatoAdapter
    private lateinit var contatoDAO: ContatoDAO
    private lateinit var btnAdicionar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_contato)

        contatoDAO = ContatoDAO(this)
        recyclerView = findViewById(R.id.recyclerViewContatos)
        btnAdicionar = findViewById(R.id.btnAdicionar)

        recyclerView.layoutManager = LinearLayoutManager(this)

        btnAdicionar.setOnClickListener {
            val intent = Intent(this, AdicionarContatoActivity::class.java)
            startActivity(intent)
        }

        atualizarLista()
    }

    private fun atualizarLista() {
        val contatos = contatoDAO.listarContatos()
        contatoAdapter = ContatoAdapter(contatos) { contato ->
            val intent = Intent(this, DetalhesContatoActivity::class.java)
            intent.putExtra("contato_id", contato.id)
            startActivity(intent)
        }
        recyclerView.adapter = contatoAdapter
    }
}
