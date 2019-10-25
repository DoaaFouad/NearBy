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
import com.doaa.nearby.model.response.LocationsResponse
import com.doaa.nearby.networking.APIService
import com.doaa.nearby.util.Constants
import io.reactivex.Single

class LocationsRepository(val apiService: APIService) {

    fun fetchNearbyLocations(location: Location): Single<LocationsResponse> =
        apiService.getNearbyLocations(
            "${location.lat},${location.lng}",
            Constants.EXPLORE_RADIUS,
            Constants.CLIENT_ID,
            Constants.CLIENT_SECRET,
            Constants.VERSIONING
        )
}