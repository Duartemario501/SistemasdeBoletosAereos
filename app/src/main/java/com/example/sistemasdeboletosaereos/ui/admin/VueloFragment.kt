package com.example.sistemasdeboletosaereos.ui.admin

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.databinding.FragmentVuelosBinding
import com.example.sistemasdeboletosaereos.db.AvionesEntity
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.example.sistemasdeboletosaereos.db.RutasEntity
import com.example.sistemasdeboletosaereos.db.VuelosEntity
import com.example.sistemasdeboletosaereos.util.VueloAdapter
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Calendar

class VueloFragment : Fragment() {
    private var _binding : FragmentVuelosBinding? = null
    private val binding get() = _binding!!
    private var data = ArrayList<VuelosEntity>()
    private var rutaSelect : Int = 0
    private var avionSelect : Int = 0
    private var aerolineaSelect : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVuelosBinding.inflate(inflater, container, false)
        val db = DBHelper(requireContext())

        val btnAdd = binding.btnAddVuelo
        val rvVuelos = binding.rvVuelos
        rvVuelos.setHasFixedSize(true)
        rvVuelos.layoutManager = LinearLayoutManager(requireContext())

        val vuelosCursor = db.getVuelos()
        if(vuelosCursor?.moveToFirst()== true){
            do {
                val vuelo = VuelosEntity(
                    vuelosCursor.getString(0), vuelosCursor.getString(1), vuelosCursor.getString(2),
                    vuelosCursor.getString(3), vuelosCursor.getString(4), vuelosCursor.getString(5),
                    vuelosCursor.getString(6), vuelosCursor.getString(7), vuelosCursor.getString(8),
                    vuelosCursor.getString(9), vuelosCursor.getString(10), vuelosCursor.getString(11),
                    vuelosCursor.getString(12), vuelosCursor.getString(13)
                )
                data.add(vuelo)
            }while (vuelosCursor.moveToNext())

        }
        val adapter = VueloAdapter(data, 3)
        rvVuelos.adapter = adapter

        btnAdd.setOnClickListener {
            Log.i("ADD-VUELO", "AQUI")
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.add_vuelo_dialog)


            //Cargando select
            val destinos = db.getDestinos()
            val aviones = db.getAviones()
            val aerolineas = db.getAerolineas()
            val rutas = mutableListOf<RutasEntity>()
            val av = mutableListOf<AvionesEntity>()
            val ae = mutableListOf<AvionesEntity>()
            val dataAvion = ArrayList<String>()
            val dataAerolinea = ArrayList<String>()
            val dataRuta = ArrayList<String>()
            if (destinos?.moveToFirst() == true) {
                do {
                    val ruta = RutasEntity(
                        destinos.getInt(0),
                        destinos.getString(1),
                        destinos.getString(2)
                    )
                    rutas.add(ruta)
                    dataRuta.add(destinos.getString(1).plus("-").plus(destinos.getString(2)))
                } while (destinos.moveToNext())

            }
            var adapterDropdown = ArrayAdapter(requireContext(),R.layout.list_dropdown_pais, dataRuta)
            val selectRuta: AutoCompleteTextView = dialog.findViewById(R.id.dropdown_menu_ruta)
            selectRuta.setAdapter(adapterDropdown)
            selectRuta.setOnItemClickListener{ adapterView, view, position, id ->
                rutaSelect = rutas.get(position).id
            }

            if(aviones?.moveToFirst() == true){
                do{
                    val avion = AvionesEntity(aviones.getInt(0), aviones.getString(1))
                    av.add(avion)
                    dataAvion.add(aviones.getString(1))
                }while (aviones.moveToNext())
            }
            adapterDropdown = ArrayAdapter(requireContext(),R.layout.list_dropdown_pais, dataAvion)
            val selectAvion: AutoCompleteTextView = dialog.findViewById(R.id.dropdown_menu_avion)
            selectAvion.setAdapter(adapterDropdown)
            selectAvion.setOnItemClickListener { adapterView, view, position, id ->
                avionSelect = av.get(position).id
            }

