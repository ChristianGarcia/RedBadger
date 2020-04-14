package uk.co.origamibits.redbadger.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uk.co.origamibits.redbadger.R
import uk.co.origamibits.redbadger.databinding.RobotsActivityBinding
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalStdlibApi
class RobotsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(RobotTrafficViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        val binding: RobotsActivityBinding = DataBindingUtil.setContentView(this, R.layout.robots_activity)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

    }

}
