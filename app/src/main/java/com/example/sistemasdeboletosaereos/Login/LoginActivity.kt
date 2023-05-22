package com.example.sistemasdeboletosaereos.Login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.sistemasdeboletosaereos.MainActivity
import com.example.sistemasdeboletosaereos.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException


class LoginActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var txtContraseña: EditText
    private lateinit var txtuser: EditText
    private lateinit var txtNombre: EditText
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtContraseña=findViewById(R.id.password)
        txtuser=findViewById(R.id.username)
        progressBar=findViewById(R.id.barra)
        auth= FirebaseAuth.getInstance()

    }


    fun NewCount(view: View) {
        startActivity(Intent(this,RegistroActivity::class.java))
    }

    fun forgot_password(view: View) {
        Toast.makeText(this, "Error en la autentificacion", Toast.LENGTH_LONG).show()

    }

    fun Ingresar(view: View) {

        progressBar.visibility = view.visibility
        signIn()



    }
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun signIn() {
        val user:String=txtuser.text.toString()
        val password:String=txtContraseña.text.toString()
        if (!isValidEmail(user)) {
            Toast.makeText(this, "La dirección de correo electrónico no es válida.", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(user, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Toast.makeText(this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show()
                    Accion()
                } else {
                    val errorCode = (task.exception as FirebaseAuthException).errorCode
                    when (errorCode) {
                        "ERROR_WRONG_PASSWORD" -> Toast.makeText(this, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show()
                        "ERROR_USER_NOT_FOUND" -> Toast.makeText(this, "Usuario no encontrado.", Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(this, "Error al iniciar sesión.", Toast.LENGTH_SHORT).show()
                    }
                    Toast.makeText(this, "Error al iniciar sesión.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    fun isEmailValid(email: String): Boolean {
        val user:String=txtuser.toString()
        return android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()
    }

    private fun Accion(){

        startActivity(Intent(this,MainActivity::class.java))
    }
    private fun esCorreoValido(correo: String): Boolean {
        return if (correo.isEmpty()) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
        }



    }



}