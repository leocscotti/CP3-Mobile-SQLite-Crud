package com.example.cp3_sqlitecrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cp3_sqlitecrud.activities.AdicionarContatoActivity
import com.example.cp3_sqlitecrud.activities.DetalhesContatoActivity
import com.example.cp3_sqlitecrud.activities.InformacoesActivity
import com.example.cp3_sqlitecrud.activities.ListagemContatosActivity
import com.example.cp3_sqlitecrud.database.ContatoDAO
import com.example.cp3_sqlitecrud.model.Contato
import com.example.cp3_sqlitecrud.adapter.ContatoAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContatoAdapter
    private lateinit var contatoDAO: ContatoDAO
    private lateinit var btnAddContato: Button
    private lateinit var btnInfo: Button
    private lateinit var btnContatosSalvos: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contatoDAO = ContatoDAO(this)


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ContatoAdapter(contatoDAO.listarContatos()) { contato ->
            abrirDetalhesContato(contato)
        }
        recyclerView.adapter = adapter

        btnAddContato = findViewById(R.id.btnAddContato)
        btnAddContato.setOnClickListener {
            val intent = Intent(this, AdicionarContatoActivity::class.java)
            startActivity(intent)
        }

        btnInfo = findViewById(R.id.btnInfo)
        btnInfo.setOnClickListener {
            val intent = Intent(this, InformacoesActivity::class.java)
            startActivity(intent)
        }

        btnContatosSalvos= findViewById(R.id.btnContatosSalvos)
        btnContatosSalvos.setOnClickListener{
            val intent= Intent(this, ListagemContatosActivity::class.java)
            startActivity(intent)
        }

    }

    private fun abrirDetalhesContato(contato: Contato) {
        val intent = Intent(this, DetalhesContatoActivity::class.java)
        intent.putExtra("contato_id", contato.id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        adapter.atualizarLista(contatoDAO.listarContatos())
    }
}
