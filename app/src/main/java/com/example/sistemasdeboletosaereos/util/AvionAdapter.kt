package com.example.sistemasdeboletosaereos.util

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.db.AvionesEntity
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.google.android.material.snackbar.Snackbar

class AvionAdapter(private var aviones: List<AvionesEntity>) : RecyclerView.Adapter<AvionAdapter.AvionViewHolder>() {

    var context: Context? = null
    val NOTIFY_ID = 2
    val CHANNEL_ID = "SOAR_CHANNEL_1"

    inner class AvionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val modelo: TextView = itemView.findViewById(R.id.txt_modelo)
        val btnEdit: Button = itemView.findViewById(R.id.btn_edit)
        val btnDel: Button = itemView.findViewById(R.id.btn_delete)
    }
    fun setFilteredList(aviones: List<AvionesEntity>) {
        this.aviones = aviones
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvionViewHolder {
        var view : View? = null
        context = parent.context

        view = LayoutInflater.from(context).inflate(R.layout.row_avion, parent, false)
        return AvionViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvionViewHolder, position: Int) {

        val db = DBHelper(holder.itemView.context)
        val avion = aviones[position]
        holder.modelo.text = (avion.modelo)

        holder.btnEdit.setOnClickListener {
            Log.i("EDIT-AVION", "Avion seleccionado: " + avion.id + "-" + avion.modelo)
            val dialog = Dialog(holder.itemView.context)
            dialog.setContentView(R.layout.add_avion_dialog)

            val modelo: EditText = dialog.findViewById(R.id.edit_modelo)
            val btn: Button = dialog.findViewById(R.id.btn_add_avion)

            btn.text = "Actualizar"

            modelo.setText(avion.modelo)

            btn.setOnClickListener {
                db.updateAvion(avion.id.toString(), modelo.text.toString())

                var avionesUpdate = ArrayList<AvionesEntity>()
                val avionesCursor = db.getAviones()
                if(avionesCursor?.moveToFirst()== true){
                    do {
                        var avion: AvionesEntity = AvionesEntity(
                            avionesCursor.getString(0).toInt(), avionesCursor.getString(1)
                        )
                        avionesUpdate.add(avion)
                    }while (avionesCursor.moveToNext())
                }
                setFilteredList(avionesUpdate)
                Snackbar.make(
                    holder.itemView,
                    "Avion Actualizado",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).show()

                dialog.dismiss()
            }

            dialog.show()

        }

        holder.btnDel.setOnClickListener {

            db.deleteAvion(avion.id.toString())

            var avionesUpdate = ArrayList<AvionesEntity>()
            val avionesCursor = db.getAviones()
            if(avionesCursor?.moveToFirst()== true){
                do {
                    var avion: AvionesEntity = AvionesEntity(
                        avionesCursor.getString(0).toInt(), avionesCursor.getString(1)
                    )
                    avionesUpdate.add(avion)
                }while (avionesCursor.moveToNext())
            }
            setFilteredList(avionesUpdate)
            Snackbar.make(
                holder.itemView,
                "Avion Eliminado",
                Snackbar.LENGTH_LONG
            ).setAction("Action", null).show()
        }
    }

    override fun onBindViewHolder(
        holder: AvionViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int {
        return aviones.size
    }

}