package com.example.sistemasdeboletosaereos.ui.vuelo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sistemasdeboletosaereos.databinding.FragmentVuelosBinding
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.example.sistemasdeboletosaereos.db.VuelosEntity
import com.example.sistemasdeboletosaereos.util.VueloAdapter
import com.google.firebase.auth.FirebaseAuth

class VuelosFragment: Fragment() {
    private var _binding: FragmentVuelosBinding? = null
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
        _binding = FragmentVuelosBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val db = DBHelper(requireContext())
        val auth = FirebaseAuth.getInstance()

        Log.i("SESION-USER", "USUARIO EN SESION: " + auth.currentUser?.uid!!)
        Log.i("SESION-USER", "USUARIO EN SESION: " + db.getIdUsuarioByUid(auth.uid!!))

        val misVuelosCursor = db.getVuelosByUser(db.getIdUsuarioByUid(auth.uid!!))

        if(misVuelosCursor?.moveToFirst()== true){
            do {
                var vuelo: VuelosEntity = VuelosEntity(
                    misVuelosCursor.getString(0), misVuelosCursor.getString(1), misVuelosCursor.getString(2),
                    misVuelosCursor.getString(3), misVuelosCursor.getString(4), misVuelosCursor.getString(5),
                    misVuelosCursor.getString(6), misVuelosCursor.getString(7), misVuelosCursor.getString(8),
                    misVuelosCursor.getString(9), misVuelosCursor.getString(10), misVuelosCursor.getString(11),
                    misVuelosCursor.getString(12), misVuelosCursor.getString(13), misVuelosCursor.getString(14)
                )
                vuelos.add(vuelo)
            }while (misVuelosCursor.moveToNext())

        }

        val rvVuelos = binding.rvVuelos
        rvVuelos.setHasFixedSize(true)
        rvVuelos.layoutManager = LinearLayoutManager(requireContext())
        addVuelos()
        val adapter = VueloAdapter(vuelos, 2)
        rvVuelos.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addVuelos(){
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
    }
}