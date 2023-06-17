package com.example.sistemasdeboletosaereos.util

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasdeboletosaereos.R
import com.example.sistemasdeboletosaereos.db.DBHelper
import com.example.sistemasdeboletosaereos.db.VuelosEntity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.io.FileOutputStream


class VueloAdapter(private var vuelos: List<VuelosEntity>, private var version: Int ) : RecyclerView.Adapter<VueloAdapter.VueloViewHolder>() {

    var context: Context? = null
    val auth = FirebaseAuth.getInstance()
    // dimensiones de archivo pdf
    var pageHeight = 1120
    var pageWidth = 792
    val NOTIFY_ID = 1
    val CHANNEL_ID = "SOAR_CHANNEL_1"

    // Creando imagenes que tendra el pdf del boleto
    lateinit var bmp: Bitmap
    lateinit var logo: Bitmap
    lateinit var qr: Bitmap

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

        when(version){
            1,3 ->{
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_vuelo, parent, false)
            }
        }

        context = parent.context
        return VueloViewHolder(view)


    }




    override fun onBindViewHolder(holder: VueloViewHolder, position: Int) {

        val db = DBHelper(holder.itemView.context)
        val vuelo = vuelos[position]
        holder.logo.setImageResource(R.drawable.icon_home_viajes)
        if(version== 1 || version == 3)
            holder.titleTv.text = vuelo.destino
        else
            holder.titleTv.text = vuelo.destino.plus("(${vuelo.estado})")
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
        if(version == 3){
            val btn : Button = holder.itemView.findViewById(R.id.btnCompra)
            btn.visibility = View.INVISIBLE
        }
        holder.itemView.findViewById<View>(R.id.btnCompra).setOnClickListener {
//            Log.i("SELECCION-BOLETO","El boleto seleccionado es: " + vuelos.get(position).destino)
            val dialog : Dialog = Dialog(holder.itemView.context)
            dialog.setContentView(R.layout.custom_dialog)
            val total:  TextView = dialog.findViewById(R.id.txtTotal)
            val precio:  TextView = dialog.findViewById(R.id.txtPrecio)
            val btnComprar : Button = dialog.findViewById(R.id.btnCompra)
            val asiento : EditText = dialog.findViewById(R.id.editAsiento)
            if(version == 2){
                btnComprar.setText("Imprimir")
                asiento.setText(db.getAsiento(db.getIdUsuarioByUid(auth.uid!!), vuelo.id))
            }
            total.text = "$${vuelo.precio}"
            precio.text = "$${vuelo.precio}"
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
                if(version == 2){
                    bmp = BitmapFactory.decodeResource(holder.itemView.resources, R.drawable.logoico)
                    logo = Bitmap.createScaledBitmap(bmp, 140, 140, false)
                    bmp = BitmapFactory.decodeResource(holder.itemView.resources, R.drawable.qr)
                    qr = Bitmap.createScaledBitmap(bmp, 300, 300, false)

                    val vueloEnc = "Su vuelo es de " + vuelo.origen + " hacia " + vuelo.destino
                    val vueloHora = "Por favor debe estar antes de: " + vuelo.hora_salida
                    val vueloDesc = "Informacion de su vuelo: "+  vuelo.descripcion

                    if(checkPermissions(holder.itemView.context)){
                        generatePDF(holder.itemView.context, vuelo.id, vueloEnc, vueloHora, vueloDesc)
                    }else {
                        Toast.makeText(
                            context,
                            "Para poder imprimir el boleto debe conceder los permisos",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }else if(version == 1){
                    val idUser = db.getIdUsuarioByUid(auth.uid!!)
                    db.anyadirDatoreservacion(
                        db.getLastIdReservacion(),
                        vuelo.id,
                        idUser,
                        vuelo.tarifa,
                        asiento.text.toString(),
                        "ACT"
                    )
                    db.anyadirDatoprograma_fidelizacion(db.getLastIdPuntos(), idUser, "500")

                    val builder = NotificationCompat.Builder(holder.itemView.context, "SOAR_CHANNEL")
                    val message = "Su compra se completo exitosamente.\nVerifique su boleto en la opción Mis Vuelos"
                    builder.setContentTitle("COMPRA COMPLETADA")
                        .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                        .setContentText(message)
                        .setSmallIcon(R.drawable.icon_home_viajes)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setChannelId(CHANNEL_ID)

                    if (ActivityCompat.checkSelfPermission(
                            holder.itemView.context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        Snackbar.make(
                            holder.itemView,
                            "No cuenta con permisos para enviar notificaciones",
                            Snackbar.LENGTH_LONG
                        ).setAction("Action", null).show()
                        return@setOnClickListener
                    }
                    NotificationManagerCompat.from(holder.itemView.context).notify(NOTIFY_ID, builder.build())

                    dialog.dismiss()
                }
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

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun generatePDF(context: Context, idBoleto: String, vueloEnc: String, vueloHora: String, vueloDesc: String) {
        // Creando objeto PDF
        var pdfDocument: PdfDocument = PdfDocument()

        // Se crean dos variables para poder agregar contenido al archivo
        // "paint" para imagen, "title" para texto
        var paint: Paint = Paint()
        var title: Paint = Paint()

        // Se agrega la informacion del pdf que se creara
        var myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        // Iniciamos la pagina del pdf
        var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)

        var canvas: Canvas = myPage.canvas

        //Agregamos el logo de la app
        canvas.drawBitmap(logo, 56F, 40F, paint)

        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))

        // Tamaño del texto par el encabezado
        title.textSize = 20F
        // color del encabezado
        title.setColor(ContextCompat.getColor(context, R.color.second))

        // Agregando el encabezado
        canvas.drawText("Soar Inc.", 209F, 100F, title)
        canvas.drawText("Boleto " + idBoleto, 209F, 80F, title)

        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        //Color del contenido
        title.setColor(ContextCompat.getColor(context, R.color.black))
        // Tamaño del texto par el contenido
        title.textSize = 15F

        // Agregando el contenido
        title.textAlign = Paint.Align.CENTER
        canvas.drawText(vueloEnc, 396F, 330F, title)
        canvas.drawText(vueloHora, 396F, 350F, title)
        canvas.drawText(vueloDesc, 396F, 370F, title)

        // Agreando codigo qr del boleto
        canvas.drawBitmap(qr, 250F, 500F, paint)

        //Cerramos o finalizamos el pdf
        pdfDocument.finishPage(myPage)

        // Definimos la ruta y archivo donde se guardara el archivo
        val file: File = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "boleto.pdf")

        try {
            // Escribimos el archivo
            pdfDocument.writeTo(FileOutputStream(file))

            // Indicamos al usuario que su bolote se guardo
            Toast.makeText(context, "Puede encontrar su boleto en la carpeta Download", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()

            // Indicamos al usuario que existe un error
            Toast.makeText(context, "Problemas para generar imprimir su archivo", Toast.LENGTH_LONG).show()
        }
        // Cerramos el archivo para liberar la memoria
        pdfDocument.close()
    }

    //Metodo para verificar permisos de lectura y escritura de archivos
    fun checkPermissions(context: Context): Boolean {

        var writeStoragePermission = ContextCompat.checkSelfPermission(
            context,
            WRITE_EXTERNAL_STORAGE
        )
        var readStoragePermission = ContextCompat.checkSelfPermission(
            context,
            READ_EXTERNAL_STORAGE
        )
        return writeStoragePermission == PackageManager.PERMISSION_GRANTED
                && readStoragePermission == PackageManager.PERMISSION_GRANTED
    }

}