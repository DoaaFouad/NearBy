/*
 * Created by Doaa Fouad on 10/25/19 6:24 PM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/25/19 6:24 PM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby

import android.app.Application
import android.content.Context
import com.doaa.nearby.di.apiModule
import com.doaa.nearby.di.repositoryModule
import com.doaa.nearby.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NearByApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin()
    }

    private fun startKoin(){
        startKoin{
            androidLogger()
            androidContext(this@NearByApplication)
            modules(listOf(viewModelModule, repositoryModule, apiModule))
        }
    }

    companion object {
        const val SHARED_PREF_NAME = "NEARBY_SHARED_PREF"
        const val SHARED_PREF_MODE = Context.MODE_PRIVATE
    }
}