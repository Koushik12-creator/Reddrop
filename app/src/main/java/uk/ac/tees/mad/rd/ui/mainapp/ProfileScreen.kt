package uk.ac.tees.mad.rd.ui.mainapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import uk.ac.tees.mad.rd.R
import uk.ac.tees.mad.rd.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.rd.ui.theme.poppinsFamily


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    authViewmodel: AuthViewmodel
){

    val currentUser by authViewmodel.currentUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5B3AF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Spacer(modifier = Modifier.weight(4f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            GlideImage(
                modifier = Modifier
                    .height(150.dp)
                    .aspectRatio(1f, matchHeightConstraintsFirst = true)
                    .border(
                        1.dp,
                        Color.DarkGray,
                        shape = CircleShape
                    )
                    .padding(1.dp)
                    .clip(CircleShape),
                model = currentUser?.profilePicture,
                contentDescription = "Profile Picture",
                failure = placeholder(R.drawable.img)
            )

            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = CardDefaults.elevatedCardElevation(10.dp)
                ){
                    currentUser?.name?.let {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            text = it,
                            fontSize = 18.sp,
                            fontFamily = poppinsFamily
                        )
                    }
                    currentUser?.let {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            text = it.email,
                            fontSize = 18.sp,
                            fontFamily = poppinsFamily
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            elevation = CardDefaults.elevatedCardElevation(10.dp)
        ){
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Blood Group:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFamily
            )
            currentUser?.let {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    text = it.bloodGroup,
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            elevation = CardDefaults.elevatedCardElevation(10.dp)
        ){
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Contact:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFamily
            )
            currentUser?.let {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    text = it.phoneNumber,
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            elevation = CardDefaults.elevatedCardElevation(10.dp)
        ){
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Diabetes?:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFamily
            )

            if (currentUser?.healthDetails?.diabetes == true){
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    text = "Yes",
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily
                )
            }else{
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    text = "No",
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily
                )
            }

        }

        Spacer(modifier = Modifier.weight(1f))

        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            elevation = CardDefaults.elevatedCardElevation(10.dp)
        ){
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Heart Disease?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFamily
            )
            if (currentUser?.healthDetails?.heartDisease == true){
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    text = "Yes",
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily
                )
            }else{
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    text = "No",
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.Black),
            onClick = {
                authViewmodel.logout()
                navController.navigate("auth_graph"){
                    popUpTo(navController.graph.startDestinationId){
                        inclusive = true
                    }
                    launchSingleTop=true
                }
            }
        ) {
            Text(
                text = "LogOut!",
                fontSize = 16.sp,
                fontFamily = poppinsFamily
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.Black),
            onClick = {
                navController.navigate("edit_profile_screen")
            }
        ) {
            Text(
                text = "Edit Profile!",
                fontSize = 16.sp,
                fontFamily = poppinsFamily
            )
        }

        Spacer(modifier = Modifier.weight(10f))
    }
}