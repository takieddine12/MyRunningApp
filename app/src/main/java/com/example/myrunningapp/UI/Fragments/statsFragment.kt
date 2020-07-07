package com.example.myrunningapp.UI.Fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myrunningapp.R
import com.example.myrunningapp.UI.ViewModels.MainViewModel
import com.example.myrunningapp.UI.ViewModels.StatsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class statsFragment : Fragment(R.layout.statisfragmentlayout) {

    private val mainViewModel : StatsViewModel by viewModels()
}