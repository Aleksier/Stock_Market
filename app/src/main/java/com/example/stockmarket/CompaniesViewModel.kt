package com.example.stockmarket


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class CompaniesViewModel : ViewModel() {
    var companies: LiveData<List<Company>>
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("HTTP","ERROR")
    }

    init {
        companies = loadCompanies()
    }

    private fun loadCompanies(): LiveData<List<Company>> {
        viewModelScope.launch(exceptionHandler) {
            coroutineScope {
                DataReceiver.listCompanies()
            }
        }

        while (true) {
            if(Data.companies.value?.size != 0)
            {
                return Data.companies
            }
            else
                continue
        }
    }
}