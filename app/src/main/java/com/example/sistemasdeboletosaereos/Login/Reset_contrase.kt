package com.example.sistemasdeboletosaereos.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.sistemasdeboletosaereos.MainActivity
import com.example.sistemasdeboletosaereos.R
import com.google.firebase.auth.FirebaseAuth

class Reset_contrase : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var username: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_contrase)
        auth = FirebaseAuth.getInstance()
        username=findViewById(R.id.username)

    }
    private fun sendPasswordResetEmail(email: String) {

    }

    private fun Accion(){

        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun Resetpass(view: View) {

        val email = username.text.toString().trim()

        if (email.isNotEmpty()) {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Correo electrónico de recuperación enviado", Toast.LENGTH_LONG).show()
                        Accion()
                    } else {
                        Toast.makeText(this, "Error al enviar el correo electrónico de recuperación", Toast.LENGTH_LONG).show()
                    }
                }

        } else {
            Toast.makeText(this, "Por favor, ingrese su correo electrónico", Toast.LENGTH_SHORT).show()
        }
    }
}