            if(aerolineas?.moveToFirst() == true){
                do{
                    val aero = AvionesEntity(aerolineas.getInt(0), aerolineas.getString(1))
                    ae.add(aero)
                    dataAerolinea.add(aerolineas.getString(1))
                }while (aerolineas.moveToNext())
            }
            adapterDropdown = ArrayAdapter(requireContext(),R.layout.list_dropdown_pais, dataAerolinea)
            val selectAerolinea: AutoCompleteTextView = dialog.findViewById(R.id.dropdown_menu_aerolinea)
            selectAerolinea.setAdapter(adapterDropdown)
            selectAerolinea.setOnItemClickListener { adapterView, view, position, id ->
                aerolineaSelect = ae.get(position).id
            }

            val btn: Button = dialog.findViewById(R.id.btn_action_vuelo)
            val fechaSalida: EditText = dialog.findViewById(R.id.edit_fecha_salida)
            val horaSalida: EditText = dialog.findViewById(R.id.edit_hora)
            val fechaRegreso: EditText = dialog.findViewById(R.id.edit_fecha_regreso)
            val descripcion: EditText = dialog.findViewById(R.id.edit_descripcion)
            val precio: EditText = dialog.findViewById(R.id.edit_precio)
            val clase: EditText = dialog.findViewById(R.id.edit_clase)

            fechaSalida.setOnClickListener {
                val calendar = Calendar.getInstance()
                val año = calendar.get(Calendar.YEAR)
                val mes = calendar.get(Calendar.MONTH)
                val día = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { _, selectedYear, selectedMonth, selectedDay ->
                        val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                        fechaSalida.setText(selectedDate)
                    },
                    año,
                    mes,
                    día
                )
                datePickerDialog.show()
            }

            fechaRegreso.setOnClickListener {
                val calendar = Calendar.getInstance()
                val año = calendar.get(Calendar.YEAR)
                val mes = calendar.get(Calendar.MONTH)
                val día = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { _, selectedYear, selectedMonth, selectedDay ->
                        val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                        fechaRegreso.setText(selectedDate)
                    },
                    año,
                    mes,
                    día
                )
                datePickerDialog.show()
            }
            val calendar = Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)

                horaSalida.setText(SimpleDateFormat("HH:mm").format(calendar.time))
            }

            horaSalida.setOnClickListener {
                TimePickerDialog(context, timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), true
                ).show()
            }

            btn.setOnClickListener {
                val idVuelo = data.size + 1
                Log.i("ADD-VUELO", "Agregando Vuelo. ID: " + idVuelo)
                try {
                    db.anyadirDatovuelo(
                        idVuelo.toString(),aerolineaSelect.toString(), rutaSelect.toString(),
                        avionSelect.toString(), fechaSalida.text.toString(), horaSalida.text.toString(),
                        fechaRegreso.text.toString(), descripcion.text.toString(), "",
                        precio.text.toString().toDouble()
                    )

                    db.anyadirDatotarifa(
                        idVuelo.toString(), idVuelo.toString(), clase.text.toString(),
                        precio.text.toString(), "50"
                    )

                    data.clear()
                    val vuelosCursor = db.getVuelos()
                    if(vuelosCursor?.moveToFirst()== true){
                        do {
                            val vuelo = VuelosEntity(
                                vuelosCursor.getString(0), vuelosCursor.getString(1), vuelosCursor.getString(2),
                                vuelosCursor.getString(3), vuelosCursor.getString(4), vuelosCursor.getString(5),
                                vuelosCursor.getString(6), vuelosCursor.getString(7), vuelosCursor.getString(8),
                                vuelosCursor.getString(9), vuelosCursor.getString(10), vuelosCursor.getString(11),
                                vuelosCursor.getString(12), vuelosCursor.getString(13)
                            )
                            data.add(vuelo)
                        }while (vuelosCursor.moveToNext())
                        adapter.setFilteredList(data)
                    }
                    Snackbar.make(
                        requireView(),
                        "Nuevo vuelo creado con exito",
                        Snackbar.LENGTH_LONG
                    ).setAction("Action", null).show()
                }catch (e: Exception){
                    Snackbar.make(
                        requireView(),
                        "Error creando vuelo",
                        Snackbar.LENGTH_LONG
                    ).setAction("Action", null).show()
                }


                dialog.dismiss()
            }

            dialog.show()
        }

        return binding.root
    }
}