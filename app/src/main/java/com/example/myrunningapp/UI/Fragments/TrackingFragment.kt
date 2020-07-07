package com.example.myrunningapp.UI.Fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myrunningapp.R
import com.example.myrunningapp.UI.ViewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.trackingfragmentlayout) {

    private val mainViewModel : MainViewModel by viewModels()
}