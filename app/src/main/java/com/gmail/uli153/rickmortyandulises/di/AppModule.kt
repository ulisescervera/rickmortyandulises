package com.gmail.uli153.rickmortyandulises.di

import android.content.Context
import com.gmail.uli153.rickmortyandulises.BuildConfig
import com.gmail.uli153.rickmortyandulises.data.services.ApiService
import com.gmail.uli153.rickmortyandulises.data.services.GraphQLService
import com.gmail.uli153.rickmortyandulises.data.RMUDatabase
import com.gmail.uli153.rickmortyandulises.data.datasource.RMULocalDataSourceImpl
import com.gmail.uli153.rickmortyandulises.data.datasource.RMURemoteDataSourceImp
import com.gmail.uli153.rickmortyandulises.domain.RMURepository
import com.gmail.uli153.rickmortyandulises.domain.RMURepositoryImpl
import com.gmail.uli153.rickmortyandulises.domain.usecases.CharacterUseCases
import com.gmail.uli153.rickmortyandulises.domain.usecases.EpisodeUseCases
import com.gmail.uli153.rickmortyandulises.domain.usecases.GetAllCharacters
import com.gmail.uli153.rickmortyandulises.domain.usecases.GetCharacterById
import com.gmail.uli153.rickmortyandulises.domain.usecases.GetEpisodesByIds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
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
        val logger = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun graphqlServiceProvider(): GraphQLService {
        val logger = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.GRAPH_QL_BASE_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GraphQLService::class.java)
    }

    @Provides
    @Singleton
    fun repositoryProvider(database: RMUDatabase, api: ApiService, graphQLService: GraphQLService): RMURepository {
        return RMURepositoryImpl(RMULocalDataSourceImpl(database), RMURemoteDataSourceImp(api, graphQLService))
    }

    @Provides
    @Singleton
    fun characterUseCasesProvider(repository: RMURepository): CharacterUseCases {
        return CharacterUseCases(
            getAllCharacters = GetAllCharacters(repository),
            getCharacterById = GetCharacterById(repository)
        )
    }

    @Provides
    @Singleton
    fun episodeUseCasesProvider(repository: RMURepository): EpisodeUseCases {
        return EpisodeUseCases(
            GetEpisodesByIds(repository)
        )
    }
}