/*
 * Created by Doaa Fouad on 10/25/19 8:44 PM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/25/19 8:44 PM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.model.response

import com.doaa.nearby.model.Venue
import com.google.gson.annotations.SerializedName

data class LocationsResponse(
    @SerializedName("meta") var metadata: MetaData?,
    @SerializedName("response") var response: ResponseData?
)

data class MetaData(
    @SerializedName("code") var code: Int?,
    @SerializedName("errorType") var errorType: String?
)

data class ResponseData(
    @SerializedName("groups") var groups: MutableList<GroupData>?
)

data class GroupData(
    @SerializedName("items") var items: MutableList<ItemData>?
)

data class ItemData(
    @SerializedName("venue") var venue: Venue?
)