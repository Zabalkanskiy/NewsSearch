package com.aston.astonintensivfinal.headlines.dagger

import com.aston.astonintensivfinal.headlines.data.HeadlinesRepositoryImpl
import com.aston.astonintensivfinal.headlines.domain.GetHeadLinesUseCase
import com.aston.astonintensivfinal.headlines.presentation.presenter.HeadlinesPresenter
import dagger.Module
import dagger.Provides
@Module
class HeadlinesModule {

    @Provides
    fun getHeadlinesPresenter():HeadlinesPresenter{
        return HeadlinesPresenter(headlinesUseCase = GetHeadLinesUseCase(repositoryImpl = HeadlinesRepositoryImpl()))

    }

    @Provides
    fun getHeadKinesUseCase(): GetHeadLinesUseCase = GetHeadLinesUseCase(repositoryImpl = HeadlinesRepositoryImpl())
    @Provides
    fun getHeadlinesRepositoryImpl(): HeadlinesRepositoryImpl = HeadlinesRepositoryImpl()


}