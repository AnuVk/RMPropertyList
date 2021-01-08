package com.example.rmove.rmpropertylist.api

import com.example.rmove.rmpropertylist.model.PropertyList
import io.reactivex.Observable
import retrofit2.http.GET


interface PropertyListApi {

    @GET("master/properties.json")
    fun getPropertyDetails(): Observable<PropertyList>

}