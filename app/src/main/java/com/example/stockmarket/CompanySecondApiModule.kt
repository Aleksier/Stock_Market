package com.example.stockmarket

/*

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

@Serializable
data class CompanyInformationAboutQuote(
        @SerialName("ask")
        val currentPrice: String
)

interface CompanyApi2 {
    @GET("qu/quote")
    suspend fun getListOfExtendedQuote(
            @Query("symbol") symbol: String,
            @Query("token") token: String
    ): List<CompanyInformationAboutQuote>
}

object SecondRetrofitModule {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL2)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    val companyApi2: CompanyApi2 = retrofit.create()
}

*/