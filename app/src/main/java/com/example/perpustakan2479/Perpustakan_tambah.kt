package com.example.perpustakan2479

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayOutputStream

class Perpustakan_tambah : AppCompatActivity() {
    var urlgambar:Uri? = null
    var iv_foto:ImageView? = null
    var bitmapgambar:Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perpustakan_tambah)

        val edit_judul:EditText = findViewById(R.id.edit_judul)
        val edit_penulis:EditText = findViewById(R.id.edit_penulis)
        val edit_jenis:EditText = findViewById(R.id.edit_jenis)
        val btn_simpan:ImageButton = findViewById(R.id.btn_simpan)

        iv_foto = findViewById(R.id.iv_foto)

        iv_foto?.setOnClickListener {
            val bukagambar: Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pilih_gambar.launch(bukagambar)
        }

        btn_simpan.setOnClickListener {
            val isi_judul: String = edit_judul.text.toString()
            val isi_penulis: String = edit_penulis.text.toString()
            val isi_jenis: String = edit_jenis.text.toString()

            val dis = ByteArrayOutputStream()
            bitmapgambar?.compress(Bitmap.CompressFormat.JPEG, 100, dis)
            val  bytearraygambar = dis.toByteArray()

            val dbperpustakaan: SQLiteDatabase = openOrCreateDatabase("perpustakaan", MODE_PRIVATE, null)

            val sql = "INSERT INTO buku(judul,penulis,jenis,cover) VALUES (?,?,?,?)"
            val statement = dbperpustakaan.compileStatement(sql)
            statement.clearBindings()
            statement.bindString(1, isi_judul)
            statement.bindString(2, isi_penulis)
            statement.bindString(3, isi_jenis)
            statement.bindBlob(4,bytearraygambar)
            statement.executeInsert()

            //geser
            val geser:Intent = Intent(this, Perpustakaan::class.java)
            startActivity(geser)
        }
    }

    val pilih_gambar = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val gambardiperoleh = it.data

            if (gambardiperoleh!=null) {
                urlgambar = gambardiperoleh.data

                bitmapgambar = MediaStore.Images.Media.getBitmap(contentResolver, urlgambar)
                iv_foto?.setImageBitmap(bitmapgambar)
            }

        }
    }
}