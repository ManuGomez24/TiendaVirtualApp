package com.manu.tiendavirtualapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.manu.tiendavirtualapp.Cliente.MainActivityCliente
import com.manu.tiendavirtualapp.Vendedor.MainActivityVendedor


class splashscreensactivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreensactivity)

        firebaseAuth = FirebaseAuth.getInstance()


        verBienvenida()

    }

    private fun verBienvenida() {
       object : CountDownTimer(3000 , 1000){
           override fun onTick(millisUntilFinished: Long) {

           }

           override fun onFinish() {
               comprobarTipoUsuario()
           }

           }.start()


       }

    private fun comprobarTipoUsuario(){
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this , MainActivityVendedor::class.java))
        }else{
            val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
            reference.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                      val tipoU = snapshot.child("tipoUsuario").value

                        if (tipoU=="vendedor"){
                            startActivity(Intent(this@splashscreensactivity , MainActivityVendedor::class.java))
                            finishAffinity()
                        }else if (tipoU === "cliente"){
                            startActivity(Intent(this@splashscreensactivity , MainActivityCliente::class.java))
                            finishAffinity()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }
    }
}