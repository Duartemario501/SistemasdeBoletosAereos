package com.example.sistemasdeboletosaereos

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun buttonClickTest() {
        // Encuentra el botón por su ID y realiza un clic





        // Verifica que el texto en un TextView ha cambiado después del clic
        onView(ViewMatchers.withId(R.id.TxtContraseña))
            .check(ViewAssertions.matches(ViewMatchers.withText("Texto esperado")))
        onView(ViewMatchers.withId(R.id.txtCorreo))
            .check(ViewAssertions.matches(ViewMatchers.withText("Texto esperado")))
        onView(ViewMatchers.withId(R.id.txtComprarVuelo))
            .check(ViewAssertions.matches(ViewMatchers.withText("Texto esperado")))
        onView(ViewMatchers.withId(R.id.txtComprarVuelo))
            .check(ViewAssertions.matches(ViewMatchers.withText("Texto esperado")))
        onView(ViewMatchers.withId(R.id.txtNombre))
            .check(ViewAssertions.matches(ViewMatchers.withText("Texto esperado")))
        onView(ViewMatchers.withId(R.id.txtPrecio))
            .check(ViewAssertions.matches(ViewMatchers.withText("Texto esperado")))
        onView(ViewMatchers.withId(R.id.txtTelefono))
            .check(ViewAssertions.matches(ViewMatchers.withText("Texto esperado")))
        onView(ViewMatchers.withId(com.example.sistemasdeboletosaereos.R.id.txtTotal))
            .check(ViewAssertions.matches(ViewMatchers.withText("Texto esperado")))

    }

}
