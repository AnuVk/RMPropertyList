package com.example.rmove.rmpropertylist.ui

import com.example.rmove.rmpropertylist.model.PropertyDetails
import java.math.BigDecimal

class PropertyListContract {
    interface PropertyView {
        fun showProgress()
        fun hideProgress()
        fun showErrorMessage(errorMessage: String)
        fun populateAverage(average: String)
        fun populateDetachedAverage(average: String)
    }

    interface PropertyListPresenter {
        fun initialise(view: PropertyView)
        fun getPropertyList()
        suspend fun getPropertyListByCo()
        fun calculateAverage(propertyDetailsList: List<PropertyDetails>): BigDecimal

    }
}