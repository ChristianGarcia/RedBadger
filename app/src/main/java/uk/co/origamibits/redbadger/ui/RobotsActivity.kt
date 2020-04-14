package uk.co.origamibits.redbadger.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uk.co.origamibits.redbadger.R
import uk.co.origamibits.redbadger.business.AsStringMoveRobotsUseCase
import uk.co.origamibits.redbadger.databinding.RobotsActivityBinding
import uk.co.origamibits.redbadger.reader.EarthStationReader
import uk.co.origamibits.redbadger.reader.StartingPointParser
import uk.co.origamibits.redbadger.reader.WorldGridParser
import uk.co.origamibits.redbadger.robot.RobotHiveMind
import uk.co.origamibits.redbadger.traffic.MarsTrafficDispatcher

@ExperimentalStdlibApi
class RobotsActivity : AppCompatActivity() {
    private val viewModel: RobotTrafficViewModel by lazy {
        ViewModelProvider(this, viewModelFactory {
            RobotTrafficViewModel(
                AsStringMoveRobotsUseCase(
                    MarsTrafficDispatcher(
                        EarthStationReader(
                            WorldGridParser(), StartingPointParser()
                        ),
                        RobotHiveMind()
                    )
                )
            )
        }).get(RobotTrafficViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: RobotsActivityBinding = DataBindingUtil.setContentView(this, R.layout.robots_activity)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

    }

    private inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }
}
