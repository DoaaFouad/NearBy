/*
 * Created by Doaa Fouad on 10/26/19 4:21 PM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/26/19 4:21 PM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.repository

import android.content.SharedPreferences

open class SharedPreferencesRepository(private val sharedPreferences: SharedPreferences) {

    open fun getBoolean(name: String, defVal: Boolean): Boolean? =
        sharedPreferences.getBoolean(name, defVal)

    open fun putBoolean(name: String, value: Boolean) =
        sharedPreferences.edit().putBoolean(name, value).apply()
}