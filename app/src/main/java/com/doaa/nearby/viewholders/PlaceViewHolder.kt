/*
 * Created by Doaa Fouad on 10/26/19 2:28 AM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/26/19 2:28 AM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doaa.nearby.model.response.Venue
import com.doaa.nearby.util.Constants
import kotlinx.android.synthetic.main.row_place.view.*

class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /*
    * Set venue data => name, address and photo
    * @param venue current venue
    */
    fun bind(venue: Venue?) {
        itemView.tv_locationName?.text = venue?.name
        itemView.tv_locationAddress?.text = venue?.location?.address
        itemView.context?.let {
            val icon = venue?.category?.get(0)?.icon
            Glide.with(it)
                .load("${icon?.prefix}bg_${Constants.ICON_PREFERED_SIZE}${icon?.suffix}") //Prefix + bg + Size + Suffix
                .into(itemView.iv_locationImg)
        }
    }
}