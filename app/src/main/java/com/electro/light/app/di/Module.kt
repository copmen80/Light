package com.electro.light.app.di

import android.app.Application
import androidx.room.Room
import com.electro.light.app.LightDataBase
import com.electro.light.explore.data.LightRepository
import com.electro.light.explore.data.local.LocationDAO
import com.electro.light.explore.data.remote.LightService
import com.electro.light.explore.ui.ExploreViewModel
import com.electro.light.menu.ui.MenuViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { ExploreViewModel(get()) }
    viewModel { MenuViewModel() }
}


val apiModule = module {
    fun provideUserApi(retrofit: Retrofit): LightService {
        return retrofit.create(LightService::class.java)
    }

    single { provideUserApi(get()) }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }


    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }

}


val databaseModule = module {

    fun provideDataBase(application: Application): LightDataBase {
        return Room.databaseBuilder(
            application,
            LightDataBase::class.java, "light-database"
        ).build()
    }

    fun provideLightDAO(lightDataBase: LightDataBase): LocationDAO {
        return requireNotNull(lightDataBase.getLocationDao())
    }

    single { provideDataBase(androidApplication()) }
    single { provideLightDAO(get()) }
}

val repositoryModule = module {
    fun provideLightRepository(
        lightService: LightService,
        locationDAO: LocationDAO
    ): LightRepository {
        return LightRepository(lightService, locationDAO)
    }

    single { provideLightRepository(get(), get()) }
}