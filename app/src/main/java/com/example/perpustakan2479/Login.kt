package com.example.perpustakan2479

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val edit_nama:EditText = findViewById(R.id.edit_nama)
        val edit_password:EditText = findViewById(R.id.edit_password)
        val btn_login:Button = findViewById(R.id.btn_login)

        btn_login.setOnClickListener {

            val isi_nama:String = edit_nama.text.toString()
            val isi_password:String = edit_password.text.toString()

            val dbperpustakaan:SQLiteDatabase = openOrCreateDatabase("perpustakaan", MODE_PRIVATE, null)

            val query = dbperpustakaan.rawQuery("SELECT * FROM user WHERE nama='$isi_nama' AND password='$isi_password'",null )
            val cek = query.moveToNext()

            if (cek) {

                val id_user = query.getString(0)
                val nama    = query.getString(1)
                val password = query.getString(2)

                val session:SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
                val keliling = session.edit()
                keliling.putString("id_user",id_user)
                keliling.putString("nama", nama)
                keliling.putString("password", password)
                keliling.commit()

                val geser:Intent = Intent(this,Perpustakaan::class.java)
                startActivity(geser)
            } else {
                Toast.makeText(this, "User atau password yang anda masukin salah!", Toast.LENGTH_LONG).show()
            }
        }
    }
}