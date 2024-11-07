package com.example.cp3_sqlitecrud.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3_sqlitecrud.R
import com.example.cp3_sqlitecrud.database.ContatoDAO
import com.example.cp3_sqlitecrud.model.Contato
import com.google.firebase.firestore.FirebaseFirestore

class AdicionarContatoActivity : AppCompatActivity() {

    private lateinit var edtNome: EditText
    private lateinit var edtTelefone: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtEndereco: EditText
    private lateinit var edtObservacoes: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnExcluir: Button
    private lateinit var contatoDAO: ContatoDAO
    private lateinit var firestore: FirebaseFirestore

    private var contatoId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_contato)

        // Inicializar Firebase e banco de dados local (SQLite)
        firestore = FirebaseFirestore.getInstance()
        contatoDAO = ContatoDAO(this)

        // Inicializar campos de entrada e botões
        edtNome = findViewById(R.id.edtNome)
        edtTelefone = findViewById(R.id.edtTelefone)
        edtEmail = findViewById(R.id.edtEmail)
        edtEndereco = findViewById(R.id.edtEndereco)
        edtObservacoes = findViewById(R.id.edtObservacoes)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnExcluir = findViewById(R.id.btnExcluir)

        // Receber ID do contato, se for uma atualização
        contatoId = intent.getLongExtra("contato_id", -1).takeIf { it != -1L }

        // Preencher campos se contatoId não for nulo
        contatoId?.let {
            val contato = contatoDAO.obterContatoPorId(it)
            contato?.let { c ->
                edtNome.setText(c.nome)
                edtTelefone.setText(c.telefone)
                edtEmail.setText(c.email)
                edtEndereco.setText(c.endereco)
                edtObservacoes.setText(c.observacoes)
            }
        }

        // Ações dos botões
        btnSalvar.setOnClickListener {
            if (contatoId == null) {
                salvarContato()
            } else {
                atualizarContato()
            }
        }

        btnExcluir.setOnClickListener {
            excluirContato()
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

        // Adicionar contato ao Firestore
        firestore.collection("Contato")
            .add(contato)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Contato salvo com sucesso no Firestore! ID: ${documentReference.id}", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao salvar contato no Firestore.", Toast.LENGTH_SHORT).show()
            }

        // Adicionar contato ao banco de dados local (SQLite)
        contatoDAO.adicionarContato(contato)
        Toast.makeText(this, "Contato salvo localmente!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun atualizarContato() {
        contatoId?.let { id ->
            val contato = Contato(
                id = id,
                nome = edtNome.text.toString(),
                telefone = edtTelefone.text.toString(),
                email = edtEmail.text.toString(),
                endereco = edtEndereco.text.toString(),
                observacoes = edtObservacoes.text.toString()
            )

            // Atualizar contato no Firestore
            firestore.collection("Contato").document(id.toString())
                .set(contato)
                .addOnSuccessListener {
                    Toast.makeText(this, "Contato atualizado no Firestore!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro ao atualizar contato no Firestore.", Toast.LENGTH_SHORT).show()
                }

            // Atualizar contato no banco de dados local (SQLite)
            contatoDAO.atualizarContato(contato)
            Toast.makeText(this, "Contato atualizado localmente!", Toast.LENGTH_SHORT).show()
            finish()
        } ?: run {
            Toast.makeText(this, "ID do contato inválido.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun excluirContato() {
        contatoId?.let { id ->
            firestore.collection("Contato").document(id.toString())
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Contato excluído com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro ao excluir contato.", Toast.LENGTH_SHORT).show()
                }
        } ?: run {
            Toast.makeText(this, "ID do contato inválido.", Toast.LENGTH_SHORT).show()
        }
    }
}