package com.example.perpustakan2479

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class Perpustakaan_edit : AppCompatActivity() {
    var urlgambar: Uri? = null
    var iv_foto:ImageView? = null
    var bitmapgambar:Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perpustakaan_edit)

        val id_buku_terpilih:String = intent.getStringExtra("id_buku_terpilih").toString()

        val dbperpustakaan:SQLiteDatabase = openOrCreateDatabase("perpustakaan", MODE_PRIVATE, null)
        val ambil = dbperpustakaan.rawQuery("SELECT * FROM buku WHERE id_buku= '$id_buku_terpilih'", null)
        ambil.moveToNext()

        val isi_judul:String = ambil.getString(1)
        val isi_penulis:String = ambil.getString(2)
        val isi_jenis:String = ambil.getString(3)
        val isi_cover:ByteArray = ambil.getBlob(4)

        val edit_judul:EditText = findViewById(R.id.edit_judul)
        val edit_penulis:EditText = findViewById(R.id.edit_penulis)
        val edit_jenis:EditText = findViewById(R.id.edit_jenis)
        val btn_simpan:ImageButton  = findViewById(R.id.btn_simpan)

        iv_foto = findViewById(R.id.iv_foto)


        edit_judul.setText(isi_judul)
        edit_penulis.setText(isi_penulis)
        edit_jenis.setText(isi_jenis)

        try {
            val dis = ByteArrayInputStream(isi_cover)
            val gambarbitmap: Bitmap = BitmapFactory.decodeStream(dis)
            iv_foto?.setImageBitmap(gambarbitmap)
        } catch (e:Exception) {
            val gambarbitmap: Bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.bookimage)
            iv_foto?.setImageBitmap(gambarbitmap)
        }

        iv_foto?.setOnClickListener {
            val bukagambar: Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pilih_gambar.launch(bukagambar)
        }

        btn_simpan.setOnClickListener {
            val judul_baru:String = edit_judul.text.toString()
            val penulis_baru:String = edit_penulis.text.toString()
            val jenis_baru:String = edit_jenis.text.toString()

            val dis = ByteArrayOutputStream()
            bitmapgambar?.compress(Bitmap.CompressFormat.JPEG, 100, dis)
            val  bytearraygambar = dis.toByteArray()

            val sql = "UPDATE buku SET judul=?, penulis=?, jenis=?, cover=? WHERE id_buku='$id_buku_terpilih'"
            val statement = dbperpustakaan.compileStatement(sql)
            statement.clearBindings()
            statement.bindString(1, isi_judul)
            statement.bindString(2, isi_penulis)
            statement.bindString(3, isi_jenis)
            statement.bindBlob(4,bytearraygambar)
            statement.executeUpdateDelete()

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