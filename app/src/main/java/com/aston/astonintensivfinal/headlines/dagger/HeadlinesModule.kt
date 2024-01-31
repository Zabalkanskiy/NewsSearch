package com.aston.astonintensivfinal.headlines.dagger

import com.aston.astonintensivfinal.headlines.data.HeadlinesRepositoryImpl
import com.aston.astonintensivfinal.headlines.domain.generalUseCase.FindHeadlinesInDataBaseUse
import com.aston.astonintensivfinal.headlines.domain.generalUseCase.GetHeadLinesUseCase
import com.aston.astonintensivfinal.headlines.domain.generalUseCase.LoadHeadlinesByDateUseCase
import com.aston.astonintensivfinal.headlines.domain.generalUseCase.LoadHeadlinesFromDataBaseUseCase
import com.aston.astonintensivfinal.headlines.domain.generalUseCase.SaveHeadlinesDataInDataBaseUseCase
import com.aston.astonintensivfinal.headlines.presentation.presenter.HeadlinesPresenter
import dagger.Module
import dagger.Provides

@Module
class HeadlinesModule {

    @Provides
    fun getHeadlinesPresenter(): HeadlinesPresenter {
        return HeadlinesPresenter(
            headlinesUseCase = GetHeadLinesUseCase(repositoryImpl = HeadlinesRepositoryImpl()),
            findHeadlinesInDataBaseUse = FindHeadlinesInDataBaseUse(repositoryImpl = HeadlinesRepositoryImpl()),
            saveHeadlinesDataInDataBaseUseCase = SaveHeadlinesDataInDataBaseUseCase(repositoryImpl = HeadlinesRepositoryImpl()),
            loadHeadlinesFromDataBaseUseCase = LoadHeadlinesFromDataBaseUseCase(repositoryImpl = HeadlinesRepositoryImpl()),
            loadHeadlinesByDateUseCase = LoadHeadlinesByDateUseCase(repositoryImpl = HeadlinesRepositoryImpl())
        )

    }

    @Provides
    fun getHeadKinesUseCase(): GetHeadLinesUseCase =
        GetHeadLinesUseCase(repositoryImpl = HeadlinesRepositoryImpl())

    @Provides
    fun getHeadlinesRepositoryImpl(): HeadlinesRepositoryImpl = HeadlinesRepositoryImpl()

    @Provides
    fun providesFindHeadlinesUseCase(): FindHeadlinesInDataBaseUse =
        FindHeadlinesInDataBaseUse(repositoryImpl = HeadlinesRepositoryImpl())

    @Provides
    fun providesLoadHeadlinesFromDBUseCase(): LoadHeadlinesFromDataBaseUseCase =
        LoadHeadlinesFromDataBaseUseCase(repositoryImpl = HeadlinesRepositoryImpl())

    @Provides
    fun providesSaveHeadlinesInDBUseCase(): SaveHeadlinesDataInDataBaseUseCase =
        SaveHeadlinesDataInDataBaseUseCase(repositoryImpl = HeadlinesRepositoryImpl())

    fun providesLoadHeadlinesByDateUseCase(): LoadHeadlinesByDateUseCase =
        LoadHeadlinesByDateUseCase(repositoryImpl = HeadlinesRepositoryImpl())

}