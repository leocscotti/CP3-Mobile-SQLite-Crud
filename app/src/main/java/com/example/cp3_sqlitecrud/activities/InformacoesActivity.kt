package com.example.cp3_sqlitecrud.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3_sqlitecrud.R

class InformacoesActivity : AppCompatActivity() {
    private lateinit var textViewInfos: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacoes)

        textViewInfos = findViewById(R.id.textViewInfos)

        val info = """
            Desenvolvedores:
            - Nome: Enzo Gallone
              RM: 551754

            - Nome: Leonardo Scotti
              RM: 550769
        """.trimIndent()

        textViewInfos.text = info
    }
}
