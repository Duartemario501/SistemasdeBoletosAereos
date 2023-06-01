package com.example.sistemasdeboletosaereos.util

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.db.VuelosEntity
import com.google.android.material.snackbar.Snackbar

class RecompensaAdapter(private var vuelos: List<VuelosEntity>): RecyclerView.Adapter<RecompensaAdapter.RecompensaViewHolder>() {

    var context: Context? = null
    inner class RecompensaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val logo: ImageView = itemView.findViewById(R.id.logoIv)
        val titleTv: TextView = itemView.findViewById(R.id.titleTv)
        val langDescTv: TextView = itemView.findViewById(R.id.langDesc)
//        val price : TextView = itemView.findViewById(R.id.txtPrice)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)

        fun collapseExpandedView(){
            langDescTv.visibility = View.GONE
        }
    }
    fun setFilteredList(vuelos: List<VuelosEntity>) {
        this.vuelos = vuelos
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecompensaViewHolder {
        var view : View? = null

        view = LayoutInflater.from(parent.context).inflate(R.layout.item_recompensa, parent, false)

        context = parent.context
        return RecompensaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecompensaViewHolder, position: Int) {

        val vuelo = vuelos[position]
        holder.logo.setImageResource(R.drawable.icon_home_viajes)
        holder.titleTv.text = "Por solo "+ vuelo.precio.toDouble()*10 +" puntos"
//        holder.titleTv.text = vuelo.destino
        holder.langDescTv.text = vuelo.descripcion
        holder.langDescTv.visibility = View.VISIBLE
//        holder.constraintLayout.setOnClickListener {
//            vuelo.isExpandable = !vuelo.isExpandable
//            notifyItemChanged(position , Unit)
//        }
        holder.itemView.findViewById<View>(R.id.btnCompra).setOnClickListener {
            Log.i("SELECCION-BOLETO","El boleto seleccionado es: " + vuelos.get(position).destino)
            val dialog : Dialog = Dialog(holder.itemView.context)
            dialog.setContentView(R.layout.dialog_recompensa)
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
        holder: RecompensaViewHolder,
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