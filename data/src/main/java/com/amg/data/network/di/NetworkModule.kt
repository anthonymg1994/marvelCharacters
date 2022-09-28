package com.amg.data.network.di

import com.amg.config.Constants
import com.amg.data.network.api.MarvelApiService
import com.amg.data.repository.GetCharactersRepositoryImpl
import com.amg.data.repository.GetComicsRepositoryImpl
import com.amg.domain.repository.CharactersRepository
import com.amg.domain.repository.ComicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    private const val READ_TIMEOUT = 30
    private const val WRITE_TIMEOUT = 30
    private const val CONNECTION_TIMEOUT = 10

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: Interceptor,
    ): OkHttpClient {

        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(headerInterceptor)
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor {
            val url = it.request().url().newBuilder()
                .addQueryParameter("apikey", Constants.API_KEY)
                .addQueryParameter("ts", Constants.timeStamp)
                .addQueryParameter("hash",  Constants.hash())
                .build()
            val request = it.request().newBuilder()
                .url(url)
                .build()
            it.proceed(request)
        }
    }

    @Provides
    fun provideMarvelApiService(retrofit: Retrofit) : MarvelApiService = retrofit.create(MarvelApiService::class.java)

    @Provides
    fun provideGetCharactersRepository(apiService: MarvelApiService): CharactersRepository {
        return GetCharactersRepositoryImpl(apiService = apiService)
    }

    @Provides
    fun provideGetComicRepository(apiService: MarvelApiService): ComicRepository {
        return GetComicsRepositoryImpl(apiService = apiService)
    }


}