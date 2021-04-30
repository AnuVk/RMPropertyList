package com.example.rmove.rmpropertylist.api

import com.example.rmove.rmpropertylist.model.PropertyList
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET


interface PropertyListApi {

    @GET("master/properties.json")
    fun getPropertyDetails(): Observable<PropertyList>

    @GET("master/properties.json")
    suspend fun getPropertyDetailsByCo(): PropertyList


}