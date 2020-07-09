package com.example.myrunningapp.UI.Fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myrunningapp.R
import kotlinx.android.synthetic.main.setupfragmentlayout.*

class setupFragment : Fragment(R.layout.setupfragmentlayout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvContinue.setOnClickListener {
            findNavController().navigate(R.id.action_setupFragment_to_runFragment2)
        }
    }
}