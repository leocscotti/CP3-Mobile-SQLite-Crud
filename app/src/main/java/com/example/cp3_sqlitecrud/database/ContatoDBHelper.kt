package com.example.cp3_sqlitecrud.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.cp3_sqlitecrud.model.Contato

class ContatoDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {

    companion object {
        const val DATABASE_NAME = "contatos.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "contato"
        const val COLUMN_ID = BaseColumns._ID
        const val COLUMN_NOME = "nome"
        const val COLUMN_TELEFONE = "telefone"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_ENDERECO = "endereco"
        const val COLUMN_OBSERVACOES = "observacoes"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOME TEXT NOT NULL,
                $COLUMN_TELEFONE TEXT NOT NULL,
                $COLUMN_EMAIL TEXT,
                $COLUMN_ENDERECO TEXT,
                $COLUMN_OBSERVACOES TEXT
            )
        """
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addContato(contato: Contato): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOME, contato.nome)
            put(COLUMN_TELEFONE, contato.telefone)
            put(COLUMN_EMAIL, contato.email)
            put(COLUMN_ENDERECO, contato.endereco)
            put(COLUMN_OBSERVACOES, contato.observacoes)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getContatos(): List<Contato> {
        val db = readableDatabase
        val contatos = mutableListOf<Contato>()
        val cursor: Cursor = db.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_NOME ASC"
        )

        with(cursor) {
            while (moveToNext()) {
                val contato = Contato(
                    id = getLong(getColumnIndexOrThrow(COLUMN_ID)),
                    nome = getString(getColumnIndexOrThrow(COLUMN_NOME)),
                    telefone = getString(getColumnIndexOrThrow(COLUMN_TELEFONE)),
                    email = getString(getColumnIndexOrThrow(COLUMN_EMAIL)),
                    endereco = getString(getColumnIndexOrThrow(COLUMN_ENDERECO)),
                    observacoes = getString(getColumnIndexOrThrow(COLUMN_OBSERVACOES))
                )
                contatos.add(contato)
            }
        }
        cursor.close()
        return contatos
    }

    fun updateContato(contato: Contato): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOME, contato.nome)
            put(COLUMN_TELEFONE, contato.telefone)
            put(COLUMN_EMAIL, contato.email)
            put(COLUMN_ENDERECO, contato.endereco)
            put(COLUMN_OBSERVACOES, contato.observacoes)
        }

        return db.update(
            TABLE_NAME,
            values,
            "$COLUMN_ID = ?",
            arrayOf(contato.id.toString())
        )
    }

    fun deleteContato(id: Long): Int {
        val db = writableDatabase
        return db.delete(
            TABLE_NAME,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }

    fun getContatoById(id: Long): Contato? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        var contato: Contato? = null

        with(cursor) {
            if (moveToFirst()) {
                contato = Contato(
                    id = getLong(getColumnIndexOrThrow(COLUMN_ID)),
                    nome = getString(getColumnIndexOrThrow(COLUMN_NOME)),
                    telefone = getString(getColumnIndexOrThrow(COLUMN_TELEFONE)),
                    email = getString(getColumnIndexOrThrow(COLUMN_EMAIL)),
                    endereco = getString(getColumnIndexOrThrow(COLUMN_ENDERECO)),
                    observacoes = getString(getColumnIndexOrThrow(COLUMN_OBSERVACOES))
                )
            }
        }
        cursor.close()
        return contato
    }
}
