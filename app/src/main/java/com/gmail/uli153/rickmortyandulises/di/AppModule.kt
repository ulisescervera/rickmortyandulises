package com.gmail.uli153.rickmortyandulises.di

import android.content.Context
import com.gmail.uli153.rickmortyandulises.data.ApiService
import com.gmail.uli153.rickmortyandulises.data.RMUDatabase
import com.gmail.uli153.rickmortyandulises.data.datasource.RMULocalDataSourceImpl
import com.gmail.uli153.rickmortyandulises.data.datasource.RMURemoteDataSourceImp
import com.gmail.uli153.rickmortyandulises.domain.RMURepository
import com.gmail.uli153.rickmortyandulises.domain.RMURepositoryImpl
import com.gmail.uli153.rickmortyandulises.domain.usecases.CharacterUseCases
import com.gmail.uli153.rickmortyandulises.domain.usecases.GetAllCharacters
import com.gmail.uli153.rickmortyandulises.domain.usecases.GetCharacterById
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun databaseProvider(@ApplicationContext context: Context): RMUDatabase {
        return RMUDatabase.buildDatabase(context)
    }

    @Provides
    @Singleton
    fun apiServiceProvider(): ApiService {
        val apiUrl = "https://rickandmortyapi.com/api/"
        val logger = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun repositoryProvider(database: RMUDatabase, api: ApiService): RMURepository {
        return RMURepositoryImpl(RMULocalDataSourceImpl(database), RMURemoteDataSourceImp(database, api))
    }

    @Provides
    @Singleton
    fun characterUseCasesProvider(repository: RMURepository): CharacterUseCases {
        return CharacterUseCases(
            getAllCharacters = GetAllCharacters(repository),
            getCharacterById = GetCharacterById(repository)
        )
    }
}