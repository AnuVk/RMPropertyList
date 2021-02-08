package com.example.rmove.rmpropertylist.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.example.rmove.rmpropertylist.R
import com.example.rmove.rmpropertylist.RMApp
import com.example.rmove.rmpropertylist.di.component.DaggerActivityComponent
import com.example.rmove.rmpropertylist.di.module.ActivityModule
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class PropertyActivity : AppCompatActivity(), PropertyListContract.PropertyView {

    @Inject
    lateinit var presenter: PropertyListContract.PropertyListPresenter

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.question_tv)
    internal lateinit var questionTv: TextView

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.answer_tv)
    internal lateinit var answerTv: TextView

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.property_main_layout)
    internal lateinit var mainPropertyLayout: ConstraintLayout

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.progess_bar)
    internal lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.property_main_layout)

        injectDependency()
        ButterKnife.bind(this)

        presenter.initialise(this)
        presenter.getPropertyList()
    }

    fun injectDependency() {

        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .applicationComponent(RMApp.appComponent)
            .build()
        activityComponent.inject(this)
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE

    }

    override fun showErrorMessage(errorMessage: String) {
        Snackbar.make(mainPropertyLayout, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    override fun populateAverage(average: String) {
        answerTv.text = average
    }

    override fun populateDetachedAverage(average: String) {
        TODO("Not yet implemented")
    }
}