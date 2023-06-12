package com.example.sistemasdeboletosaereos.util

import android.app.Dialog
import android.content.Context
import android.database.Cursor
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

class AvionAdapter(private var aviones: List<AvionesEntity>, private var type: String) : RecyclerView.Adapter<AvionAdapter.AvionViewHolder>() {

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

        var data = ArrayList<AvionesEntity>()
        var cursor: Cursor

        holder.btnEdit.setOnClickListener {
            Log.i("EDIT-AVION", "Avion seleccionado: " + avion.id + "-" + avion.modelo)
            val dialog = Dialog(holder.itemView.context)
            dialog.setContentView(R.layout.add_avion_dialog)

            val titulo: TextView = dialog.findViewById(R.id.txt_title)
            val modelo: EditText = dialog.findViewById(R.id.edit_modelo)
            val btn: Button = dialog.findViewById(R.id.btn_add_avion)
            var msg = ""

            btn.text = "Actualizar"

            when(type){
                "AEROLINEA" -> {titulo.setText("Editar Aerolinea")}
            }
            modelo.setText(avion.modelo)

            btn.setOnClickListener {
                when(type) {
                    "AEROLINEA" -> {
                        db.updateAerolinea(avion.id.toString(), modelo.text.toString())
                        msg = "Aerolinea Actualizada"
                        cursor = db.getAerolineas()!!
                        if(cursor?.moveToFirst()== true){
                            do {
                                var avion: AvionesEntity = AvionesEntity(
                                    cursor.getString(0).toInt(), cursor.getString(1)
                                )
                                data.add(avion)
                            }while (cursor.moveToNext())
                        }
                    }
                    "AVION" -> {
                        db.updateAvion(avion.id.toString(), modelo.text.toString())
                        msg = "Avion Actualizado"
                        cursor = db.getAviones()!!
                        if(cursor?.moveToFirst()== true){
                            do {
                                var avion: AvionesEntity = AvionesEntity(
                                    cursor.getString(0).toInt(), cursor.getString(1)
                                )
                                data.add(avion)
                            }while (cursor.moveToNext())
                        }
                    }
                }
                setFilteredList(data)
                Snackbar.make(
                    holder.itemView,
                    msg,
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).show()

                dialog.dismiss()
            }
            dialog.show()
        }

        holder.btnDel.setOnClickListener {
            when(type) {
                "AEROLINEA" -> {
                    db.deleteAerolinea(avion.id.toString())
                    val avionesCursor = db.getAerolineas()
                    if(avionesCursor?.moveToFirst()== true){
                        do {
                            var avion: AvionesEntity = AvionesEntity(
                                avionesCursor.getString(0).toInt(), avionesCursor.getString(1)
                            )
                            data.add(avion)
                        }while (avionesCursor.moveToNext())
                    }
                }
                "AVION" -> {
                    db.deleteAvion(avion.id.toString())
                    val avionesCursor = db.getAviones()
                    if(avionesCursor?.moveToFirst()== true){
                        do {
                            var avion: AvionesEntity = AvionesEntity(
                                avionesCursor.getString(0).toInt(), avionesCursor.getString(1)
                            )
                            data.add(avion)
                        }while (avionesCursor.moveToNext())
                    }
                }
            }

            setFilteredList(data)
            Snackbar.make(
                holder.itemView,
                "Registro Eliminado",
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