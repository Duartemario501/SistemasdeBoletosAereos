package com.example.sistemasdeboletosaereos.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) : SQLiteOpenHelper(context, "Sistemasboletoaereo.s3db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val aerolinea="CREATE TABLE aerolinea ("+
                "    id INT PRIMARY KEY,"+
                "    nombre VARCHAR(255)"+
                ");"
        val ruta="CREATE TABLE ruta ("+
                "    id INT PRIMARY KEY,"+
                "    origen VARCHAR(255),"+
                "    destino VARCHAR(255)"+
                ");"
        val avion="CREATE TABLE avion ("+
                "    id INT PRIMARY KEY,"+
                "    modelo VARCHAR(255)"+
                ");"
        val vuelo="CREATE TABLE vuelo ("+
                "    id INT PRIMARY KEY,"+
                "    aerolinea_id INT,"+
                "    ruta_id INT,"+
                "    avion_id INT,"+
                "    fecha_salida DATE,"+
                "    hora_salida TIME,"+
                "    fecha_regreso DATE,"+
                "    duracion INT,"+
                "    descripcion VARCHAR(255),"+
                "    FOREIGN KEY (aerolinea_id) REFERENCES aerolinea(id),"+
                "    FOREIGN KEY (ruta_id) REFERENCES ruta(id),"+
                "    FOREIGN KEY (avion_id) REFERENCES avion(id)"+
                ");"
        val tarifa="CREATE TABLE tarifa ("+
                "    id INT PRIMARY KEY,"+
                "    vuelo_id INT,"+
                "    clase VARCHAR(255),"+
                "    precio DECIMAL(10, 2),"+
                "    capacidad_clase INT,"+
                "    FOREIGN KEY (vuelo_id) REFERENCES vuelo(id)"+
                ");"
        val pasajero="CREATE TABLE pasajero ("+
                "    id INT PRIMARY KEY,"+
                "    nombre VARCHAR(255),"+
                "    correo VARCHAR(100),"+
                "    password VARCHAR(100),"+
                "    telefono VARCHAR(50),"+
                "    fecha_nacimiento DATE,"+
                "    numero_pasaporte VARCHAR(255)"+
                ");"
        val programa_fidelizacion="CREATE TABLE programa_fidelizacion ("+
                "    id INT PRIMARY KEY,"+
                "    pasajero_id INT,"+
                "    puntos INT,"+
                "    FOREIGN KEY (pasajero_id) REFERENCES pasajero(id)"+
                ");"
        val reservacion="CREATE TABLE reservacion ("+
                "    id INT PRIMARY KEY,"+
                "    vuelo_id INT,"+
                "    pasajero_id INT,"+
                "    tarifa_id INT,"+
                "    asiento INT,"+
                "    estado VARCHAR(255),"+
                "    FOREIGN KEY (vuelo_id) REFERENCES vuelo(id),"+
                "    FOREIGN KEY (pasajero_id) REFERENCES pasajero(id),"+
                "    FOREIGN KEY (tarifa_id) REFERENCES tarifa(id)"+
                ");"
        val pago="CREATE TABLE pago ("+
                "    id INT PRIMARY KEY,"+
                "    reservacion_id INT,"+
                "    metodo_pago VARCHAR(255),"+
                "    monto DECIMAL(10, 2),"+
                "    fecha DATE,"+
                "    FOREIGN KEY (reservacion_id) REFERENCES reservacion(id)"+
                ");"
        val tripulacion="CREATE TABLE tripulacion ("+
                "    id INT PRIMARY KEY,"+
                "    nombre VARCHAR(255),"+
                "    puesto VARCHAR(255),"+
                "    vuelo_id INT,"+
                "    FOREIGN KEY (vuelo_id) REFERENCES vuelo(id)"+
                ");"
        db!!.execSQL(pago)
        db!!.execSQL(aerolinea)
        db!!.execSQL(tripulacion)
        db!!.execSQL(reservacion)
        db!!.execSQL(programa_fidelizacion)
        db!!.execSQL(pasajero)
        db!!.execSQL(tarifa)
        db!!.execSQL(vuelo)
        db!!.execSQL(avion)
        db!!.execSQL(ruta)

        Log.i("INIT-DB", "BASE CREADA")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val pago = "DROP TABLE IF EXISTS pago"
        val aerolinea = "DROP TABLE IF EXISTS aerolinea"
        val tripulacion = "DROP TABLE IF EXISTS tripulacion"
        val reservacion = "DROP TABLE IF EXISTS reservacion"
        val pasajero = "DROP TABLE IF EXISTS pasajero"
        val programa_fidelizacion = "DROP TABLE IF EXISTS programa_fidelizacion"
        val tarifa = "DROP TABLE IF EXISTS tarifa"
        val vuelo = "DROP TABLE IF EXISTS vuelo"
        val ruta = "DROP TABLE IF EXISTS ruta"
        val avion = "DROP TABLE IF EXISTS avion"

        db!!.execSQL(pago)
        db!!.execSQL(aerolinea)
        db!!.execSQL(tripulacion)
        db!!.execSQL(reservacion)
        db!!.execSQL(programa_fidelizacion)
        db!!.execSQL(pasajero)
        db!!.execSQL(tarifa)
        db!!.execSQL(vuelo)
        db!!.execSQL(avion)
        db!!.execSQL(ruta)
        onCreate(db)
    }

    fun llenarDB(){
        val db = this.writableDatabase
        db.execSQL("INSERT INTO aerolinea\n" +
                "(id, nombre)\n" +
                "VALUES(1, 'AVIANCA')")

        db.execSQL("INSERT INTO ruta\n" +
                "(id, origen, destino)\n" +
                "VALUES(1, 'EL SALVADOR', 'MEXICO')")

        db.execSQL("INSERT INTO ruta\n" +
                "(id, origen, destino)\n" +
                "VALUES(2, 'EL SALVADOR', 'COSTA RICA')")

        db.execSQL("INSERT INTO ruta\n" +
                "(id, origen, destino)\n" +
                "VALUES(3, 'EL SALVADOR', 'COLOMBIA')")

        db.execSQL("INSERT INTO avion\n" +
                "(id, modelo)\n" +
                "VALUES(1, 'A320')")

        db.execSQL("INSERT INTO vuelo\n" +
                "(id, aerolinea_id, ruta_id, avion_id, fecha_salida, hora_salida, duracion, descripcion, fecha_regreso)\n" +
                "VALUES(1, 1, 1, 1, '31/05/2023', '20:00:00', 2, 'Vuelo directo, primera clase', '07/06/2023')")

        db.execSQL("INSERT INTO vuelo\n" +
                "(id, aerolinea_id, ruta_id, avion_id, fecha_salida, hora_salida, duracion, descripcion, fecha_regreso)\n" +
                "VALUES(2, 1, 2, 1, '31/05/2023', '20:00:00', 2, 'Vuelo directo, clase turista', '07/06/2023')")

        db.execSQL("INSERT INTO vuelo\n" +
                "(id, aerolinea_id, ruta_id, avion_id, fecha_salida, hora_salida, duracion, descripcion, fecha_regreso)\n" +
                "VALUES(3, 1, 3, 1, '31/05/2023', '20:00:00', 2, 'Vuelo con escala en PANAMA, clase turista', '07/06/2023')")

        db.execSQL("INSERT INTO vuelo\n" +
                "(id, aerolinea_id, ruta_id, avion_id, fecha_salida, hora_salida, duracion, descripcion, fecha_regreso)\n" +
                "VALUES(4, 1, 3, 1, '31/05/2023', '20:00:00', 2, 'Vuelo sin escalas, primera clase', '07/06/2023')")

        db.execSQL("INSERT INTO tarifa\n" +
                "(id, vuelo_id, clase, precio, capacidad_clase)\n" +
                "VALUES(1, 1, 'A', 500, 50)")

        db.execSQL("INSERT INTO tarifa\n" +
                "(id, vuelo_id, clase, precio, capacidad_clase)\n" +
                "VALUES(2, 2, 'A', 400, 50)")

        db.execSQL("INSERT INTO tarifa\n" +
                "(id, vuelo_id, clase, precio, capacidad_clase)\n" +
                "VALUES(3, 3, 'A', 300, 50)")

        db.execSQL("INSERT INTO tarifa\n" +
                "(id, vuelo_id, clase, precio, capacidad_clase)\n" +
                "VALUES(4, 4, 'A', 450, 50)")
//
//        db.execSQL("INSERT INTO pasajero\n" +
//                "(id, nombre, fecha_nacimiento, numero_pasaporte)\n" +
//                "VALUES(1, 'Mauricio', '', 'AD85')")
        db.close()
    }
    fun anyadirDatoaerolinea(id: String, nombre: String, ) {
        val datosaerolinea = ContentValues()
        datosaerolinea.put("id", id)
        datosaerolinea.put("nombre", nombre)
        val db = this.writableDatabase
        db.insert("aerolinea", null, datosaerolinea)
        db.close()
    }


   fun anyadirDatoruta(id: String, origen: String, destino: String, ) {
        val datosruta = ContentValues()
        datosruta.put("id", id)
        datosruta.put("origen", origen)
        datosruta.put("destino", destino)
        val db = this.writableDatabase
        db.insert("ruta", null, datosruta)
        db.close()
    }

   fun  anyadirDatoavion(id: String, modelo: String, ) {
        val datosavion = ContentValues()
        datosavion.put("id", id)
        datosavion.put("modelo", modelo)
        val db = this.writableDatabase
        db.insert("avion", null, datosavion)
        db.close()
    }
   fun anyadirDatovuelo(id: String, aerolinea_id: String, ruta_id: String, avion_id: String, fecha_salida: String, hora_salida: String, fecha_regreso: String,
                        descripcion: String, duracion: String, precio: Double) {
        val datosvuelo = ContentValues()
        datosvuelo.put("id", id)
        datosvuelo.put("aerolinea_id", aerolinea_id)
        datosvuelo.put("ruta_id", ruta_id)
        datosvuelo.put("avion_id", avion_id)
        datosvuelo.put("fecha_salida", fecha_salida)
        datosvuelo.put("hora_salida", hora_salida)
        datosvuelo.put("fecha_regreso", fecha_regreso)
        datosvuelo.put("descripcion", descripcion)
        datosvuelo.put("duracion", duracion)
        val db = this.writableDatabase
        db.insert("vuelo", null, datosvuelo)
        db.close()
    }


   fun anyadirDatotarifa(id: String, vuelo_id: String, clase: String, precio: String, capacidad_clase: String, ) {
        val datostarifa = ContentValues()
        datostarifa.put("id", id)
        datostarifa.put("vuelo_id", vuelo_id)
        datostarifa.put("clase", clase)
        datostarifa.put("precio", precio)
        datostarifa.put("capacidad_clase", capacidad_clase)
        val db = this.writableDatabase
        db.insert("tarifa", null, datostarifa)
        db.close()
    }


   fun anyadirDatopasajero(id: String, nombre: String, fecha_nacimiento: String, numero_pasaporte: String, ) {
        val datospasajero = ContentValues()
        datospasajero.put("id", id)
        datospasajero.put("nombre", nombre)
        datospasajero.put("fecha_nacimiento", fecha_nacimiento)
        datospasajero.put("numero_pasaporte", numero_pasaporte)
        val db = this.writableDatabase
        db.insert("pasajero", null, datospasajero)
        db.close()
    }

   fun anyadirDatoprograma_fidelizacion(id: String, pasajero_id: String, puntos: String, ) {
        val datosprograma_fidelizacion = ContentValues()
        datosprograma_fidelizacion.put("id", id)
        datosprograma_fidelizacion.put("pasajero_id", pasajero_id)
        datosprograma_fidelizacion.put("puntos", puntos)
        val db = this.writableDatabase
        db.insert("programa_fidelizacion", null, datosprograma_fidelizacion)
        db.close()
    }


   fun anyadirDatoreservacion(id: String, vuelo_id: String, pasajero_id: String, tarifa_id: String, asiento: String, estado: String, ) {
        val datosreservacion = ContentValues()
        datosreservacion.put("id", id)
        datosreservacion.put("vuelo_id", vuelo_id)
        datosreservacion.put("pasajero_id", pasajero_id)
        datosreservacion.put("tarifa_id", tarifa_id)
        datosreservacion.put("asiento", asiento)
        datosreservacion.put("estado", estado)
        val db = this.writableDatabase
        db.insert("reservacion", null, datosreservacion)
        db.close()
    }

   fun anyadirDatopago(id: String, reservacion_id: String, metodo_pago: String, monto: String, fecha: String, ) {
        val datospago = ContentValues()
        datospago.put("id", id)
        datospago.put("reservacion_id", reservacion_id)
        datospago.put("metodo_pago", metodo_pago)
        datospago.put("monto", monto)
        datospago.put("fecha", fecha)
        val db = this.writableDatabase
        db.insert("pago", null, datospago)
        db.close()
    }

   fun anyadirDatotripulacion(id: String, nombre: String, puesto: String, vuelo_id: String, ) {
        val datostripulacion = ContentValues()
        datostripulacion.put("id", id)
        datostripulacion.put("nombre", nombre)
        datostripulacion.put("puesto", puesto)
        datostripulacion.put("vuelo_id", vuelo_id)
        val db = this.writableDatabase
        db.insert("tripulacion", null, datostripulacion)
        db.close()
    }

    //CONSULTAS VISTA USUARIO

    fun getVuelos(): Cursor? {
        val db = readableDatabase
        val vuelos = db.rawQuery("SELECT v.*, t.precio, t.id, t.clase, r.origen, r.destino " +
                "FROM vuelo v \n" +
                "inner join ruta r ON v.ruta_id = r.id \n" +
                "inner join avion a on v.avion_id = a.id \n" +
                "INNER JOIN tarifa t ON v.id = t.vuelo_id \n", null, null)

        return vuelos
    }

    fun getAviones(): Cursor? {
        val db = readableDatabase
        val vuelos = db.rawQuery("SELECT * FROM avion ", null, null)

        return vuelos
    }

    fun getAerolineas(): Cursor? {
        val db = readableDatabase
        val vuelos = db.rawQuery("SELECT * FROM aerolinea ", null, null)

        return vuelos
    }
    fun getDestinos(): Cursor? {
        val db = readableDatabase
        val vuelos = db.rawQuery("SELECT * FROM ruta r ", null, null)

        return vuelos
    }

    fun buscarVuelos(destino: String, precioDesde: String, precioHasta: String): Cursor? {
        val db = readableDatabase
        val vuelos = db.rawQuery("SELECT v.*, t.precio, t.id, t.clase, r.origen, r.destino " +
                "FROM vuelo v \n" +
                "inner join ruta r ON v.ruta_id = r.id \n" +
                "inner join avion a on v.avion_id = a.id \n" +
                "INNER JOIN tarifa t ON v.id = t.vuelo_id \n" +
                "WHERE r.id = ? AND t.precio BETWEEN ? AND ?", arrayOf(destino, precioDesde, precioHasta), null)

        return vuelos
    }

    fun getVuelosByUser(user: String): Cursor? {
        val db = readableDatabase
        val vuelos = db.rawQuery("SELECT \n" +
                "\tv.*, t.precio, t.id, t.clase, r.origen, r.destino, re.id, re.estado \n" +
                "FROM reservacion re \n" +
                "INNER JOIN vuelo v ON re.vuelo_id = v.id  \n" +
                "inner join ruta r ON v.ruta_id = r.id \n" +
                "inner join avion a on v.avion_id = a.id \n" +
                "INNER JOIN tarifa t ON v.id = t.vuelo_id \n" +
                "WHERE re.pasajero_id = ?", arrayOf(user), null)

        return vuelos
    }

    fun getLastIdReservacion(): String {
        val db = readableDatabase
        val idre = db.rawQuery("SELECT IFNULL(MAX(id),0) FROM reservacion r  ", null, null)
        idre.moveToFirst()
        val id = idre.getInt(0)+1
        return id.toString()
    }

    fun getLastIdPuntos(): String {
        val db = readableDatabase
        val idre = db.rawQuery("SELECT IFNULL(MAX(id),0) FROM programa_fidelizacion pf  ", null, null)
        idre.moveToFirst()
        val id = idre.getInt(0)+1
        return id.toString()
    }

    fun getPuntosByUser(user: String): String {
        val db = readableDatabase
        val puntos = db.rawQuery("SELECT IFNULL(SUM(puntos),0) FROM programa_fidelizacion pf " +
                "WHERE pasajero_id = '" + user + "'", null, null)
        puntos.moveToFirst()
        return puntos.getString(0)
    }

    fun getLastIdUsuario(): String {
        val db = readableDatabase
        val idre = db.rawQuery("SELECT IFNULL(MAX(id),0) FROM pasajero p ", null, null)
        idre.moveToFirst()
        val id = idre.getInt(0)+1
        return id.toString()
    }

    fun getIdUsuarioByUid(uid: String): String {
        val db = readableDatabase
        val idre = db.rawQuery("SELECT IFNULL(id,0) FROM pasajero p WHERE numero_pasaporte = ?", arrayOf(uid), null)
        var id : Int = 0
        if(idre.moveToFirst()){
            id = idre.getInt(0)
        }
        return id.toString()
    }
    fun getUsuarioByUid(uid: String): Cursor {
        val db = readableDatabase
        val user = db.rawQuery("SELECT * FROM pasajero p WHERE numero_pasaporte = ?", arrayOf(uid), null)
        return user
    }
    fun getAsiento(user: String, vuelo_id: String): String {
        val db = readableDatabase
        val idre = db.rawQuery("SELECT asiento FROM reservacion r\n" +
                "WHERE pasajero_id = ? AND vuelo_id = ? \n" +
                "ORDER BY id\n" +
                "LIMIT 1", arrayOf(user,vuelo_id), null)
        var id : Int = 0
        if(idre.moveToFirst()){
            id = idre.getInt(0)
        }
        return id.toString()
    }

    fun cancelarVuelo(id: String): String {
        val db = writableDatabase

        db.execSQL("UPDATE reservacion SET estado = 'CNC' WHERE id = '" + id + "'")
        db.close()
        return "Vuelo cancelado"
    }

    fun updateAvion(id: String, modelo: String, ) {
        val datosavion = ContentValues()
        datosavion.put("modelo", modelo)
        val db = this.writableDatabase
        db.update("avion", datosavion, "id=?", arrayOf(id))
        db.close()
    }
    fun updateAerolinea(id: String, nombre: String) {
        val datosavion = ContentValues()
        datosavion.put("nombre", nombre)
        val db = this.writableDatabase
        db.update("aerolinea", datosavion, "id=?", arrayOf(id))
        db.close()
    }

    fun deleteAvion(id: String){
        val db = this.writableDatabase
        db.delete("avion", "id=?", arrayOf(id))
        db.close()
    }
    fun deleteAerolinea(id: String){
        val db = this.writableDatabase
        db.delete("aerolinea", "id=?", arrayOf(id))
        db.close()
    }
    fun updateRuta(id: String, origen: String, destino: String ) {
        val datosavion = ContentValues()
        datosavion.put("origen", origen)
        datosavion.put("destino", destino)
        val db = this.writableDatabase
        db.update("ruta", datosavion, "id=?", arrayOf(id))
        db.close()
    }
    fun deleteRuta(id: String): String{
        val db = this.writableDatabase
        val query = "SELECT * FROM vuelo v \n" +
                "INNER JOIN ruta r ON v.ruta_id = r.id \n" +
                "WHERE r.id = ?"
        var msg = ""
        if(db.rawQuery(query, arrayOf(id), null).moveToFirst()){
            msg = "Esta ruta no puede ser eliminada porque esta siendo usada en algunos vuelos"
        }else {
            msg = "Ruta eliminada"
            db.delete("ruta", "id=?", arrayOf(id))
        }
        db.close()
        return msg
    }
}