package com.gmail.uli153.rickmortyandulises.di

import com.gmail.uli153.rickmortyandulises.domain.usecases.CharacterUseCases
import com.gmail.uli153.rickmortyandulises.ui.viewmodels.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ActivityModule {

    @Provides
    @ViewModelScoped
    fun mainViewModel(characterUseCases: CharacterUseCases): MainViewModel {
        return MainViewModel(characterUseCases)
    }

}