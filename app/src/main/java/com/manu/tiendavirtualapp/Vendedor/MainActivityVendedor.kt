package com.manu.tiendavirtualapp.Vendedor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.manu.tiendavirtualapp.R
import com.manu.tiendavirtualapp.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentMisProductosV
import com.manu.tiendavirtualapp.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentOrdenesV
import com.manu.tiendavirtualapp.Vendedor.nav_fragment_vendedor.FragmentMiTiendaV
import com.manu.tiendavirtualapp.Vendedor.nav_fragment_vendedor.FragmentReseniasV
import com.manu.tiendavirtualapp.Vendedor.nav_fragment_vendedor.FragmentinicioV
import com.manu.tiendavirtualapp.databinding.ActivityMainVendedorBinding

class MainActivityVendedor : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding : ActivityMainVendedorBinding
    private var firebaseAuth : FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        binding.navigationView.setNavigationItemSelectedListener (this)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer


        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        replaceFragment(FragmentinicioV())
        binding.navigationView.setCheckedItem(R.id.op_inicio_v)


    }

    private fun cerrarSesion(){
        firebaseAuth!!.signOut()
        startActivity(Intent(applicationContext , LoginVendedorActivity::class.java))
        finish()
        Toast.makeText(applicationContext, "has cerrado sesion" , Toast.LENGTH_SHORT).show()
    }

    private fun comprobarSesion() {
        /*si el usuario no ha iniciado sesion*/
        if (firebaseAuth!!.currentUser == null){
            startActivity(Intent(applicationContext ,LoginVendedorActivity::class.java))
            Toast.makeText(applicationContext , "Vendedor no registrado o no logeado" , Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(applicationContext , "Vendedor en linea" , Toast.LENGTH_SHORT).show()
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment , fragment)
            .commit()


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.op_inicio_v ->{
                replaceFragment(FragmentinicioV())

            }
            R.id.op_inicio_v->{
                replaceFragment(FragmentMiTiendaV())
            }
            R.id.op_resenia_v->{
                replaceFragment(FragmentReseniasV())
            }
            R.id.op_cerrar_sesion_v->{
                cerrarSesion()
            }
            R.id.op_mis_productos_v->{
                replaceFragment(FragmentMisProductosV())
            }
            R.id.op_mis_ordenes_v->{
                replaceFragment(FragmentOrdenesV())
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}