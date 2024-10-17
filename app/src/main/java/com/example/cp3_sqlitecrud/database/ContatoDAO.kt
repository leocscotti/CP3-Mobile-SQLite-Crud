package com.example.cp3_sqlitecrud.database

import android.content.Context
import com.example.cp3_sqlitecrud.model.Contato

class ContatoDAO(context: Context) : IContatoDAO {
    private val dbHelper = ContatoDatabaseHelper(context)

    override fun adicionarContato(contato: Contato): Long {
        return dbHelper.addContato(contato)
    }

    override fun atualizarContato(contato: Contato): Int {
        return dbHelper.updateContato(contato)
    }

    override fun deletarContato(id: Long): Int {
        return dbHelper.deleteContato(id)
    }

    override fun listarContatos(): List<Contato> {
        return dbHelper.getContatos()
    }

    override fun obterContatoPorId(id: Long): Contato? {
        return dbHelper.getContatoById(id)
    }
}