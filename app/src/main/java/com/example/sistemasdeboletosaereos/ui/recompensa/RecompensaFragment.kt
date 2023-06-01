package com.example.sistemasdeboletosaereos.ui.recompensa

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.databinding.FragmentRecompensaBinding
import com.example.sistemasdeboletosaereos.databinding.FragmentVuelosBinding
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.example.sistemasdeboletosaereos.db.VuelosEntity
import com.example.sistemasdeboletosaereos.util.RecompensaAdapter
import com.example.sistemasdeboletosaereos.util.VueloAdapter
import com.google.firebase.auth.FirebaseAuth

class RecompensaFragment : Fragment() {
    private var _binding: FragmentRecompensaBinding? = null
    private var vuelos = ArrayList<VuelosEntity>()

    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecompensaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val auth = FirebaseAuth.getInstance()
        val db = DBHelper(requireContext())
        activity?.actionBar?.hide()

        val rvVuelos = binding.rvVuelosEncontrados
        rvVuelos.setHasFixedSize(true)
        rvVuelos.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false);
        // VUELOS DISPONIBLES SEGUN BUSQUEDA
        val vuelosCursor = db.getVuelos()

        if(vuelosCursor?.moveToFirst()== true){
            do {
                var vuelo: VuelosEntity = VuelosEntity(
                    vuelosCursor.getString(0), vuelosCursor.getString(1), vuelosCursor.getString(2),
                    vuelosCursor.getString(3), vuelosCursor.getString(4), vuelosCursor.getString(5),
                    vuelosCursor.getString(6), vuelosCursor.getString(7), vuelosCursor.getString(8),
                    vuelosCursor.getString(9), vuelosCursor.getString(10), vuelosCursor.getString(11),
                    vuelosCursor.getString(12), vuelosCursor.getString(13)
                )
                vuelos.add(vuelo)
            }while (vuelosCursor.moveToNext())

        }
        val adapter = RecompensaAdapter(vuelos)
        rvVuelos.adapter = adapter

        binding.tvPuntos.setText("Tienes " + db.getPuntosByUser(db.getIdUsuarioByUid(auth.uid!!)) + " puntos")
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFAB00")))
        activity?.window?.statusBarColor = Color.TRANSPARENT
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#FF00ACB6")))
        activity?.window?.statusBarColor = Color.parseColor("#00ACB6")
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