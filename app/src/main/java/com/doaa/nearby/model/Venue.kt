/*
 * Created by Doaa Fouad on 10/26/19 12:26 AM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/26/19 12:26 AM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.model

import com.google.gson.annotations.SerializedName

data class Venue(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String
)