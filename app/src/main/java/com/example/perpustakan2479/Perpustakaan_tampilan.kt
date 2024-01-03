package com.example.perpustakan2479

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Perpustakaan_tampil (val ini:Context, val id_buku:MutableList<String>, val judul:MutableList<String>, val penulis:MutableList<String>, val jenis:MutableList<String>,
                           val cover:MutableList<Bitmap>) : RecyclerView.Adapter<Perpustakaan_tampil.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Perpustakaan_tampil.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.perpustakan_tampilan, parent, false)
        return ViewHolder(view)
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txt_judul:TextView = itemView.findViewById(R.id.txt_judul)
        val txt_penulis:TextView = itemView.findViewById(R.id.txt_penulis)
        val txt_jenis_buku:TextView = itemView.findViewById(R.id.txt_jenis_buku)
        val iv_cover:ImageView = itemView.findViewById(R.id.iv_cover)
        val btn_hapus:ImageButton = itemView.findViewById(R.id.btn_hapus)
        val btn_edit:ImageButton = itemView.findViewById(R.id.btn_edit)
    }

    override fun getItemCount(): Int {
        return judul.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_judul.text = judul.get(position)
        holder.txt_penulis.text = penulis.get(position)
        holder.txt_jenis_buku.text = jenis.get(position)
        holder.iv_cover.setImageBitmap(cover.get(position))

        holder.btn_hapus.setOnClickListener {
           val id_buku_terpilih:String = id_buku.get(position)

            val geser:Intent = Intent(ini, Perpustakan_hapus::class.java)
            geser.putExtra("id_buku_terpilih", id_buku_terpilih)
            ini.startActivity(geser)
        }

        holder.btn_edit.setOnClickListener{
            val id_buku_terpilih:String = id_buku.get(position)

            val geser:Intent = Intent(ini, Perpustakaan_edit::class.java)
            geser.putExtra("id_buku_terpilih", id_buku_terpilih)
            ini.startActivity(geser)
        }

    }
}