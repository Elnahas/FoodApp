package elnahas.foodapp.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import elnahas.foodapp.data.Constant.BAS_URL
import elnahas.foodapp.data.remote.FoodApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FoodDataModule {


//    @Provides
//    @Singleton
//    fun provideOkHTTPClient(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .connectTimeout(20, TimeUnit.SECONDS)
//            .readTimeout(20, TimeUnit.SECONDS).build()
//    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder().baseUrl(BAS_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): FoodApiService {
        return retrofit.create(FoodApiService::class.java)
    }

}