package com.numq.template.core.di

import android.content.Context
import androidx.room.Room
import com.numq.template.BuildConfig
import com.numq.template.core.constants.AppConstants
import com.numq.template.data.AppDatabase
import com.numq.template.data.cards.CardsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    lateinit var database: AppDatabase

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "app-db").build()

    @Provides
    fun provideCardsDao(db: AppDatabase) = db.cardsDao

    @Provides
    @Singleton
    fun provideCardsCache(dataSource: CardsRepository.Cache): CardsRepository = dataSource

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(createClient())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

}