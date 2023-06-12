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
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.example.sistemasdeboletosaereos.db.RutasEntity
import com.google.android.material.snackbar.Snackbar

class RutaAdapter(private var rutas: List<RutasEntity>) : RecyclerView.Adapter<RutaAdapter.AvionViewHolder>() {

    var context: Context? = null
    val NOTIFY_ID = 2
    val CHANNEL_ID = "SOAR_CHANNEL_1"

    inner class AvionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.txt_modelo)
        val btnEdit: Button = itemView.findViewById(R.id.btn_edit)
        val btnDel: Button = itemView.findViewById(R.id.btn_delete)
    }
    fun setFilteredList(rutas: List<RutasEntity>) {
        this.rutas = rutas
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
        val ruta = rutas[position]
        var data = ArrayList<RutasEntity>()

        "${ruta.origen} - ${ruta.destino}".also { holder.title.text = it }
        holder.btnEdit.setOnClickListener {
            Log.i("EDIT-RUTA", "Ruta seleccionada: " + ruta.id + "-" + ruta.origen)
            val dialog = Dialog(holder.itemView.context)
            dialog.setContentView(R.layout.add_ruta_dialog)

            val destino: EditText = dialog.findViewById(R.id.edit_destino)
            val origen: EditText = dialog.findViewById(R.id.edit_origen)
            val btn: Button = dialog.findViewById(R.id.btn_action_ruta)

            //SET VALUES
            btn.text = "Editar"
            origen.setText(ruta.origen)
            destino.setText(ruta.destino)

            btn.setOnClickListener {
                db.updateRuta(ruta.id.toString(), origen.text.toString(), destino.text.toString())
                val rc = db.getDestinos()
                if(rc!!.moveToFirst()){
                    do{
                        val ruta = RutasEntity(rc.getInt(0), rc.getString(1), rc.getString(2))
                        data.add(ruta)
                    }while (rc.moveToNext())
                }
                setFilteredList(data)
                Snackbar.make(
                    holder.itemView,
                    "Ruta Actualizada",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).show()
                dialog.dismiss()
            }
            dialog.show()
        }
        holder.btnDel.setOnClickListener {
            val msg = db.deleteRuta(ruta.id.toString())
            val rc = db.getDestinos()
            if(rc!!.moveToFirst()){
                do{
                    val ruta = RutasEntity(rc.getInt(0), rc.getString(1), rc.getString(2))
                    data.add(ruta)
                }while (rc.moveToNext())
            }
            setFilteredList(data)
            Snackbar.make(
                holder.itemView,
                msg,
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
        return rutas.size
    }

}