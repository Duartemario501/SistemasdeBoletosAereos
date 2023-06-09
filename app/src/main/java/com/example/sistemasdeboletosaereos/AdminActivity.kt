package com.example.sistemasdeboletosaereos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sistemasdeboletosaereos.ui.admin.AdminFragment

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AdminFragment.newInstance())
                .commitNow()
        }
    }
}