package com.example.sistemasdeboletosaereos.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.databinding.FragmentHomeBinding
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val auth = FirebaseAuth.getInstance()
        val bundle = Bundle()
        bundle.putString("arg", "VALOR")

        Log.i("SESION-USER", "USUARIO EN SESION: " + auth.currentUser?.uid!!)
        //INIT DB
        val db = DBHelper(requireContext());
        if(db.getVuelos()?.moveToFirst() == false){
            db.llenarDB()
        }

        //COMPRAR VUELOS
        binding.cardComprarVuelo.setOnClickListener {
            findNavController().navigate(R.id.nav_compra_vuelo, bundle)
        }

        //MIS VUELOS
        binding.cardMisVuelos.setOnClickListener {
            findNavController().navigate(R.id.nav_vuelos)
        }

        //RECOMPENSAS
        binding.cardRecompensas.setOnClickListener {
            findNavController().navigate(R.id.nav_recompensa)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}