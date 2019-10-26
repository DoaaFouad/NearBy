/*
 * Created by Doaa Fouad on 10/25/19 6:21 PM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/25/19 6:21 PM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.repository

import com.doaa.nearby.model.Location
import com.doaa.nearby.model.response.PlacesResponse
import com.doaa.nearby.networking.APIService
import com.doaa.nearby.util.Constants
import io.reactivex.Single

class PlacesRepository(val apiService: APIService) {

    /*
    * Network call request to fetch all nearby places to the location
    * @param location current user's location
    **/
    fun fetchNearbyPlaces(location: Location): Single<PlacesResponse> =
        apiService.getNearbyPlaces(
            "${location.lat},${location.lng}",
            Constants.EXPLORE_RADIUS,
            Constants.CLIENT_ID,
            Constants.CLIENT_SECRET,
            Constants.VERSIONING
        )
}