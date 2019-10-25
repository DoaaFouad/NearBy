/*
 * Created by Doaa Fouad on 10/25/19 5:20 PM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/25/19 5:20 PM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.di

import com.doaa.nearby.networking.APIService
import com.doaa.nearby.util.Constants
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val HTTP_LOGGING_INTERCEPTOR = "HTTP_LOGGING_INTERCEPTOR"

val apiModule = module {

    single { Gson() }

    // Add Okhttp logging interceptor
    single (named(HTTP_LOGGING_INTERCEPTOR)) {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        logInterceptor as Interceptor
    }

    single {
        OkHttpClient.Builder().addInterceptor(get(named(HTTP_LOGGING_INTERCEPTOR))).build()
    }

    // Build Retrofit
    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
            .create(APIService::class.java)
    }

}