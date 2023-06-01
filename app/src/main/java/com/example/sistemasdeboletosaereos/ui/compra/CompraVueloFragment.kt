package com.example.sistemasdeboletosaereos.ui.compra

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.databinding.FragmentCompraVueloBinding
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.example.sistemasdeboletosaereos.db.RutasEntity
import com.example.sistemasdeboletosaereos.db.VuelosEntity
import com.example.sistemasdeboletosaereos.util.VueloAdapter
import com.google.firebase.auth.FirebaseAuth

class CompraVueloFragment : Fragment() {
    private var _binding: FragmentCompraVueloBinding? = null
    private var vuelos = ArrayList<VuelosEntity>()

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompraVueloBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val db = DBHelper(requireContext())
        val auth= FirebaseAuth.getInstance()
        Log.i("USER-SESSION", "Usuario en sesion " + auth.currentUser?.uid)

        // PAISES
        val spn = binding.dropdownMenu

        val destinos = db.getDestinos()
        val rutas = mutableListOf<RutasEntity>()
        var paisSelect : Int = 0
        var paises = ArrayList<String>()
        if(spn != null){

            if(destinos?.moveToFirst()== true){
                do {
                    var ruta: RutasEntity = RutasEntity(destinos.getInt(0), destinos.getString(1), destinos.getString(2))
                    rutas.add(ruta)
                    paises.add(destinos.getString(2))
                }while (destinos.moveToNext())

            }

            val adapterDropdown = ArrayAdapter(requireContext(),R.layout.list_dropdown_pais, paises)
            spn.setAdapter(adapterDropdown)

            spn.setOnItemClickListener { adapterView, view, position, id ->
                Log.i("SELECT-PAIS", "Pais seleccionado " + rutas.get(position).id + " - " + rutas.get(position).destino)
                paisSelect = rutas.get(position).id
            }

            val rvVuelos = binding.rvVuelosEncontrados
            rvVuelos.setHasFixedSize(true)
            rvVuelos.layoutManager = LinearLayoutManager(requireContext())
//                addVuelos()
            val adapter = VueloAdapter(vuelos, 1)
            rvVuelos.adapter = adapter

            binding.btnSearch.setOnClickListener {
                vuelos.clear()
                val precioDesde = binding.editTextPrecioDesde.text.toString()
                val precioHasta = binding.editTextPrecioHasta.text.toString()

                // VUELOS DISPONIBLES SEGUN BUSQUEDA
                val vuelosCursor = db.buscarVuelos(paisSelect.toString(), precioDesde, precioHasta)

                if(vuelosCursor?.moveToFirst()== true){
                    do {
                        var vuelo: VuelosEntity = VuelosEntity(
                            vuelosCursor.getString(0), vuelosCursor.getString(1), vuelosCursor.getString(2),
                            vuelosCursor.getString(3), vuelosCursor.getString(4), vuelosCursor.getString(5),
                            vuelosCursor.getString(6), vuelosCursor.getString(7), vuelosCursor.getString(8),
                            vuelosCursor.getString(9), vuelosCursor.getString(10), vuelosCursor.getString(11),
                            vuelosCursor.getString(12), vuelosCursor.getString(12)
                        )
                        vuelos.add(vuelo)
                    }while (vuelosCursor.moveToNext())

                }

                adapter.notifyDataSetChanged()

            }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun addVuelos(){
//        vuelos.add(
//            VuelosEntity(1, 1,
//                "EL SALVADOR", "COSTA RICA",
//                "25/05/2023", "05/06/2023")
//        )
//
//        vuelos.add(
//            VuelosEntity(1, 1,
//                "EL SALVADOR", "GUATEMALA",
//                "31/05/2023", "05/06/2023")
//        )
//
//        vuelos.add(
//            VuelosEntity(1, 1,
//                "EL SALVADOR", "Mexico",
//                "31/05/2023", "05/06/2023")
//        )
//    }

}