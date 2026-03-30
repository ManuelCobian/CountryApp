package com.luvsoft.countryapp.di

import androidx.fragment.app.Fragment
import com.luvsoft.core.network.services.CountriesService
import com.luvsoft.core.network.services.imp.CountriesServiceImp
import com.luvsoft.countryapp.ui.adapters.CountryAdapter
import com.luvsoft.countryapp.ui.adapters.FavoriteAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class HitModule {
    @Singleton
    @Binds
    abstract fun bindHitService(imp: CountriesServiceImp): CountriesService
}

@Module
@InstallIn(FragmentComponent::class)
object CountryModule {
    @Provides
    fun provideCallback(fragment: Fragment) =
        fragment as CountryAdapter.CallbackClick
}

@Module
@InstallIn(FragmentComponent::class)
object FavoriteModule {
    @Provides
    fun provideCallback(fragment: Fragment) =
        fragment as FavoriteAdapter.CallbackClick
}