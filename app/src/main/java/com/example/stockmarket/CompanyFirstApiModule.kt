package com.example.stockmarket

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
data class Quote(
    @SerialName("c")
    val currentPrice: String,
    @SerialName("o")
    val previousPrice: String
)

@Serializable
data class CompanyTickers(
   @SerialName("constituents")
    val tickers: List<String>
)

@Serializable
data class CompanyName(
        @SerialName("name")
        val name: String
)

interface CompanyApi {
    @GET("quote")
    suspend fun getQuote(
            @Query("symbol") symbol: String,
            @Query("token") token: String
    ): Quote

    @GET("index/constituents")
    suspend fun getTickers(
            @Query("symbol") symbol: String,
            @Query("token") token: String

    ) : CompanyTickers

    @GET("stock/profile2")
    suspend fun getName(
            @Query("symbol") symbol: String,
            @Query("token") token: String
    ) : CompanyName
}

object RetrofitModule {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    val companyApi: CompanyApi = retrofit.create()
}

