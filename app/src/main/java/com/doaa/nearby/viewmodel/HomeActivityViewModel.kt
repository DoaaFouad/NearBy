/*
 * Created by Doaa Fouad on 10/25/19 4:48 AM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/25/19 3:52 AM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.viewmodel

import com.doaa.nearby.model.Location
import com.doaa.nearby.repository.LocationsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeActivityViewModel(val repo: LocationsRepository) : BaseViewModel() {

    fun requestNearbyLocations(currentLocation: Location) {
        val locationsObservable = repo.fetchNearbyLocations(currentLocation)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        compositeDisposable.add(locationsObservable)
    }

}