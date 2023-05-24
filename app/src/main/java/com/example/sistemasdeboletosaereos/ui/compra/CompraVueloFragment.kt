package com.example.sistemasdeboletosaereos.ui.compra

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sistemasdeboletosaereos.R

class CompraVueloFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.i("COMPRA-VUELO", "Iniciando pantalla de compra: " + (arguments?.getString("arg") ?: ""))
        return inflater.inflate(R.layout.fragment_compra_vuelo, container, false)
    }

}