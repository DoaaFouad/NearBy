# NearBy
Android app written in Kotlin for displaying realtime nearby places around you by consuming Foursquare Places API.

## Screenshots

<img src="https://serving.photos.photobox.com/56537153d3b5e5aec876d4ef9ef0e08b88aae415691119946b3464395f184b40d80bb9de.jpg" width="300" height="600"/>         <img src="https://serving.photos.photobox.com/2507815118a435e66d957cd17fcabf8132e348c0dd15c2b2771dd68c2b8b3e052826cf4a.jpg" width="300" height="600"/> <img src="https://serving.photos.photobox.com/4925047373f3544d7bcae283c5f88f78997546f36d8366089334edd7536135748d157698.jpg" width="300" height="600"/> <img src="https://serving.photos.photobox.com/22267073828e230dd92e575eddb989e120b9de7a85cecd26c30c55737ff26696e4517347.jpg" width="300" height="600"/>



## App Architecture
Model-View-ViewModel(MVVM) 

## The app has following packages:
- view: It contains Activity/Fragment related classes.
- viewmodel: It contains classes responsible for managing the data for an Activity/Fragment .
- model: It contains all domain models.
- repository: It contains all classes responisble of fetching data.
- networking: It contains API services.
- di: It contains Dependency modules using Koin.
- adapter: It contains all recyclerviews adapters.
- utils: It contains utility classes.

## Libraries references:
#### Reactive
RxJava2: https://github.com/ReactiveX/RxJava
#### Dependency Injection
Koin: https://github.com/InsertKoinIO/koin
#### Networking
Retrofit: https://github.com/square/retrofit

Gson: https://github.com/google/gson

OKhttp: https://github.com/square/okhttp

#### UI
Glide: https://github.com/bumptech/glide

Shimmer: https://github.com/facebook/shimmer-android
