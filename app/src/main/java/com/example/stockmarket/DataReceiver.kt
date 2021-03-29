package com.example.stockmarket

import android.util.Log
import kotlinx.coroutines.*


object DataReceiver {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    //private val listener: ProgressBar? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e("HTTP","ERROR")
            //listener?.turnOff()
    }

    suspend fun getListOfNames(listOfTickers: List<String>) = withContext(Dispatchers.IO + exceptionHandler) {
    var listOfNames: MutableList<String> = mutableListOf()
        for (companyTicker in listOfTickers) {
            coroutineScope {
                launch {
                    val companyName = RetrofitModule.companyApi.getName(companyTicker, Constants.API_KEY)
                    listOfNames.add(companyName.name.substring(0, companyName.name.length - 4))
                    Log.e("listnames", "$listOfNames")
                }
            }
        }
        Data.listOfNames = listOfNames
    }

    suspend fun getListOfTickers() = withContext(Dispatchers.IO + exceptionHandler) {
        var listOfTickers: MutableList<String> = mutableListOf()
        listOfTickers = RetrofitModule.companyApi.getTickers(Constants.ETF_KEY, Constants.API_KEY).tickers.sorted().subList(0, 20).toMutableList()
        Data.listOfTickers = listOfTickers
    }

    suspend fun getListOfQuotes(listOfIndexies: List<String>) = withContext(Dispatchers.IO + exceptionHandler) {
        var listOfQuotes: MutableList<String> = mutableListOf()
        var listOfPreviousQuotes: MutableList<String> = mutableListOf()
        for (companySymbol in listOfIndexies) {
            coroutineScope {
                launch{
                    val companyQuote = RetrofitModule.companyApi.getQuote(companySymbol, Constants.API_KEY)
                    listOfQuotes.add(companyQuote.currentPrice)
                    listOfPreviousQuotes.add(companyQuote.previousPrice)
                    Log.e("listquoes", "$listOfQuotes")
                }
            }
        }
        Data.listOfQuotes = listOfQuotes
        Data.listOfPreviousQuotes = listOfPreviousQuotes
    }

    suspend fun load() {
        coroutineScope{
            coroutineScope {
                getListOfTickers()
            }
            getListOfNames(Data.listOfTickers)
            getListOfQuotes(Data.listOfTickers)
        }
    }

    fun setData() {
        for (i in 0..(Data.listOfTickers.size - 1)) {
            Data.listOfCompanies.add(Company(Data.listOfNames[i],  Data.listOfTickers[i], Data.listOfQuotes[i], Data.listOfPreviousQuotes[i]))
        }
    }

     suspend fun listCompanies() {
         coroutineScope {
             launch { load() }
         }
         Data.isLoadingFinished = true
         setData()
         Log.e("D1ATA", "${Data.listOfCompanies}")
         Data.companies.value = Data.listOfCompanies
     }

    /*
    interface ProgressBar{
        fun turnOff()
    }

     */
}