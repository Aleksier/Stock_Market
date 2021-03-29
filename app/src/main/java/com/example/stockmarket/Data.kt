package com.example.stockmarket

import androidx.lifecycle.MutableLiveData

object Data {
    var listOfTickers: MutableList<String> = mutableListOf()
    var listOfNames: MutableList<String> = mutableListOf()
    var listOfQuotes: MutableList<String> = mutableListOf()
    var listOfPreviousQuotes: MutableList<String> = mutableListOf()
    var listOfCompanies: MutableList<Company> = mutableListOf()
    val companies: MutableLiveData<List<Company>> by lazy {
        MutableLiveData<List<Company>>()
    }
    var isLoadingFinished: Boolean = false
}