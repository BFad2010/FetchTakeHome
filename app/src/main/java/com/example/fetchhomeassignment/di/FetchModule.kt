package com.example.fetchhomeassignment.di

import android.content.Context
import com.example.fetchhomeassignment.data.service.FetchJsonApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FetchModule {

    @Provides
    @Singleton
    fun provideFetchJsonApi(retrofit: Retrofit) = retrofit.create(FetchJsonApi::class.java)

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().disableHtmlEscaping().create()

    @Provides
    fun provideContext(
        @ApplicationContext context: Context,
    ): Context {
        return context
    }

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}