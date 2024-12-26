package uk.ac.tees.mad.rd.ui.mainapp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.rd.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.rd.mainapp.model.RequestModel
import uk.ac.tees.mad.rd.mainapp.viewmodel.MainViewModel
import uk.ac.tees.mad.rd.ui.theme.iconFamily
import uk.ac.tees.mad.rd.ui.theme.metamorphousFamily
import uk.ac.tees.mad.rd.ui.theme.poppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    authViewmodel: AuthViewmodel,
    mainViewModel: MainViewModel,
    navController: NavHostController
){

    val allRequest by mainViewModel.allRequests.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "RedDrop",
                        fontSize = 25.sp,
                        fontFamily = metamorphousFamily
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            authViewmodel.fetchCurrentUser()
                            navController.navigate("profile_screen")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Profile Screen"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF5B5DF)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Color(0xFFF5B5DF),
                shape = CircleShape,
                onClick = {
                    navController.navigate("request_blood_screen")
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Request Blood Screen"
                )
            }
        }
    ){innerpadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .background(color = Color(0xFFF5B3AF)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(top = 15.dp),
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(width = 1.dp, color = Color.Black),
                onClick = {
                    navController.navigate("donation_center_screen")
                }
            ) {
                Text(
                    text = "See the nearest donation Centers!!",
                    fontSize = 17.sp,
                    fontFamily = poppinsFamily
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ){
                items(allRequest){request->
                    BloodRequestDetails(request)
                }
            }
        }
    }
}


@Composable
fun BloodRequestDetails(
    request: RequestModel
){

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(vertical = 10.dp)
    ){
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            request.name?.let {
                Text(
                    text = it,
                    fontSize = 20.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }
            request.requiredBlood?.let {
                Text(
                    text = it,
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily
                )
            }
            Row {
                Text(
                    text = "Contact: ",
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily
                )
                request.contact?.let {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 5.dp),
                        text = it,
                        fontSize = 16.sp,
                        fontFamily = poppinsFamily
                    )
                }
            }
            Button(
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(1.dp, color = Color.Black),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                ),
                onClick = {
                    val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${request.contact}")
                    }
                    context.startActivity(dialIntent)
                }
            ) {
                Text(
                    text = "Contact donor!!",
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily
                )
            }
        }
    }
}
