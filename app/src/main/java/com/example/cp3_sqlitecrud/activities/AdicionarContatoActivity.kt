package com.example.cp3_sqlitecrud.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3_sqlitecrud.R
import com.example.cp3_sqlitecrud.database.ContatoDAO
import com.example.cp3_sqlitecrud.model.Contato

class AdicionarContatoActivity : AppCompatActivity() {

    private lateinit var edtNome: EditText
    private lateinit var edtTelefone: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtEndereco: EditText
    private lateinit var edtObservacoes: EditText
    private lateinit var btnSalvar: Button
    private lateinit var contatoDAO: ContatoDAO

    private var contatoId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_contato)

        contatoDAO = ContatoDAO(this)
        edtNome = findViewById(R.id.edtNome)
        edtTelefone = findViewById(R.id.edtTelefone)
        edtEmail = findViewById(R.id.edtEmail)
        edtEndereco = findViewById(R.id.edtEndereco)
        edtObservacoes = findViewById(R.id.edtObservacoes)
        btnSalvar = findViewById(R.id.btnSalvar)

        contatoId = intent.getLongExtra("contato_id", -1).takeIf { it != -1L }

        if (contatoId != null) {
            val contato = contatoDAO.obterContatoPorId(contatoId!!)
            if (contato != null) {
                edtNome.setText(contato.nome)
                edtTelefone.setText(contato.telefone)
                edtEmail.setText(contato.email)
                edtEndereco.setText(contato.endereco)
                edtObservacoes.setText(contato.observacoes)
            }
        }

        btnSalvar.setOnClickListener {
            if (contatoId == null) {
                salvarContato()
            } else {
                atualizarContato()
            }
        }
    }

    private fun salvarContato() {
        val contato = Contato(
            nome = edtNome.text.toString(),
            telefone = edtTelefone.text.toString(),
            email = edtEmail.text.toString(),
            endereco = edtEndereco.text.toString(),
            observacoes = edtObservacoes.text.toString()
        )

        val id = contatoDAO.adicionarContato(contato)
        if (id > 0) {
            Toast.makeText(this, "Contato salvo com sucesso! ID: $id", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Erro ao salvar contato.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun atualizarContato() {
        val contato = Contato(
            id = contatoId!!,
            nome = edtNome.text.toString(),
            telefone = edtTelefone.text.toString(),
            email = edtEmail.text.toString(),
            endereco = edtEndereco.text.toString(),
            observacoes = edtObservacoes.text.toString()
        )

        val rowsAffected = contatoDAO.atualizarContato(contato)
        if (rowsAffected > 0) {
            Toast.makeText(this, "Contato atualizado com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Erro ao atualizar contato.", Toast.LENGTH_SHORT).show()
        }
    }
}

