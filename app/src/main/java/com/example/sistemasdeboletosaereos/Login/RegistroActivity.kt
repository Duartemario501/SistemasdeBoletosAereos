package com.example.sistemasdeboletosaereos.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.sistemasdeboletosaereos.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.ref.Reference
import java.net.Authenticator


class RegistroActivity : AppCompatActivity() {

    private lateinit var txtContraseña:EditText
    private lateinit var txtCorreo:EditText
    private lateinit var txtNombre:EditText
    private lateinit var txtTelefono:EditText
    private lateinit var txtFechaNacimiento:EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        txtContraseña=findViewById(R.id.TxtContraseña)
        txtCorreo=findViewById(R.id.txtCorreo)
        txtNombre=findViewById(R.id.txtNombre)
        txtTelefono=findViewById(R.id.txtTelefono)
        txtFechaNacimiento=findViewById(R.id.TxtFechaNacimiento)

        progressBar=findViewById(R.id.progressBar)
        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()

        dbReference=database.reference.child("User")

    }

    fun Registro(view: View){
        creacionCuenta()
    }

    private fun creacionCuenta(){
        val nombre:String=txtNombre.text.toString()
        val correo:String=txtCorreo.text.toString()
        val contraseña:String=txtContraseña.text.toString()
        val telefono:String=txtTelefono.text.toString()
        val fecha:String=txtFechaNacimiento.text.toString()
        if (!TextUtils.isEmpty(nombre)&&!TextUtils.isEmpty(correo)&&!TextUtils.isEmpty(contraseña)&&!TextUtils.isEmpty(telefono)&&!TextUtils.isEmpty(fecha))
        {
            progressBar.visibility= View.VISIBLE
            auth.createUserWithEmailAndPassword(correo,contraseña)
                .addOnCompleteListener(this){
                    task->
                    if (task.isComplete){
                        val user:FirebaseUser?=auth.currentUser
                        verificacionCorreo(user)
                        val userBD= user?.uid?.let { dbReference.child(it) }

                        userBD?.child("nombre")?.setValue(nombre)
                        accion()
                    }
                }
        }

    }
    private fun accion(){
        startActivity(Intent(this,LoginActivity::class.java))
    }
    private fun verificacionCorreo(user: FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                task->
                if (task.isComplete){
                    Toast.makeText(this, "Correo Enviado", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, "Error al enciar el correo", Toast.LENGTH_LONG).show()
                }

            }
    }
}