package com.example.sistemasdeboletosaereos.ui.admin

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.databinding.FragmentAerolineaBinding
import com.example.sistemasdeboletosaereos.db.AvionesEntity
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.example.sistemasdeboletosaereos.util.AvionAdapter
import com.google.android.material.snackbar.Snackbar

class AerolineaFragment : Fragment() {

    private var _binding: FragmentAerolineaBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAerolineaBinding.inflate(inflater, container, false)
        val db = DBHelper(requireContext())

        var aerolineas = ArrayList<AvionesEntity>()

        val rv = binding.rvAerolineas
        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(context)

        val aerolineasCursor = db.getAerolineas()
        if(aerolineasCursor?.moveToFirst()== true){
            do {
                var aerolinea: AvionesEntity = AvionesEntity(
                    aerolineasCursor.getString(0).toInt(), aerolineasCursor.getString(1)
                )
                aerolineas.add(aerolinea)
            }while (aerolineasCursor.moveToNext())
        }
        val adapter = AvionAdapter(aerolineas, "AEROLINEA")
        rv.adapter = adapter
        adapter.notifyDataSetChanged()

        binding.fbAddAerolinea.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.add_avion_dialog)

            val titulo: TextView = dialog.findViewById(R.id.txt_title)
            val nombre: EditText = dialog.findViewById(R.id.edit_modelo)
            val btnAdd: Button = dialog.findViewById(R.id.btn_add_avion)
            titulo.setText("Ingreso de aerolineas")
            btnAdd.setOnClickListener {
                val id = aerolineas.size + 1
                db.anyadirDatoaerolinea(id.toString(), nombre.text.toString());

                //Se recarga la informacion
                aerolineas.clear()
                val aerolineasCursor = db.getAerolineas()
                if(aerolineasCursor?.moveToFirst()== true){
                    do {
                        var avion: AvionesEntity = AvionesEntity(
                            aerolineasCursor.getString(0).toInt(), aerolineasCursor.getString(1)
                        )
                        aerolineas.add(avion)
                    }while (aerolineasCursor.moveToNext())
                }
                adapter.setFilteredList(aerolineas)
                Snackbar.make(
                    requireView(),
                    "Aerolinea Agregada",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).show()
                dialog.dismiss()
            }

            dialog.show()
        }

        return binding.root
    }

}