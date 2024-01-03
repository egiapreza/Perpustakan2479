package com.example.perpustakan2479

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class Perpustakaan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perpustakan)

        val rv_perpustakaan:RecyclerView = findViewById(R.id.rv_perpustakan)

        val txt_nama_user:TextView = findViewById(R.id.txt_nama_user)
        val txt_password:TextView = findViewById(R.id.txt_password)

        val keliling:SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        var nama_pelogin:String = keliling.getString("nama", null). toString()
        txt_nama_user.text = nama_pelogin

        val masuk:SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        var password:String = masuk.getString("password", null). toString()
        txt_password.text = password

        val btn_logout:ImageButton = findViewById(R.id.btn_logout)
        btn_logout.setOnClickListener {
            val editkeliling = keliling.edit()
            editkeliling.clear()
            editkeliling.commit()

            val keluar:Intent = Intent(this, Login::class.java)
            startActivity(keluar)
            finish()
        }

        val id_buku:MutableList<String> = mutableListOf()
        val judul:MutableList<String> = mutableListOf()
        val penulis:MutableList<String> = mutableListOf()
        val jenis:MutableList<String> = mutableListOf()
        val cover:MutableList<Bitmap> = mutableListOf()

        val dbperpustakaan:SQLiteDatabase = openOrCreateDatabase("perpustakaan", MODE_PRIVATE, null)

        val gali_buku = dbperpustakaan.rawQuery("SELECT * FROM buku", null)

        while (gali_buku.moveToNext())
        {

            try {
                val dis = ByteArrayInputStream(gali_buku.getBlob(4))
                val gambarbitmap:Bitmap = BitmapFactory.decodeStream(dis)
                cover.add(gambarbitmap)
            } catch (e:Exception) {
                val gambarbitmap:Bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.bookimage)
                cover.add(gambarbitmap)
            }

            id_buku.add(gali_buku.getString(0))
            judul.add(gali_buku.getString(1))
            penulis.add(gali_buku.getString(2))
            jenis.add(gali_buku.getString(3))
        }

        val pt = Perpustakaan_tampil(this, id_buku, judul, penulis, jenis, cover)

        rv_perpustakaan.adapter = pt
        rv_perpustakaan.layoutManager = GridLayoutManager(this, 2)

        val btn_tambah:Button = findViewById(R.id.btn_tambah)
        btn_tambah.setOnClickListener {
            val geser:Intent = Intent(this, Perpustakan_tambah::class.java)
            startActivity(geser)
        }
    }
}