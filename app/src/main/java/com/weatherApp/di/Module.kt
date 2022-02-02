package com.weatherApp.di

import android.app.Application
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.weatherApp.constants.BASE_URL
import com.weatherApp.data.api.WeatherApi
import com.weatherApp.data.repository.WeatherRepository
import com.weatherApp.viewmodels.WeatherViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { WeatherViewModel(weatherRepository = get()) }
}

val apiModule = module {
    fun provideTheMovieApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    single { provideTheMovieApi(get()) }
}

val repositoryModule = module {
    fun providePopularRepository(api: WeatherApi): WeatherRepository {
        return WeatherRepository(api)
    }

    single { providePopularRepository(get()) }
}

    val networkModule = module {
        fun provideCache(application: Application): Cache {
            val cacheSize = 10 * 1024 * 1024
            return Cache(application.cacheDir, cacheSize.toLong())
        }

        fun provideHttpClient(cache: Cache): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(logging)
                .cache(cache)

            return okHttpClientBuilder.build()
        }

        fun provideGson(): Gson {
            return Gson()
        }



        fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(factory))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()
        }

        factory { provideCache(androidApplication()) }
        factory { provideHttpClient(get()) }
        factory { provideGson() }
        factory { provideRetrofit(get(), get()) }


}