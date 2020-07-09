package com.example.myrunningapp.UI.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.myrunningapp.services.Polyline
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myrunningapp.Constants
import com.example.myrunningapp.R
import com.example.myrunningapp.UI.ViewModels.MainViewModel
import com.example.myrunningapp.services.PolyLines
import com.example.myrunningapp.services.TrackingService
import com.example.myrunningapp.services.TrackingService.Companion.pathPoints
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.trackingfragmentlayout.*

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.trackingfragmentlayout) {

    private var map : GoogleMap? = null
    private val mainViewModel : MainViewModel by viewModels()
    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()

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

    private fun moveCameraToUser(){
        if(pathPoints.isNotEmpty() && pathPoints.size > 1){
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(pathPoints.last().last(),
                    Constants.CAMERA_ZOOM
            ))
        }
    }


    private fun ToggleRun(){
        if(isTracking){
            SendAction(Constants.ACTION_PAUSE_SERVICE)
        }else {
            SendAction(Constants.SERVICE_START_RESUME)
        }
    }
    private fun updateTracking(isTracking : Boolean){
        this.isTracking = isTracking
        if(!isTracking){
            btnToggleRun.text = "START"
            btnFinishRun.visibility = View.VISIBLE
        }else{
            btnToggleRun.text = "STOP"
            btnFinishRun.visibility = View.INVISIBLE
        }
    }
    private fun AddAllPolyLines(){
        for( polyline in pathPoints){
            var polylineOptions = PolylineOptions()
                .color(Constants.POLYLINE_COLOR)
                .width(Constants.POLYLINE_WIDTH)
                .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun getLastestPolylines(){
        if(pathPoints.isNotEmpty() && pathPoints.size > 1){
            var preLastLatLng =  pathPoints.last()[pathPoints.size - 2]
            var lastLatLng = pathPoints.last().last()
            var polyLineOptions = PolylineOptions().apply{
                color(Constants.POLYLINE_COLOR)
                width(Constants.POLYLINE_WIDTH)
                add(preLastLatLng)
                add(lastLatLng)
            }
            map?.addPolyline(polyLineOptions)

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