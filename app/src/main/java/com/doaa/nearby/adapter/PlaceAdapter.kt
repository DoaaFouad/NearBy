/*
 * Created by Doaa Fouad on 10/26/19 2:24 AM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/26/19 2:24 AM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doaa.nearby.R
import com.doaa.nearby.model.response.ItemData
import com.doaa.nearby.viewholders.PlaceViewHolder

class PlaceAdapter : RecyclerView.Adapter<PlaceViewHolder>() {

    private var placesList: MutableList<ItemData> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_place, parent, false)

        return PlaceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return placesList.size
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(placesList[position].venue)
    }

    fun setData(placesData: MutableList<ItemData>?) {
        if (!placesData.isNullOrEmpty()) {
            //placesList.clear()
            placesList = placesData
            Log.d("SizePlaces", "" + placesData?.size)
            notifyDataSetChanged()
        }
    }
}