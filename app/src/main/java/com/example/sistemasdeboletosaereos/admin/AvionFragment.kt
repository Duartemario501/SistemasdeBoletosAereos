package com.example.sistemasdeboletosaereos.admin

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.databinding.FragmentAvionBinding
import com.example.sistemasdeboletosaereos.db.AvionesEntity
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.example.sistemasdeboletosaereos.util.AvionAdapter
import com.google.android.material.snackbar.Snackbar

class AvionFragment : Fragment() {
    private var _binding: FragmentAvionBinding? = null

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
        _binding = FragmentAvionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val db = DBHelper(requireContext())

        var aviones = ArrayList<AvionesEntity>()

        val rv = binding.rvAviones
        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(context)

        val avionesCursor = db.getAviones()
        if(avionesCursor?.moveToFirst()== true){
            do {
                var avion: AvionesEntity = AvionesEntity(
                    avionesCursor.getString(0).toInt(), avionesCursor.getString(1)
                )
                aviones.add(avion)
            }while (avionesCursor.moveToNext())
        }
        val adapter = AvionAdapter(aviones)
        rv.adapter = adapter

        binding.btnAdd.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.add_avion_dialog)

            val modelo: EditText = dialog.findViewById(R.id.edit_modelo)
            val btnAdd: Button = dialog.findViewById(R.id.btn_add_avion)

            btnAdd.setOnClickListener {
                val id = aviones.size + 1
                db.anyadirDatoavion(id.toString(), modelo.text.toString());

                //Se recarga la informacion
                aviones.clear()
                val avionesCursor = db.getAviones()
                if(avionesCursor?.moveToFirst()== true){
                    do {
                        var avion: AvionesEntity = AvionesEntity(
                            avionesCursor.getString(0).toInt(), avionesCursor.getString(1)
                        )
                        aviones.add(avion)
                    }while (avionesCursor.moveToNext())
                }
                adapter.setFilteredList(aviones)
                Snackbar.make(
                    requireView(),
                    "Avion Agregado",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).show()
                dialog.dismiss()
            }

            dialog.show()
        }
        return root

    }
}