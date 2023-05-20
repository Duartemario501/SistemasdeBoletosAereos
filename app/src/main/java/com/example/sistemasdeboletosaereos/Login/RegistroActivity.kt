package com.example.sistemasdeboletosaereos.Login

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
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
import java.util.*
import android.widget.Spinner


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
        val spnAreaCode: Spinner = findViewById(R.id.spnAreaCode)


        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()

        dbReference=database.reference.child("User")

        txtFechaNacimiento.setOnClickListener { showDatePickerDialog()}
        supportActionBar?.hide()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, areaCodes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnAreaCode.setAdapter(adapter)

        val selectedItem = spnAreaCode.selectedItem as AreaCode

        txtContraseña.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = txtContraseña.text.toString()
                if (text.length < 6) {
                    Toast.makeText(this, "Por favor, Ingresa una contraseña con almenos  digitos", Toast.LENGTH_SHORT).show();
                    txtContraseña.setText("") // Establece el texto en una cadena vacía
                } else {

                }
            }
        }


    }

    data class AreaCode(val code: String, val country: String) {
        override fun toString(): String {
            return "$code - $country"
        }
    }
    val areaCodes = listOf(
        AreaCode("+1", "Estados Unidos"),
        AreaCode("+52", "México"),
        AreaCode("+44", "Reino Unido"),
        AreaCode("+503", "El Salvador"),
    )




    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                txtFechaNacimiento.setText(selectedDate)

            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }


    fun Registro(view: View){
        if (TextUtils.isEmpty(txtContraseña.getText().toString()) ||TextUtils.isEmpty(txtNombre.getText().toString()) ||TextUtils.isEmpty(txtFechaNacimiento.getText().toString()) ||TextUtils.isEmpty(txtTelefono.getText().toString()) ||TextUtils.isEmpty(txtCorreo.getText().toString()) ){
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        }else creacionCuenta()

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

    fun Regresar(view: View) {
        startActivity(Intent(this,LoginActivity::class.java))
    }
}