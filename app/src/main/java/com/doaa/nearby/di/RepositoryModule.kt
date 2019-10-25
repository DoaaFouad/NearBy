/*
 * Created by Doaa Fouad on 10/25/19 6:13 PM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/25/19 6:13 PM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.di

import com.doaa.nearby.repository.LocationsRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<LocationsRepository> { LocationsRepository() }
}