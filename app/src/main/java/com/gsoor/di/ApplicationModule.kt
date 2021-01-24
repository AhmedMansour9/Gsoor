package com.gsoor.di

import com.gsoor.data.DataCenterImpl
import com.gsoor.data.DataCenterManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideDataCenterManger(dataCenterImpl: DataCenterImpl): DataCenterManager = dataCenterImpl




}