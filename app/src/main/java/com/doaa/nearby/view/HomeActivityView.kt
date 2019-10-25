/*
 * Created by Doaa Fouad on 10/25/19 4:48 AM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/25/19 3:41 AM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.view

import com.doaa.nearby.R
import com.doaa.nearby.model.Location
import com.doaa.nearby.viewmodel.HomeActivityViewModel
import org.koin.android.ext.android.inject

class HomeActivityView : BaseActivity<HomeActivityViewModel>() {

    override val viewModel by inject<HomeActivityViewModel>()
    override val layoutRes = R.layout.home_activity
}