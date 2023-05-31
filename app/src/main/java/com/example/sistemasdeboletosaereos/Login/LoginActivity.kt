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
import com.example.sistemasdeboletosaereos.util.Admin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


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
        startActivity(Intent(this,Reset_contrase::class.java))

    }

    fun Ingresar(view: View) {
        val user:String=txtuser.text.toString()
        val password:String=txtContraseña.text.toString()
        progressBar.visibility = view.visibility
        if (user.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese su usuario y contraseña.", Toast.LENGTH_LONG).show()
        } else {
            // Clase para representar un usuariodata
            class User(
                val uid: String,
                val role: String
            )

            // Función para obtener el rol de un usuario a partir de su UID
            fun getUserRole(uid: String, callback: (String?) -> Unit) {
                val ref = FirebaseDatabase.getInstance().getReference("user").child(uid)
                ref.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)
                        callback(user?.role)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        callback(null)
                    }
                })
            }

// Ejemplo de uso
            val user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid.toString()
            getUserRole(uid) { role ->
                if (role == "admin") {
                    // El usuario es administrador
                    startActivity(Intent(this, Admin::class.java))
                } else {
                    // El usuario no es administrador
                    signIn()

                }
            }



        }
    }


    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun signIn() {
        val user: String = txtuser.text.toString()
        val password: String = txtContraseña.text.toString()
        if (!isValidEmail(user)) {
            Toast.makeText(
                this,
                "La dirección de correo electrónico no es válida.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        auth.signInWithEmailAndPassword(user, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    if (currentUser == null) {
                        Toast.makeText(this, "Cuenta no verificada", Toast.LENGTH_LONG).show()
                    } else {
                        currentUser.reload().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val isVerified = currentUser.isEmailVerified
                                if (isVerified) {
                                    // Obtener el rol del usuario actual desde la Realtime Database
                                    val uid = currentUser.uid
                                    val db = FirebaseDatabase.getInstance()
                                    val userRef = db.getReference("user").child(uid)

                                    userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                                            val role = dataSnapshot.child("role").getValue(String::class.java)
                                            // Redirigir al usuario a la pantalla correspondiente en función de su rol
                                            when (role) {
                                                "Admin" -> AccionAdmin()
                                                "Usuario" -> AccionUsuario()
                                                else -> Toast.makeText(
                                                    this@LoginActivity,
                                                    "Rol no válido.",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }

                                        override fun onCancelled(databaseError: DatabaseError) {
                                            Toast.makeText(
                                                this@LoginActivity,
                                                "Error al obtener el rol del usuario.",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    })
                                } else {
                                    Toast.makeText(this, "Cuenta no verificada", Toast.LENGTH_LONG)
                                        .show()
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Error al verificar la cuenta",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                } else {
                    val errorCode = (task.exception as FirebaseAuthException).errorCode
                    when (errorCode) {
                        "ERROR_WRONG_PASSWORD" -> Toast.makeText(
                            this,
                            "Contraseña incorrecta.",
                            Toast.LENGTH_LONG
                        ).show()

                        "ERROR_USER_NOT_FOUND" -> Toast.makeText(
                            this,
                            "Usuario no encontrado.",
                            Toast.LENGTH_LONG
                        ).show()

                        else -> Toast.makeText(this, "Error al iniciar sesión.", Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(this, "Error al iniciar sesión.", Toast.LENGTH_LONG).show()
                }
            }
    }
    fun isEmailValid(email: String): Boolean {
        val user:String=txtuser.toString()
        return android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()
    }

    private fun AccionUsuario(){

        startActivity(Intent(this,MainActivity::class.java))
    }
    private fun AccionAdmin(){

        startActivity(Intent(this,Admin::class.java))
    }
    private fun esCorreoValido(correo: String): Boolean {
        return if (correo.isEmpty()) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
        }



    }



}