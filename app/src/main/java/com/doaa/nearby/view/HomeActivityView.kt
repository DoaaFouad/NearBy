/*
 * Created by Doaa Fouad on 10/25/19 4:48 AM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/25/19 3:41 AM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.doaa.nearby.R
import com.doaa.nearby.adapter.PlaceAdapter
import com.doaa.nearby.model.response.PlacesResponse
import com.doaa.nearby.util.Constants
import com.doaa.nearby.viewmodel.HomeActivityViewModel
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.home_activity.*
import org.koin.android.ext.android.inject

class HomeActivityView : BaseActivity<HomeActivityViewModel>() {

    override val viewModel by inject<HomeActivityViewModel>()
    override val layoutRes = R.layout.home_activity

    private val placeAdapter = PlaceAdapter()

    //Location services
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun initViews() {
        setUpPlacesRecyclerViewer()
        setupLocationProvider()
    }

    override fun observeViewModel() {
        viewModel.isLoading().observe({
            this.lifecycle
        }, { if (it) showLoading() else hideLoading() })

        viewModel.updatedPlaces().observe({
            this.lifecycle
        }, {
                places -> handlePlacesData(places)
        })
    }

    /*
    * Initial setup of recyclerviewer layoutManager and adapter
    */
    private fun setUpPlacesRecyclerViewer() {
        rv_places?.layoutManager = LinearLayoutManager(this)
        rv_places?.adapter = placeAdapter
    }

    /*
    * Handle observed places data and set it to adapter's data
    */
    private fun handlePlacesData(places: PlacesResponse) {
        placeAdapter.setData(places.response?.groups?.get(0)?.items)
    }

    /**
     * Setup locations services
     * */
    private fun setupLocationProvider() {
        checkUserPermissions()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequestConfig()
        setLocationCallBack()
    }

    private fun locationRequestConfig(): LocationRequest? {
        return LocationRequest.create()?.apply {
            interval = Constants.LOCATIONS_UPDATES_INTERVAL
            fastestInterval = Constants.LOCATIONS_UPDATES_FATEST_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun setLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                viewModel.calculateDistance(locationResult.lastLocation)
            }
        }
    }

    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequestConfig(),
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
      fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun checkUserPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // if permission not guranted, then request
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                Constants.MY_PERMISSIONS_REQUEST_FINE_LOCATION
            )
        } else {
            // permission is guaranteed do nothing
        }
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }

    private fun showError() {

    }

    private fun showNullState() {

    }

    override fun onResume() {
        super.onResume()

        startLocationUpdates()
    }

    override fun onStop() {
        super.onStop()

        stopLocationUpdates()
    }
}