package com.electro.light.app.di

import android.app.Application
import androidx.room.Room
import com.electro.light.location.common.data.database.LightDataBase
import com.electro.light.location.common.data.LocationRepository
import com.electro.light.location.common.data.LocationDao
import com.electro.light.location.createlocation.choosegroup.ui.GroupsViewModel
import com.electro.light.location.createlocation.fillnameandicon.ui.FillNameAndIconViewModel
import com.electro.light.location.detailed.ui.DetailedViewModel
import com.electro.light.location.common.data.remote.LightApi
import com.electro.light.location.explore.ui.ExploreViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel { ExploreViewModel(get()) }
    viewModel { FillNameAndIconViewModel(get()) }
    viewModel { GroupsViewModel() }
    viewModel { DetailedViewModel(get()) }
}

val networkModule = module {

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://53662723-94e2-4979-9fd1-90e7825e197e.mock.pstmn.io").client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder().addInterceptor(interceptor)
            .connectTimeout(5, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES).build()
    }

    factory { provideOkHttpClient() }
    single { provideRetrofit(get()) }
}

val databaseModule = module {

    fun provideDataBase(application: Application): LightDataBase {
        return Room.databaseBuilder(
            application,
            LightDataBase::class.java, "light-database"
        ).build()
    }

    fun provideLocationDAO(lightDataBase: LightDataBase): LocationDao {
        return requireNotNull(lightDataBase.getLocationDao())
    }

    single { provideDataBase(androidApplication()) }
    single { provideLocationDAO(get()) }
}

val repositoryModule = module {

    fun provideLightApi(retrofit: Retrofit): LightApi =
        retrofit.create(LightApi::class.java)

    fun provideLocationRepository(
        lightApi: LightApi,
        locationDAO: LocationDao
    ): LocationRepository {
        return LocationRepository(lightApi, locationDAO)
    }

    factory { provideLightApi(get()) }
    single { provideLocationRepository(get(), get()) }

}
