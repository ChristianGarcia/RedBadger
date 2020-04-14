package uk.co.origamibits.redbadger.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uk.co.origamibits.redbadger.ui.RobotTrafficViewModel
import uk.co.origamibits.redbadger.ui.RobotsActivity
import javax.inject.Scope


@Scope
annotation class EditionScope

@ExperimentalStdlibApi
@ExperimentalCoroutinesApi
@Module
interface RobotsModule {

    @EditionScope
    @ContributesAndroidInjector
    fun injector(): RobotsActivity

    @Binds
    @IntoMap
    @ViewModelKey(RobotTrafficViewModel::class)
    fun editionViewModel(viewModel: RobotTrafficViewModel): ViewModel

}
