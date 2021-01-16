package com.hendev.retrofittest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NotlarAdapter(private val mContext: Context, private val notlarListesi: List<Notlar>) :
    RecyclerView.Adapter<NotlarAdapter.cvHolder>() {

    inner class cvHolder(x: View) : RecyclerView.ViewHolder(x) {
        var cardView: CardView
        var txtDers: TextView
        var txtNot1: TextView
        var txtNot2: TextView

        init {
            cardView = x.findViewById(R.id.cardView)
            txtDers = x.findViewById(R.id.txtDers)
            txtNot1 = x.findViewById(R.id.txtNot1)
            txtNot2 = x.findViewById(R.id.txtNot2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cvHolder {
        val design = LayoutInflater.from(mContext).inflate(R.layout.card_tasarim, parent, false)
        return cvHolder(design)
    }

    override fun onBindViewHolder(holder: cvHolder, position: Int) {
        val not = notlarListesi[position]
        holder.txtDers.text = not.ders_adi
        holder.txtNot1.text = "1.Sınav: " + not.not1.toString()
        holder.txtNot2.text = "2.Sınav: " + not.not2.toString()

        holder.cardView.setOnClickListener {
            val intent = Intent(mContext,Detailed::class.java)
            intent.putExtra("tablo",not)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return notlarListesi.size
    }
}