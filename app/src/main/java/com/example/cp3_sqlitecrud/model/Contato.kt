package com.example.cp3_sqlitecrud.model

data class Contato(
    var id: Long = 0,
    var nome: String,
    var telefone: String,
    var email: String,
    var endereco: String,
    var observacoes: String
)
