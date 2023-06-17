package com.example.sistemasdeboletosaereos

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sistemasdeboletosaereos.Login.LoginActivity
import com.example.sistemasdeboletosaereos.botaero.Chat
import com.example.sistemasdeboletosaereos.databinding.ActivityMainBinding
import com.example.sistemasdeboletosaereos.extra.MapsActivity
import com.example.sistemasdeboletosaereos.extra.QR
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    val CHANNEL_ID = "SOAR_CHANNEL_1"
    private var backPressedTime: Long = 0

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (backPressedTime + 3000 > System.currentTimeMillis()) {
                isEnabled = false
                finishAffinity()
            } else {
                Toast.makeText(baseContext, "Presiona de nuevo para salir", Toast.LENGTH_SHORT).show()
            }
            backPressedTime = System.currentTimeMillis()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        if(!checkPermissions(this))
            requestPermission()

        createNotifChannel()

//        binding.appBarMain.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_user
            ), drawerLayout
        )
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        val color = when(navController.currentDestination?.label){
//            "Recompensa" -> R.color.black
//            else -> R.color.primary
//        }
//        toolbar.setBackgroundColor(resources.getColor(color))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val chatbttn = findViewById<FloatingActionButton>(R.id.fab)
        chatbttn.setOnClickListener(View.OnClickListener { startActivity(Intent(this, Chat::class.java))})
        onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }
    private fun accion(){
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun actionsettings(item: MenuItem) {
        auth.signOut()
        accion()
    }
    fun actionqr(item: MenuItem) {
        startActivity(Intent(this, QR::class.java))
    }
    fun actionubi(item: MenuItem) {
        startActivity(Intent(this, MapsActivity::class.java))
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
    // Metodo para solicitar permiso de lectura y escritura de archivos
    fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE), 101
        )
    }
    fun showNotification() {
        val channelId = "soar_channel"
        val notificationId = 1

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Mi canal",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Descripción del canal"
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = Notification.Builder(this, channelId)
            .setSmallIcon(R.drawable.logoico) // Reemplaza 'ic_notification' con el nombre de tu ícono de notificación
            .setContentTitle("Mi título")
            .setContentText("Este es el contenido de la notificación")
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    private fun createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "CHANNEL_SOAR", NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

}