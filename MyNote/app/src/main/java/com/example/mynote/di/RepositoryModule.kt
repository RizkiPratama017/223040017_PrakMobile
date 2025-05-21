package com.example.mynote.di

import com.example.mynote.dao.NoteDao
import com.example.mynote.networks.NoteApi
import com.example.mynote.repositories.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideNoteRepository(
        api: NoteApi,
        dao: NoteDao
    ): NoteRepository {
        return NoteRepository(api, dao)
    }
}