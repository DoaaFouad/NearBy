/*
 * Created by Doaa Fouad on 10/25/19 5:46 PM
 * Copyright (c) 2019 , Doaa Fouad . All rights reserved.
 * Last modified 10/25/19 5:46 PM
 *
 * Email: doaa_fouad2006@hotmail.com
 * LinkedIn: /doaafouad
 */

package com.doaa.nearby.util

class Constants {

    companion object {
        // FOURSQAURE
        const val BASE_URL = "https://api.foursquare.com/"
        const val EXPLORE_RADIUS = "1000"
        const val CLIENT_ID = "0OR3HH4OYW2FR2XNMZJ51QHDAYUJFXBPWYUTKVHGV0BX03GL"
        const val CLIENT_SECRET = "4ZDETA0ZZQOV5RC4PJH15DCNC1TXYU0CUMLOGOLE24SSTQ0K"
        const val VERSIONING = "20191024" //TODO let this automatically according to today's date

        //Permissions
        const val MY_PERMISSIONS_REQUEST_FINE_LOCATION = 2000

        //Config
        const val LOCATIONS_UPDATES_INTERVAL = 10000L
        const val LOCATIONS_UPDATES_FATEST_INTERVAL = 5000L

    }
}