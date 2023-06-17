package com.example.sistemasdeboletosaereos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.sistemasdeboletosaereos.Login.LoginActivity
import com.example.sistemasdeboletosaereos.ui.admin.AdminFragment
import com.google.firebase.auth.FirebaseAuth


class AdminActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var backPressedTime: Long = 0

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (backPressedTime + 3000 > System.currentTimeMillis()) {
                isEnabled = false
                finishAffinity()
            } else {
                Toast.makeText(baseContext, "Presiona de nuevo para salir", Toast.LENGTH_SHORT).show()
            }
            backPressedTime = System.currentTimeMillis()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AdminFragment())
                .commitNow()

        }
        onBackPressedDispatcher.addCallback(this, backPressedCallback)


    }

    fun cerrar(view: View) {
        auth.signOut()
        accion()
    }
    private fun accion(){
        startActivity(Intent(this, LoginActivity::class.java))
    }


}