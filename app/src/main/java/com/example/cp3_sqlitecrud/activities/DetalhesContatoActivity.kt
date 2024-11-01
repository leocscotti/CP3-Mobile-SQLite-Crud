package com.example.cp3_sqlitecrud.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3_sqlitecrud.R
import com.example.cp3_sqlitecrud.database.ContatoDAO

class DetalhesContatoActivity : AppCompatActivity() {

    private lateinit var textNome: TextView
    private lateinit var textTelefone: TextView
    private lateinit var textEmail: TextView
    private lateinit var textEndereco: TextView
    private lateinit var textObservacoes: TextView
    private lateinit var btnEditar: Button
    private lateinit var btnDeletar: Button
    private lateinit var contatoDAO: ContatoDAO
    private var contatoId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_contato)

        contatoDAO = ContatoDAO(this)

        // Inicializa os campos de texto e botões
        textNome = findViewById(R.id.textNome)
        textTelefone = findViewById(R.id.textTelefone)
        textEmail = findViewById(R.id.textEmail)
        textEndereco = findViewById(R.id.textEndereco)
        textObservacoes = findViewById(R.id.textObservacoes)
        btnEditar = findViewById(R.id.btnEditar)
        btnDeletar = findViewById(R.id.btnDeletar)

        // Obtém o ID do contato enviado pela MainActivity
        contatoId = intent.getLongExtra("contato_id", 0)

        // Exibe os detalhes do contato
        exibirDetalhesContato()

        // Configura o botão Editar
        btnEditar.setOnClickListener {
            val intent = Intent(this, AdicionarContatoActivity::class.java)
            intent.putExtra("contato_id", contatoId)
            startActivity(intent)
        }

        // Configura o botão Deletar
        btnDeletar.setOnClickListener {
            deletarContato()
        }
    }

    private fun exibirDetalhesContato() {
        val contato = contatoDAO.obterContatoPorId(contatoId)
        if (contato != null) {
            textNome.text = contato.nome
            textTelefone.text = contato.telefone
            textEmail.text = contato.email
            textEndereco.text = contato.endereco
            textObservacoes.text = contato.observacoes
        } else {
            Toast.makeText(this, "Contato não encontrado.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun deletarContato() {
        val rowsAffected = contatoDAO.deletarContato(contatoId)
        if (rowsAffected > 0) {
            Toast.makeText(this, "Contato deletado com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Erro ao deletar contato.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        exibirDetalhesContato() // Atualiza a visualização dos detalhes ao retornar para a atividade
    }
}
