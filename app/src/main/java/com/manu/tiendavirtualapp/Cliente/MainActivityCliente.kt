package com.manu.tiendavirtualapp.Cliente

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat

import com.google.android.material.navigation.NavigationView
import com.manu.tiendavirtualapp.Cliente.Bottom_Nav_Fragments_Cliente.FragmentMisOrdenesC
import com.manu.tiendavirtualapp.Cliente.Bottom_Nav_Fragments_Cliente.FragmentTiendC
import com.manu.tiendavirtualapp.Cliente.Nav_Fragments_Cliente.FragmentInicioC
import com.manu.tiendavirtualapp.R
import com.manu.tiendavirtualapp.databinding.ActivityMainClienteBinding

class MainActivityCliente : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding : ActivityMainClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        binding.navigationView.setNavigationItemSelectedListener (this)
        val toggle = ActionBarDrawerToggle(
            this ,
            binding.drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer

        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        replaceFragment(FragmentInicioC())

    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment , fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
    when (item.itemId){
        R.id.op_inicio_c->{
            replaceFragment(FragmentInicioC())
        }
        R.id.op_mi_perfil_c->{
            replaceFragment(FragmentInicioC())
        }
        R.id.op_cerrar_sesion_c->{
            Toast.makeText(applicationContext , "has cerrado sesion" , Toast.LENGTH_SHORT).show()
        }
        R.id.op_tienda_c->{
            replaceFragment(FragmentTiendC())
        }
        R.id.op_mis_ordenes_c->{
            replaceFragment(FragmentMisOrdenesC())
        }
    }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}