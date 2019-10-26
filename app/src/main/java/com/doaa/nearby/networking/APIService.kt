/*
 * Created by Doaa Fouad on 10/25/19 5:47 PM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/25/19 5:47 PM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.networking

import com.doaa.nearby.model.response.PlacesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/v2/venues/explore")
    fun getNearbyPlaces(@Query("ll") ll: String?, @Query("radius") radius: String?,
                        @Query("client_id") clientId: String,
                        @Query("client_secret") clientSecret: String,
                        @Query("v") versioning: String): Single<PlacesResponse>
}