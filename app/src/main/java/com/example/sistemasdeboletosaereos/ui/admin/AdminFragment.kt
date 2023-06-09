package com.example.sistemasdeboletosaereos.ui.admin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.admin.AvionFragment
import com.example.sistemasdeboletosaereos.databinding.FragmentAdminBinding

class AdminFragment : Fragment() {
    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = AdminFragment()
    }

    private lateinit var viewModel: AdminViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)

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

        return  root
    }

}