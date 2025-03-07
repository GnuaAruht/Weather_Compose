package com.gnua_aruht.weather_compose.data.utils

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import com.gnua_aruht.weather_compose.UserPref
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

val Context.userPrefsDataStore : DataStore<UserPref> by dataStore(
    fileName = "user_pref.pb",
    serializer = UserPrefSerializer,
    corruptionHandler = ReplaceFileCorruptionHandler { UserPref.getDefaultInstance() }
)

val UserPref.selectedTemp: TemperatureUnit
    get() = TemperatureUnit.entries[tempUnit]

val UserPref.selectedWindSpeed: WindSpeedUnit
    get() = WindSpeedUnit.entries[windSpeedUnit]

val UserPref.selectedPressure: PressureUnit
    get() = PressureUnit.entries[pressureUnit]

val UserPref.selectedTimeFormat: TimeFormat
    get() = TimeFormat.entries[timeUnit]

object UserPrefSerializer : Serializer<UserPref> {

    override val defaultValue: UserPref = UserPref.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPref {
        try {
            return UserPref.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserPref, output: OutputStream) {
        t.writeTo(output)
    }


}