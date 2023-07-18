package skustra.focusflow.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import skustra.focusflow.BuildConfig
import skustra.focusflow.main.debug.MockDataViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mockDataViewModel: MockDataViewModel by viewModels()
            if (BuildConfig.DEBUG) {
                mockDataViewModel.mockSessionArchiveData.fillDatabaseWithMockData(lifecycleScope)
            }
            HomePageScreen()
        }
    }
}
