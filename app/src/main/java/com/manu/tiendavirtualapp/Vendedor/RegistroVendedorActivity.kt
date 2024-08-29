package com.manu.tiendavirtualapp.Vendedor

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.manu.tiendavirtualapp.Constantes
import com.manu.tiendavirtualapp.R
import com.manu.tiendavirtualapp.databinding.ActivityRegistroVendedorBinding

class RegistroVendedorActivity : AppCompatActivity() {

    private lateinit var  binding :ActivityRegistroVendedorBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.btnRegistrarV.setOnClickListener{
            validarInformacion()
        }


    }

    private var nombres = ""
    private var email = ""
    private var password = ""
    private var CPassword = ""
    private fun validarInformacion () {
        nombres = binding.etNombreV.text.toString().trim()
        email = binding.etEmailV.text.toString().trim()
        password = binding.etPasswordV.text.toString().trim()
        CPassword = binding.etCPasswordV.text.toString().trim()

        if (nombres.isEmpty()){
            binding.etNombreV.error = "ingreses sos nombres"
            binding.etNombreV.requestFocus()

        }else if (email.isEmpty()){
            binding.etEmailV.error = "ingreses su email"
            binding.etEmailV.requestFocus()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmailV.error = "Email no valido"
            binding.etEmailV.requestFocus()
        } else if (password.isEmpty()){
            binding.etPasswordV.error = "Ingrese su password"
            binding.etPasswordV.requestFocus()
        } else if (password.length < 6){
            binding.etPasswordV.error = "necesita 6 o mas caracteres"
            binding.etPasswordV.requestFocus()
        } else if (CPassword.isEmpty()){
            binding.etPasswordV.error = "confirme password"
            binding.etPasswordV.requestFocus()
        }else if (password !=CPassword){
            binding.etCPasswordV.error = "No coincide"
            binding.etCPasswordV.requestFocus()
        }else {
            registrarVendedor()
        }

    }

    private fun registrarVendedor() {
        progressDialog.setMessage("Creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email , password)
            .addOnSuccessListener {
                insertarInfoBD()
                

            }
            .addOnFailureListener{ e->
                Toast.makeText(this , "fallo el registro debido a ${e.message} " ,Toast.LENGTH_SHORT).show()
            }
    }

    private fun insertarInfoBD() {
        progressDialog.setMessage("Guardando informacion...")

        val uidBD = firebaseAuth.uid
        val nombreBD = nombres
        val emailBD = email
        val tipoUsuario = "vendedor"
        val tiempoBD = Constantes().obtenerTiempoD()


        val datosVendedor = HashMap<String , Any>()

        datosVendedor["uid"] = "$uidBD"
        datosVendedor["nombres"] = "$nombreBD"
        datosVendedor["email"] = "$emailBD"
        datosVendedor["tipoUsuario"]="vendedor"
        datosVendedor["tiempo_registro"] = tiempoBD


        val references = FirebaseDatabase.getInstance().getReference("Usuarios")
        references.child(uidBD!!)
            .setValue(datosVendedor)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this , MainActivityVendedor::class.java))
                finish()

            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this , "fallo en BD debido a ${e.message}" ,Toast.LENGTH_SHORT).show()
            }
    }



}