package com.example.rmove.rmpropertylist.model

data class PropertyList(val properties: List<PropertyDetails>)

data class PropertyDetails(
    val id: Int,
    val price: Double,
    val bedrooms: Int,
    val bathrooms: Int,
    val number: String,
    val region: String,
    val postcode: String,
    val address: String,
    val propertyType: String
)