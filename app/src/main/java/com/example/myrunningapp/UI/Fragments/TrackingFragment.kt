package com.example.myrunningapp.UI.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myrunningapp.Constants
import com.example.myrunningapp.R
import com.example.myrunningapp.UI.ViewModels.MainViewModel
import com.example.myrunningapp.services.TrackingService
import com.google.android.gms.maps.GoogleMap
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.trackingfragmentlayout.*

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.trackingfragmentlayout) {

    private var map : GoogleMap? = null
    private val mainViewModel : MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            it == map
        }

        btnToggleRun.setOnClickListener {
            SendAction(Constants.SERVICE_START_RESUME)
        }

    }

    private fun SendAction(action : String){
        Intent(requireContext(),TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
    }
    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}