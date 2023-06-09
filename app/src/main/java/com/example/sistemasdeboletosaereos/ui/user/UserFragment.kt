package com.example.sistemasdeboletosaereos.ui.user

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.databinding.FragmentUserBinding
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar

class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val auth = FirebaseAuth.getInstance()
        val db = DBHelper(requireContext());

        binding.TxtFechaNacimiento.setOnClickListener {
            val calendar = Calendar.getInstance()
            val año = calendar.get(Calendar.YEAR)
            val mes = calendar.get(Calendar.MONTH)
            val día = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    binding.TxtFechaNacimiento.setText(selectedDate)
                },
                año,
                mes,
                día
            )
            datePickerDialog.show()
        }

        val userCursor = db.getUsuarioByUid(auth.currentUser?.uid!!)
        if(userCursor.moveToFirst()){
            binding.editTextNombre.setText(userCursor.getString(1))
            binding.editTextCorreo.setText(userCursor.getString(2))
            binding.editTextPassword.setText(userCursor.getString(3))
            binding.editTextTelefono.setText(userCursor.getString(4))
            binding.TxtFechaNacimiento.setText(userCursor.getString(5))
        }

        binding.btnSave.setOnClickListener {
            //ACTUALIZAR DATOS DEL USUARIO
        }
        return root
    }

}