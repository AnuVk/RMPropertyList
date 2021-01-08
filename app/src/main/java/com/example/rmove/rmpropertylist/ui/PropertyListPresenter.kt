package com.example.rmove.rmpropertylist.ui

import androidx.annotation.VisibleForTesting
import com.example.rmove.rmpropertylist.api.PropertyListApi
import com.example.rmove.rmpropertylist.model.PropertyDetails
import com.example.rmove.rmpropertylist.utils.SchedulerHelper
import io.reactivex.disposables.CompositeDisposable
import java.math.BigDecimal
import javax.inject.Inject

class PropertyListPresenter @Inject constructor(
    val propertyListApi: PropertyListApi,
    val schedulerHelper: SchedulerHelper
) : PropertyListContract.PropertyListPresenter {

    private lateinit var propertyView: PropertyListContract.PropertyView

    //    private lateinit var propertyListApi: PropertyListApi
    @VisibleForTesting
    val compositeDisposable = CompositeDisposable()

    override fun initialise(view: PropertyListContract.PropertyView) {
        propertyView = view
    }

    override fun getPropertyList() {
        propertyView.showProgress()
        compositeDisposable.add(
            propertyListApi.getPropertyDetails()
                .subscribeOn(schedulerHelper.getIoScheduler())
                .observeOn(schedulerHelper.getMainThreadScheduler())
                .subscribe(
                    { value -> onSuccess(value.properties) },
                    { error -> onFailure(error) },
                    { println("Completed!") }
                ))
    }


    private fun onFailure(throwable: Throwable) {
        propertyView.hideProgress()
        propertyView.showErrorMessage(throwable.message.toString())
    }


    private fun onSuccess(propertyDetailsList: List<PropertyDetails>) {
        propertyView.hideProgress()
        if (propertyDetailsList.isEmpty()) {
            propertyView.showErrorMessage("No property data retrieved")
        } else {
            val average = calculateAverage(propertyDetailsList)
            propertyView.populateAverage(average.toString())
        }
    }

    override fun calculateAverage(propertyDetailsList: List<PropertyDetails>): BigDecimal {
        val list: MutableList<Double> = mutableListOf()
        val map = propertyDetailsList.map { p -> p.price }
        map.toCollection(list)
//        Log.d(">>>","Avg :"+list.average())
        return list.average().toBigDecimal()

    }
}