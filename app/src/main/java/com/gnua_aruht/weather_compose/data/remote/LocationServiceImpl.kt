package com.gnua_aruht.weather_compose.data.remote

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.gnua_aruht.weather_compose.data.utils.LocationServiceDisableException
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume


class LocationServiceImpl @Inject constructor(@ApplicationContext val context: Context) : LocationService {

    @SuppressLint("MissingPermission")
    override suspend fun currentLocation(): Location? {
        if (!checkIfLocationServiceEnable()) throw LocationServiceDisableException()
        return suspendCancellableCoroutine { cont ->
            LocationServices.getFusedLocationProviderClient(context).lastLocation.apply {
                addOnSuccessListener { location ->
                    cont.resume(location)
                }
                addOnFailureListener {
                    cont.cancel(it.cause)
                }
                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
    }


    private fun checkIfLocationServiceEnable(): Boolean {
        val locManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return isGpsEnabled && isNetworkEnabled
    }


}