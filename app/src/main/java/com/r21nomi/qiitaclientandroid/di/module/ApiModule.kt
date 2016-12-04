package com.r21nomi.qiitaclientandroid.di.module

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.model.api.ApiClient
import com.r21nomi.qiitaclientandroid.model.api.SigningInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Ryota Niinomi on 2016/11/23.
 */
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(SigningInterceptor(context))
                .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.HEADERS
                })
                .addNetworkInterceptor(StethoInterceptor())  // TODO: Use only for debug
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(context: Context, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.getString(R.string.api_endpoint))
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }
}