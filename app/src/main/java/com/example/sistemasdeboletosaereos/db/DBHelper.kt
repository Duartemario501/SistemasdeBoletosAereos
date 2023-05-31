package com.example.sistemasdeboletosaereos.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "Sistemasboletoaereo.db", null, 1) {
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
                "    duracion INT,"+
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


   fun anyadirDatovuelo(id: String, aerolinea_id: String, ruta_id: String, avion_id: String, fecha_salida: String, hora_salida: String, duracion: String, ) {
        val datosvuelo = ContentValues()
        datosvuelo.put("id", id)
        datosvuelo.put("aerolinea_id", aerolinea_id)
        datosvuelo.put("ruta_id", ruta_id)
        datosvuelo.put("avion_id", avion_id)
        datosvuelo.put("fecha_salida", fecha_salida)
        datosvuelo.put("hora_salida", hora_salida)
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


}