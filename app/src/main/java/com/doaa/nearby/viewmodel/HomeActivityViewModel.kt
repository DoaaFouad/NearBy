/*
 * Created by Doaa Fouad on 10/25/19 4:48 AM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/25/19 3:52 AM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.viewmodel

import androidx.lifecycle.MutableLiveData
import com.doaa.nearby.model.Location
import com.doaa.nearby.model.response.PlacesResponse
import com.doaa.nearby.repository.PlacesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeActivityViewModel(val repo: PlacesRepository) : BaseViewModel() {

    private var isLoading = MutableLiveData<Boolean>()
    var places = MutableLiveData<PlacesResponse>()

    /*
    * Request and fetch nearby places relative to the given location
    * Then handle success or failure of request
    * @param currentLocation user's current location
    * */
    fun requestNearbyPlaces(currentLocation: Location): MutableLiveData<PlacesResponse> {
        val placesObservable = repo.fetchNearbyPlaces(currentLocation)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                places -> handleNearbyPlacesSuccess(places)
            }, {err -> handleNearbyPlacesFailure(err)})

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

}