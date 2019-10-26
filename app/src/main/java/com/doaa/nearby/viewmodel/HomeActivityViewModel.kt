/*
 * Created by Doaa Fouad on 10/25/19 4:48 AM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/25/19 3:52 AM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.doaa.nearby.model.response.PlacesResponse
import com.doaa.nearby.repository.PlacesRepository
import com.doaa.nearby.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeActivityViewModel(val repo: PlacesRepository) : BaseViewModel() {

    private var isLoading = MutableLiveData<Boolean>()
    private var places = MutableLiveData<PlacesResponse>()
    private var doRequestNewUpdate = MutableLiveData<Boolean>()
    private var lastKnownLocation: Location? = null

    /*
    * Request and fetch nearby places relative to the given location
    * Then handle success or failure of request
    * @param currentLocation user's current location
    * */
    fun requestNearbyPlaces(currentLocation: com.doaa.nearby.model.Location): MutableLiveData<PlacesResponse> {
        val placesObservable = repo.fetchNearbyPlaces(currentLocation)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ places ->
                handleNearbyPlacesSuccess(places)
            }, { err -> handleNearbyPlacesFailure(err) })

        compositeDisposable.add(placesObservable)

        return places
    }

    private fun handleNearbyPlacesSuccess(places: PlacesResponse) {
        this.places.value = places
        isLoading.value = false
    }

    private fun handleNearbyPlacesFailure(err: Throwable) {
        isLoading.value = false
    }

    fun isLoading(): MutableLiveData<Boolean> {
        return this.isLoading
    }

    fun doRequestNewUpdate(): MutableLiveData<Boolean> {
        return this.doRequestNewUpdate
    }

    /*
    * Calculate distance in meters between two latlng points,
    * If greater than X meters => set doRequestNewUpdate true
    * @param currentLocation new point that needs to be calculated relative to the last known location
    **/
    fun calculateDistance(currentLocation: Location) {
        lastKnownLocation?.let {
            val results = FloatArray(1)
            Location.distanceBetween(
                it.latitude,
                it.longitude,
                currentLocation.latitude,
                currentLocation.longitude,
                results
            )
            doRequestNewUpdate.value = results[0] > Constants.CHECK_METERS_ALLOWED
            lastKnownLocation = currentLocation
        } ?: kotlin.run {
            lastKnownLocation = currentLocation
            doRequestNewUpdate.value = true
        }
    }

}