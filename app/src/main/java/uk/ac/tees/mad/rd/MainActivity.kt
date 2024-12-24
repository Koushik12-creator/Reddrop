package uk.ac.tees.mad.rd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.tees.mad.rd.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.rd.mainapp.viewmodel.MainViewModel
import uk.ac.tees.mad.rd.navigation.CentralNavigation
import uk.ac.tees.mad.rd.ui.theme.RedDropTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewmodel by viewModels<AuthViewmodel>()
    private val mainViewmodel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            RedDropTheme {
                CentralNavigation(
                    navController = navController,
                    authViewmodel = authViewmodel,
                    mainViewModel = mainViewmodel
                )
            }
        }
    }
}
