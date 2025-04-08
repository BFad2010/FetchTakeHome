package com.example.fetchhomeassignment.di

import android.content.Context
import androidx.room.Room
import com.example.fetchhomeassignment.data.db.FetchItemsDatabase
import com.example.fetchhomeassignment.data.service.FETCH_JSON_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFetchJsonUrl() = FETCH_JSON_BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideWordleDb(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(
        app,
        FetchItemsDatabase::class.java,
        "items_database"
    ).build()

    @Singleton
    @Provides
    fun provideItemDao(db: FetchItemsDatabase) = db.FetchItemsDao()
}