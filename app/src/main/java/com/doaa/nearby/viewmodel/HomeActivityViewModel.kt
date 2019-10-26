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
import com.doaa.nearby.repository.SharedPreferencesRepository
import com.doaa.nearby.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeActivityViewModel(val placesRepo: PlacesRepository, val sharedPrefRepo: SharedPreferencesRepository) : BaseViewModel() {

    private var isLoading = MutableLiveData<Boolean>()
    private var places = MutableLiveData<PlacesResponse>()
    private var realTimeConfig = MutableLiveData<Boolean>()

    private var lastKnownLocation: Location? = null

    init {
        realTimeConfig.value = getRealTimeUpdateConfig()
    }

    /*
    * Request and fetch nearby places relative to the given location
    * Then handle success or failure of request
    * @param currentLocation user's current location
    * */
    private fun requestNearbyPlaces(currentLocation: com.doaa.nearby.model.Location) {
        val placesObservable = placesRepo.fetchNearbyPlaces(currentLocation)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ places ->
                handleNearbyPlacesSuccess(places)
            }, { err -> handleNearbyPlacesFailure(err) })

        compositeDisposable.add(placesObservable)
    }

    private fun handleNearbyPlacesSuccess(places: PlacesResponse) {
        this.places.value = places
        isLoading.value = false
    }

    private fun handleNearbyPlacesFailure(err: Throwable) {
        isLoading.value = false
    }
    /*
    * Calculate distance in meters between two latlng points,
    * If greater than X meters => make fetch request
    * @param currentLocation new point that needs to be calculated relative to the last known location
    **/
    fun calculateDistance(currentLocation: Location) {
        // TODO make calculations on background thread RX
        lastKnownLocation?.let {
            val results = FloatArray(1)
            Location.distanceBetween(
                it.latitude,
                it.longitude,
                currentLocation.latitude,
                currentLocation.longitude,
                results
            )
            if(results[0] > Constants.CHECK_METERS_ALLOWED){
                requestNearbyPlaces(com.doaa.nearby.model.Location(currentLocation.latitude, currentLocation.longitude))
                lastKnownLocation = currentLocation
            }
        } ?: kotlin.run {
            requestNearbyPlaces(com.doaa.nearby.model.Location(currentLocation.latitude, currentLocation.longitude))
            lastKnownLocation = currentLocation
        }
    }

    /*
    * Remember user realtime/single config by saving it to shared pref
    */
    fun setRealTimeUpdateConfig(){
        val newRealTimeConfig = realTimeConfig.value?.not()
        newRealTimeConfig?.let { sharedPrefRepo.putBoolean(Constants.BOOLEAN_REATIME_CONFIG, it)}
    }

    fun getRealTimeUpdateConfig(): Boolean? {
      return sharedPrefRepo.getBoolean(Constants.BOOLEAN_REATIME_CONFIG, true) //default is realtime on = true
    }

    fun isLoading(): MutableLiveData<Boolean> {
        return this.isLoading
    }

    fun updatedPlaces(): MutableLiveData<PlacesResponse> {
        return this.places
    }

    fun realTimeConfig(): MutableLiveData<Boolean> {
        return this.realTimeConfig
    }

}