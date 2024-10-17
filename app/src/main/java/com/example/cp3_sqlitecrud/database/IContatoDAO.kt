package com.example.cp3_sqlitecrud.database

import com.example.cp3_sqlitecrud.model.Contato

interface IContatoDAO {
    fun adicionarContato(contato: Contato): Long
    fun atualizarContato(contato: Contato): Int
    fun deletarContato(id: Long): Int
    fun listarContatos(): List<Contato>
    fun obterContatoPorId(id: Long): Contato?
}