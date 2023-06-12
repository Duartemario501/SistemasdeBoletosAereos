package com.example.sistemasdeboletosaereos.ui.admin

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
import com.example.sistemasdeboletosaereos.databinding.FragmentRutaBinding
import com.example.sistemasdeboletosaereos.db.AvionesEntity
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.example.sistemasdeboletosaereos.db.RutasEntity
import com.example.sistemasdeboletosaereos.util.RutaAdapter
import com.google.android.material.snackbar.Snackbar

class RutaFragment : Fragment() {
    private var _binding : FragmentRutaBinding? = null
    private val binding get() = _binding!!
    private var data = ArrayList<RutasEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRutaBinding.inflate(inflater, container, false)
        val db = DBHelper(requireContext())
        val rv = binding.rvRutas
        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(context)

        val rc = db.getDestinos()
        if(rc!!.moveToFirst()){
            do{
                val ruta = RutasEntity(rc.getInt(0), rc.getString(1), rc.getString(2))
                data.add(ruta)
            }while (rc.moveToNext())
        }
        val adapter = RutaAdapter(data)
        rv.adapter = adapter
        binding.btnAddRuta.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.add_ruta_dialog)

            val origen: EditText = dialog.findViewById(R.id.edit_origen)
            val destino: EditText = dialog.findViewById(R.id.edit_destino)
            val btnAdd: Button = dialog.findViewById(R.id.btn_action_ruta)

            btnAdd.setOnClickListener {
                val id = data.size + 1
                db.anyadirDatoruta(id.toString(), origen.text.toString(), destino.text.toString());

                //Se recarga la informacion
                data.clear()
                val rc = db.getDestinos()
                if(rc?.moveToFirst()== true){
                    do {
                        val ruta = RutasEntity(
                            rc.getInt(0), rc.getString(1), rc.getString(2)
                        )
                        data.add(ruta)
                    }while (rc.moveToNext())
                }
                adapter.setFilteredList(data)
                Snackbar.make(
                    requireView(),
                    "Ruta Agregada",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).show()
                dialog.dismiss()
            }

            dialog.show()
        }

        return binding.root
    }
}