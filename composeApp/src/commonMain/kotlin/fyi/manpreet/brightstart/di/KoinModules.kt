package fyi.manpreet.brightstart.di

import fyi.manpreet.brightstart.data.datasource.AlarmLocalDataSource
import fyi.manpreet.brightstart.data.datasource.AlarmLocalDataSourceImpl
import fyi.manpreet.brightstart.data.repository.AlarmRepository
import fyi.manpreet.brightstart.data.repository.AlarmRepositoryImpl
import fyi.manpreet.brightstart.ui.addalarm.AddAlarmViewModel
import fyi.manpreet.brightstart.ui.home.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            provideLocalDataSourceModule,
            provideRepositoryModule,
            provideViewModelModule,
            provideDatabaseModule(),
            provideRingtoneModule(),
            provideAlarmModule(),
        )
    }

val provideLocalDataSourceModule = module {
    singleOf(::AlarmLocalDataSourceImpl).bind(AlarmLocalDataSource::class)
}

val provideRepositoryModule = module {
    singleOf(::AlarmRepositoryImpl).bind(AlarmRepository::class)
}

val provideViewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::AddAlarmViewModel)
}