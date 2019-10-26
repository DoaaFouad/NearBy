/*
 * Created by Doaa Fouad on 10/25/19 4:48 AM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/25/19 4:48 AM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {

    protected abstract val viewModel: VM
    protected abstract val layoutRes: Int //used to set content view of every activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutRes)
        initViews()
        observeViewModel()
    }

    abstract fun initViews()
    abstract fun observeViewModel()
}