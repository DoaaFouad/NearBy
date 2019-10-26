/*
 * Created by Doaa Fouad on 10/25/19 4:48 AM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/25/19 3:41 AM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.view

import androidx.recyclerview.widget.LinearLayoutManager
import com.doaa.nearby.R
import com.doaa.nearby.adapter.PlaceAdapter
import com.doaa.nearby.model.Location
import com.doaa.nearby.model.response.PlacesResponse
import com.doaa.nearby.viewmodel.HomeActivityViewModel
import kotlinx.android.synthetic.main.home_activity.*
import org.koin.android.ext.android.inject

class HomeActivityView : BaseActivity<HomeActivityViewModel>() {

    override val viewModel by inject<HomeActivityViewModel>()
    override val layoutRes = R.layout.home_activity

    private val placeAdapter = PlaceAdapter()

    override fun initViews() {
        setUpPlacesRecyclerViewer()
    }

    override fun observeViewModel() {
        viewModel.requestNearbyPlaces(Location(31.2717393, 30.0159572)).observe({
            this.lifecycle
        }, { handlePlacesData(it) })

        viewModel.isLoading().observe({
            this.lifecycle
        }, { if (it) showLoading() else hideLoading() })
    }

    /*
    * Initial setup of recyclerviewer layoutManager and adapter
    */
    private fun setUpPlacesRecyclerViewer() {
        rv_places?.layoutManager = LinearLayoutManager(this)
        rv_places?.adapter = placeAdapter
    }

    /*
    * Handle observed places data and set it to adapter's data
    */
    private fun handlePlacesData(places: PlacesResponse) {
        placeAdapter.setData(places.response?.groups?.get(0)?.items)
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }

    private fun showError() {

    }

    private fun showNullState() {

    }

}