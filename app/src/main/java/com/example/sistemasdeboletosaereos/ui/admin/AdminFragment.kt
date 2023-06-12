package com.example.sistemasdeboletosaereos.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.databinding.FragmentAdminBinding

class AdminFragment : Fragment() {
    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.setTitle("Administración Soar")
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAdminBinding.inflate(inflater, container, false)
        val root = binding.root
        val framentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        binding.cardAvion.setOnClickListener {
            val home = AvionFragment()
            framentTransaction.replace(R.id.container, home)
                .addToBackStack(null)
            framentTransaction.commit()
        }
        binding.cardAerolinea.setOnClickListener {
            val aero = AerolineaFragment()
            framentTransaction.replace(R.id.container, aero)
                .addToBackStack(null)
            framentTransaction.commit()
        }

        binding.cardRuta.setOnClickListener {
            val ruta = RutaFragment()
            framentTransaction.replace(R.id.container, ruta)
                .addToBackStack(null)
            framentTransaction.commit()
        }

        binding.cardVuelo.setOnClickListener {
            val vuelo = VueloFragment()
            framentTransaction.replace(R.id.container, vuelo)
                .addToBackStack(null)
            framentTransaction.commit()
        }

        return  root
    }

}