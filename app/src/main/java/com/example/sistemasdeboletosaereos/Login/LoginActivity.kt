package com.example.sistemasdeboletosaereos.Login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.sistemasdeboletosaereos.MainActivity
import com.example.sistemasdeboletosaereos.R
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var txtContraseña: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var auth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtContraseña=findViewById(R.id.username)
        txtCorreo=findViewById(R.id.password)
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
        val user:String=txtCorreo.toString()
        val password:String=txtContraseña.toString()
        if (!TextUtils.isEmpty(user)&&!TextUtils.isEmpty(password)){
            progressBar.visibility=view.visibility
            auth.createUserWithEmailAndPassword(user,password)
                .addOnCompleteListener(this){
                    task->
                    if(task.isSuccessful){
                        Accion()
                    }else{
                        Toast.makeText(this, "Error en la autentificacion", Toast.LENGTH_LONG).show()

                    }

                }
        }

    }
    private fun Accion(){
        startActivity(Intent(this,MainActivity::class.java))
    }


}