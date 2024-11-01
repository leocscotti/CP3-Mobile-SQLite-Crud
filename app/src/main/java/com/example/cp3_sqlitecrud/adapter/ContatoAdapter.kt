package com.example.cp3_sqlitecrud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cp3_sqlitecrud.R
import com.example.cp3_sqlitecrud.model.Contato

class ContatoAdapter(
    private var contatos: List<Contato>,
    private val onClick: (Contato) -> Unit
) : RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>() {

    class ContatoViewHolder(itemView: View, private val onClick: (Contato) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val txtNome: TextView = itemView.findViewById(R.id.txtNome)
        private val txtTelefone: TextView = itemView.findViewById(R.id.txtTelefone)
        private val txtEmail: TextView = itemView.findViewById(R.id.txtEmail)
        private val txtEndereco: TextView = itemView.findViewById(R.id.txtEndereco)
        private lateinit var contato: Contato

        init {
            itemView.setOnClickListener {
                onClick(contato)
            }
        }

        fun bind(contato: Contato) {
            this.contato = contato
            txtNome.text = contato.nome
            txtTelefone.text = contato.telefone
            txtEmail.text = contato.email
            txtEndereco.text = contato.endereco
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contato, parent, false)
        return ContatoViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        holder.bind(contatos[position])
    }

    override fun getItemCount(): Int {
        return contatos.size
    }

    fun atualizarLista(novosContatos: List<Contato>) {
        contatos = novosContatos
        notifyDataSetChanged()
    }
}
