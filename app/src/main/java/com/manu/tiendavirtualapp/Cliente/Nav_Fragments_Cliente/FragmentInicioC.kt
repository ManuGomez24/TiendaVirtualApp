package com.manu.tiendavirtualapp.Cliente.Nav_Fragments_Cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manu.tiendavirtualapp.Cliente.Bottom_Nav_Fragments_Cliente.FragmentMisOrdenesC
import com.manu.tiendavirtualapp.Cliente.Bottom_Nav_Fragments_Cliente.FragmentTiendC
import com.manu.tiendavirtualapp.R
import com.manu.tiendavirtualapp.databinding.FragmentInicioCBinding


class FragmentInicioC : Fragment() {

    private lateinit var binding : FragmentInicioCBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        binding = FragmentInicioCBinding.inflate(inflater,container, false)

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.op_tienda_c->{
                    replaceFragment(FragmentTiendC())

                }
                R.id.op_mis_ordenes_c->{
                    replaceFragment(FragmentMisOrdenesC())
                }
            }
            true

        }
        replaceFragment(FragmentTiendC())
        binding.bottomNavigation.selectedItemId = R.id.op_tienda_c



        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.bottomFragment , fragment)
            .commit()
    }


}