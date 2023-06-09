package com.example.sistemasdeboletosaereos.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.sistemasdeboletosaereos.AdminActivity
import com.example.sistemasdeboletosaereos.MainActivity
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LoginActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var txtContraseña: EditText
    private lateinit var txtuser: EditText
    private lateinit var txtNombre: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var dbLocal: DBHelper
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        dbLocal = DBHelper(this)

        txtContraseña=findViewById(R.id.password)
        txtuser=findViewById(R.id.username)
        progressBar=findViewById(R.id.barra)
        auth= FirebaseAuth.getInstance()
        onBackPressedDispatcher.addCallback(this, backPressedCallback)



    }
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
            txtContraseña.setText("")
            txtuser.setText("")
        } else {
            // Clase para representar un usuariodata
            class User(
                val uid: String,
                val role: String
            )

            // Función para obtener el rol de un usuario a partir de su UID
            fun getUserRole(uid: String, callback: (String?) -> Unit) {
                val ref = FirebaseDatabase.getInstance().getReference("users").child(uid)
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


            val user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid.toString()
            getUserRole(uid) { role ->
                if (role == "admin") {
                    // El usuario es administrador
                    startActivity(Intent(this, AdminActivity::class.java))
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
            txtContraseña.setText("")
            txtuser.setText("")

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
                                    val userRef = db.getReference("User").child(uid)
                                    userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                                            //AGREGANDO USUARIO EN BASE LOCAL
                                            Log.i("VALIDACION", "USUARIO NEW: " + dbLocal.getIdUsuarioByUid(currentUser.uid!!).equals("0"))
                                            if(dbLocal.getIdUsuarioByUid(currentUser.uid!!).equals("0"))
                                                dbLocal.anyadirDatopasajero(dbLocal.getLastIdUsuario(), user, "", currentUser.uid!!)
                                            val role = dataSnapshot.child("role").getValue(String::class.java)
                                            // Redirigir al usuario a la pantalla correspondiente en función de su rol
                                            when (role) {
                                                "admin" -> AccionAdmin()
                                                "usuario" -> AccionUsuario()
                                                else -> Toast.makeText(
                                                    this@LoginActivity,
                                                    "Rol no válido.",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                            //INIT DB
                                            val db = DBHelper(applicationContext);
                                            if(db.getAviones()?.moveToFirst() == false){
                                                db.llenarDB()
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
                                    txtContraseña.setText("")
                                    txtuser.setText("")
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Error al verificar la cuenta",
                                    Toast.LENGTH_LONG
                                ).show()
                                txtContraseña.setText("")
                                txtuser.setText("")
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
                    txtContraseña.setText("")
                    txtuser.setText("")
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

        startActivity(Intent(this,AdminActivity::class.java))
    }
    private fun esCorreoValido(correo: String): Boolean {
        return if (correo.isEmpty()) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
        }



    }
    private fun rol(){

    }



}