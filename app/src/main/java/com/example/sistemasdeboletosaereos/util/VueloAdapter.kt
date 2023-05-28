package com.example.sistemasdeboletosaereos.util

import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.db.VuelosEntity
import com.google.android.material.snackbar.Snackbar

class VueloAdapter(private var vuelos: List<VuelosEntity>) : RecyclerView.Adapter<VueloAdapter.VueloViewHolder>() {

    var context: Context? = null
    inner class VueloViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val logo: ImageView = itemView.findViewById(R.id.logoIv)
        val titleTv: TextView = itemView.findViewById(R.id.titleTv)
        val langDescTv: TextView = itemView.findViewById(R.id.langDesc)
        val price : TextView = itemView.findViewById(R.id.txtPrice)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)

        fun collapseExpandedView(){
            langDescTv.visibility = View.GONE
        }
    }
    fun setFilteredList(vuelos: List<VuelosEntity>) {
        this.vuelos = vuelos
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VueloViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        context = parent.context
        return VueloViewHolder(view)
    }

    override fun onBindViewHolder(holder: VueloViewHolder, position: Int) {

        val vuelo = vuelos[position]
        holder.logo.setImageResource(R.drawable.icon_home_viajes)
        holder.titleTv.text = vuelo.destino
        holder.langDescTv.text = "El vuelo esta agendando desde " +
                 vuelo.fecha_salida + " hasta " + vuelo.fecha_llegada
        holder.price.text = "$150.00"
        val isExpandable: Boolean = true
        holder.langDescTv.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.constraintLayout.setOnClickListener {
            notifyItemChanged(position , Unit)
        }
        holder.itemView.findViewById<View>(R.id.btnCompra).setOnClickListener {
            Log.i("SELECCION-BOLETO","El boleto seleccionado es: " + vuelos.get(position).destino)
            val dialog : Dialog = Dialog(holder.itemView.context)
            dialog.setContentView(R.layout.custom_dialog)
            val total:  TextView = dialog.findViewById<View>(R.id.txtTotal) as TextView
            val cantidad : EditText = dialog.findViewById<View>(R.id.editCantidad) as EditText
            val btnComprar : Button = dialog.findViewById<View>(R.id.btnCompra) as Button

            cantidad.setText("1")
            cantidad.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    Log.i("BEFORECHANGE", "beforeTextChanged: " + value.toString())
                }

                override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    Log.i("CANTIDAD-MODIFICADA", "new val: " + value.toString())
                }

                override fun afterTextChanged(value: Editable?) {
                    Log.i("AFTERCHANGE", "beforeTextChanged: " + value.toString())
                    var totalCompra = 0
                    if(value.toString().equals("") || value == null) {
                        totalCompra = 0
                    }else{
                        totalCompra = value.toString().toInt() * 100
                    }
                    total.setText("$"+ totalCompra)
                }

            })
            val descVuelo:  TextView = dialog.findViewById<View>(R.id.txtDescripcionVuelo) as TextView
            descVuelo.setText("El vuelo desde "+ vuelo.origen+" hacia "+vuelo.destino+" esta agendando desde " +
                    vuelo.fecha_salida + " hasta " + vuelo.fecha_llegada)

            btnComprar.setOnClickListener {
//                Toast.makeText(context, "Su compra se realizo exitosamente, puede ver su boleto en el apartado 'Mis Vuelos'", Toast.LENGTH_LONG)
                Snackbar.make(holder.itemView, "Su compra se realizo exitosamente, puede ver su boleto en el apartado 'Mis Vuelos'", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                dialog.dismiss()
            }
            dialog.show()
        }

    }

//    private fun isAnyItemExpanded(position: Int){
//        val temp = vuelos.indexOfFirst {
//            it.isExpandable
//        }
//        if (temp >= 0 && temp != position){
//            vuelos[temp].isExpandable = false
//            notifyItemChanged(temp , 0)
//        }
//    }

    override fun onBindViewHolder(
        holder: VueloViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        if(payloads.isNotEmpty() && payloads[0] == 0){
            holder.collapseExpandedView()
        }else{
            super.onBindViewHolder(holder, position, payloads)

        }
    }

    override fun getItemCount(): Int {
        return vuelos.size
    }
}