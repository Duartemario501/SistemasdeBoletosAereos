package com.example.sistemasdeboletosaereos.util

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.example.sistemasdeboletosaereos.db.VuelosEntity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class VueloAdapter(private var vuelos: List<VuelosEntity>, private var version: Int ) : RecyclerView.Adapter<VueloAdapter.VueloViewHolder>() {

    var context: Context? = null
    val auth = FirebaseAuth.getInstance()

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
        var view : View? = null


        if(version == 1) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        }else{
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_vuelo, parent, false)
        }

        context = parent.context
        return VueloViewHolder(view)


    }




    override fun onBindViewHolder(holder: VueloViewHolder, position: Int) {

        val db = DBHelper(holder.itemView.context)
        val vuelo = vuelos[position]
        holder.logo.setImageResource(R.drawable.icon_home_viajes)
        holder.titleTv.text = vuelo.destino + " ("+vuelo.estado+") "
        holder.langDescTv.text = vuelo.descripcion + ". Esta agendando desde " +
                 vuelo.fecha_salida + " hasta " + vuelo.fecha_regreso
        holder.price.text = "$" + vuelo.precio
        val isExpandable: Boolean = true
        holder.langDescTv.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.constraintLayout.setOnClickListener {
            notifyItemChanged(position , Unit)
        }

        if(version == 2){
            val btnCancel : Button = holder.itemView.findViewById(R.id.btnCancelar)
            btnCancel.setOnClickListener {
                Snackbar.make(
                    holder.itemView,
                    db.cancelarVuelo(vuelo.reservacion),
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).show()
                vuelo.estado = "CNC"
                this.notifyItemChanged(position)
            }
        }
        holder.itemView.findViewById<View>(R.id.btnCompra).setOnClickListener {
            Log.i("SELECCION-BOLETO","El boleto seleccionado es: " + vuelos.get(position).destino)
            val dialog : Dialog = Dialog(holder.itemView.context)
            dialog.setContentView(R.layout.custom_dialog)
            val total:  TextView = dialog.findViewById(R.id.txtTotal)
            val btnComprar : Button = dialog.findViewById(R.id.btnCompra)
            val asiento : EditText = dialog.findViewById(R.id.editAsiento)

            if(version == 2){
                asiento.setText(db.getAsiento(db.getIdUsuarioByUid(auth.uid!!), vuelo.id))
            }
            //Deshabilitado hasta implementar compras de varios vuelos
//            val cantidad : EditText = dialog.findViewById<View>(R.id.editCantidad) as EditText
//            cantidad.setText("1")
//            cantidad.addTextChangedListener(object : TextWatcher{
//                override fun beforeTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                    Log.i("BEFORECHANGE", "beforeTextChanged: " + value.toString())
//                }
//
//                override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                    Log.i("CANTIDAD-MODIFICADA", "new val: " + value.toString())
//                }
//
//                override fun afterTextChanged(value: Editable?) {
//                    Log.i("AFTERCHANGE", "beforeTextChanged: " + value.toString())
//                    var totalCompra = 0
//                    if(value.toString().equals("") || value == null) {
//                        totalCompra = 0
//                    }else{
//                        totalCompra = value.toString().toInt() * 100
//                    }
//                    total.setText("$"+ totalCompra)
//                }
//
//            })
            val descVuelo:  TextView = dialog.findViewById<View>(R.id.txtDescripcionVuelo) as TextView
            descVuelo.setText("El vuelo desde "+ vuelo.origen+" hacia "+vuelo.destino+" esta agendando desde " +
                    vuelo.fecha_salida + " hasta " + vuelo.fecha_regreso)

            btnComprar.setOnClickListener {
//                Toast.makeText(context, "Su compra se realizo exitosamente, puede ver su boleto en el apartado 'Mis Vuelos'", Toast.LENGTH_LONG)
                val idUser = db.getIdUsuarioByUid(auth.uid!!)
                db.anyadirDatoreservacion(
                    db.getLastIdReservacion(),vuelo.id, idUser
                    , vuelo.tarifa, asiento.text.toString(), "ACT"
                )
                db.anyadirDatoprograma_fidelizacion(db.getLastIdPuntos(), idUser, "500")

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