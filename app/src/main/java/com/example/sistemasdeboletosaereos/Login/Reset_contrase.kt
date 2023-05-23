package com.example.sistemasdeboletosaereos.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.sistemasdeboletosaereos.R
import com.google.firebase.auth.FirebaseAuth

class Reset_contrase : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var txtuser: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_contrase)
        auth = FirebaseAuth.getInstance()

    }
    private fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Correo electrónico de recuperación enviado", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Error al enviar el correo electrónico de recuperación", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun Recuperar(view: View) {

        val email = txtuser.text.toString().trim()

        if (email.isNotEmpty()) {
            sendPasswordResetEmail(email)
        } else {
            Toast.makeText(this, "Por favor, ingrese su correo electrónico", Toast.LENGTH_SHORT).show()
        }
    }
}