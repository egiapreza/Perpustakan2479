package com.example.perpustakan2479

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Perpustakan_hapus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perpustakan_hapus)

        val id_buku_terpilih:String = intent.getStringExtra("id_buku_terpilih").toString()

        val dbperpustakaan: SQLiteDatabase = openOrCreateDatabase("perpustakaan", MODE_PRIVATE, null)

        val query = dbperpustakaan.rawQuery("DELETE FROM buku WHERE id_buku= '$id_buku_terpilih'", null)
        query.moveToNext()

        val geser:Intent = Intent(this, Perpustakaan::class.java)
        startActivity(geser)
    }
}