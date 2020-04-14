package uk.co.origamibits.redbadger.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Component
import dagger.MapKey
import dagger.android.AndroidInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uk.co.origamibits.redbadger.MarsRobotTrafficApplication
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@ExperimentalCoroutinesApi
@ExperimentalStdlibApi
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        RobotsModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: MarsRobotTrafficApplication)

}
