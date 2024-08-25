package com.khedr.firebaselogin.Di

import com.khedr.firebaselogin.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
     fun provideRetrofit(): Retrofit {
         val logging = HttpLoggingInterceptor().apply {
             level = HttpLoggingInterceptor.Level.BODY // Log request and response body
         }
         val client = OkHttpClient.Builder()
             .addInterceptor(logging)
             .build()

         val retrofit = Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
             .addConverterFactory(GsonConverterFactory.create()).client(client).build()
         return retrofit
     }
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService{
        val apiService = retrofit.create(ApiService::class.java)
        return apiService
    }
}