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
import android.view.Menu
import android.view.MenuItem
import android.view.View


class HomeActivityView : BaseActivity<HomeActivityViewModel>() {

    override val viewModel by inject<HomeActivityViewModel>()
    override val layoutRes = R.layout.home_activity

    private val placeAdapter = PlaceAdapter()

    //Location services
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private var menu: Menu? = null

    override fun initViews() {
        setUpPlacesRecyclerViewer()
        setupLocationProvider()
    }

    override fun observeViewModel() {
        viewModel.isLoading().observe({
            this.lifecycle
        }, { if (it) showLoading() else hideLoading() })

        viewModel.errorState().observe({
            this.lifecycle
        }, { if (it) showError() else hideError() })

        viewModel.nullState().observe({
            this.lifecycle
        }, { if (it) showNullState() else hideNullState() })

        viewModel.updatedPlaces().observe({
            this.lifecycle
        }, { places ->
            handlePlacesData(places)
        })

        // according to user realtime config decide start or stop location updates requests
        viewModel.realTimeConfig().observe({
            this.lifecycle
        }, {
            if (it) {
                startLocationUpdates()
                setRealTimeConfigUI(false)
            } else {
                stopLocationUpdates()
                setRealTimeConfigUI(true)
            }
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
        rv_places?.visibility = View.VISIBLE
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

    private fun getLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                it?.let { viewModel.calculateDistance(it) }
                    ?: kotlin.run { showError() } //Sometimes last location returns null if the device is new or restored
            }.addOnFailureListener {
                showError()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            Constants.MY_PERMISSIONS_REQUEST_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLastKnownLocation() //If permission granted, then get last known location
                } else {
                    // do nothing, error state already shown
                }
                return
            }
            else -> {
                // do nothing, error state already shown
            }
        }
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }

    private fun showError() {
        layout_error?.visibility = View.VISIBLE
        layout_null?.visibility = View.GONE
        rv_places?.visibility = View.GONE
    }

    private fun showNullState() {
        layout_error?.visibility = View.GONE
        layout_null?.visibility = View.VISIBLE
        rv_places?.visibility = View.GONE
    }

    private fun hideError() {
        layout_error?.visibility = View.GONE
    }

    private fun hideNullState() {
        layout_null?.visibility = View.GONE
    }

    /**
     * Customizable action bar
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        val inflater = menuInflater
        inflater.inflate(com.doaa.nearby.R.menu.action_bar, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // action with ID action_settings was selected
            R.id.action_settings -> viewModel.setRealTimeUpdateConfig()
            else -> {
            }
        }

        return true
    }

    /**
     * Set menu item title according to the realtime config mode
     * @param isOn true if realTime config is true
     */
    private fun setRealTimeConfigUI(isOn: Boolean) {
        menu?.let {
            val title = if (isOn) {
                getString(R.string.actionbar_realtime_on)
            } else {
                getString(R.string.actionbar_realtime_off)
            }
            it.findItem(R.id.action_settings).title = title
        }
    }

    override fun onStart() {
        super.onStart()

        getLastKnownLocation()
    }

    override fun onStop() {
        super.onStop()

        stopLocationUpdates()
    }
}