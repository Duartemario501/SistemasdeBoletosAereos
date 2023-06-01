package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.google.android.play.integrity.internal.m

lateinit var dbHelper: DBHelper
class AerolineaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aerolinea)
        dbHelper= DBHelper(this)

    }
}