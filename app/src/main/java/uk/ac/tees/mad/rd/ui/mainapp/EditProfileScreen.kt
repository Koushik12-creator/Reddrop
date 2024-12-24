package uk.ac.tees.mad.rd.ui.mainapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import uk.ac.tees.mad.rd.R
import uk.ac.tees.mad.rd.authentication.model.HealthDetails
import uk.ac.tees.mad.rd.authentication.model.UserInfo
import uk.ac.tees.mad.rd.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.rd.ui.theme.poppinsFamily

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EditProfileScreen(
    navController: NavHostController,
    authViewmodel: AuthViewmodel
){

    val bloodGroups = listOf(
        "A Positive(A+)",
        "A Negative(A-)",
        "B positive (B+)",
        "B negative (B-)",
        "AB positive (AB+)",
        "AB negative (AB-)",
        "O positive (O+)",
        "O negative (O-)"
    )

    val radioOption = listOf("Yes", "No")

    val currentUser by authViewmodel.currentUser.collectAsState()
    var updatedName by remember { mutableStateOf(currentUser?.name ?: "") }
    var updatedPhoneNumber by remember { mutableStateOf(currentUser?.phoneNumber?:"")}
    var updatedBloodGroup by remember { mutableStateOf(currentUser?.bloodGroup?:"") }
    var showBloodGroup by remember { mutableStateOf(false) }
    var anyHealthHistory by remember { mutableStateOf(currentUser?.healthDetails?.anyHistory?: false) }
    var anyHeartDisease by remember { mutableStateOf(currentUser?.healthDetails?.heartDisease?: false) }
    var diabeticHistory by remember { mutableStateOf(currentUser?.healthDetails?.diabetes?: false) }
    var updatedProfilePictureUrl by remember { mutableStateOf(currentUser?.profilePicture?: "") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5B3AF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Spacer(modifier = Modifier.weight(4f))

        GlideImage(
            modifier = Modifier
                .height(200.dp)
                .aspectRatio(1f, matchHeightConstraintsFirst = true)
                .border(
                    1.dp,
                    Color.DarkGray,
                    shape = CircleShape
                )
                .padding(1.dp)
                .clip(CircleShape),
            model = "",
            contentDescription = "Profile Picture",
            failure = placeholder(R.drawable.img)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(1.dp, color = Color.Black),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color.Transparent
            ),
            onClick = {

            }
        ) {
            Text(
                text = "Update Profile Picture!!",
                fontSize = 17.sp,
                fontFamily = poppinsFamily
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            text = "Name: ",
            fontFamily = poppinsFamily,
            fontSize = 16.sp
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            value = updatedName,
            onValueChange = {
                updatedName = it
            },
            shape = RoundedCornerShape(15.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            text = "Contact: ",
            fontFamily = poppinsFamily,
            fontSize = 16.sp
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            value = updatedPhoneNumber,
            onValueChange = {
                updatedPhoneNumber = it
            },
            shape = RoundedCornerShape(15.dp)
            ,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(

            modifier = Modifier
                .fillMaxWidth(0.88f)
                .padding(horizontal = 20.dp)
        ){
            Row(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .clickable {
                        showBloodGroup = true
                    }
            ){
                Text(
                    text = "Choose the Blood Group ",
                    fontSize = 17.sp,
                    fontFamily = poppinsFamily
                )

                Image(
                    painter = painterResource(R.drawable.round_arrow_drop_down),
                    contentDescription = ""
                )
            }
            Text(
                text = updatedBloodGroup,
                fontSize = 17.sp,
                fontFamily = poppinsFamily
            )

            DropdownMenu(
                modifier = Modifier
                    .background(color = Color(0xFFF5A5AF))
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(15.dp)),
                expanded = showBloodGroup,
                onDismissRequest = {
                    showBloodGroup = false
                }
            ) {
                bloodGroups.forEach { bloodGroup ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = bloodGroup,
                                fontFamily = poppinsFamily,
                                fontSize = 15.sp
                            )
                        },
                        onClick = {
                            showBloodGroup = false
                            updatedBloodGroup = bloodGroup
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Health Details",
            fontFamily = poppinsFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier
                    .padding(horizontal = 7.dp),
                text = "Any medical history",
                fontSize = 15.sp,
                fontFamily = poppinsFamily
            )

            radioOption.forEach{option->
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = option,
                        fontSize = 15.sp,
                        fontFamily = poppinsFamily
                    )
                    RadioButton(
                        selected = false,
                        onClick = {

                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier
                    .padding(horizontal = 7.dp),
                text = "Have any diabetic history:",
                fontSize = 15.sp,
                fontFamily = poppinsFamily
            )

            radioOption.forEach{option->
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = option,
                        fontSize = 15.sp,
                        fontFamily = poppinsFamily
                    )
                    RadioButton(
                        selected = false,
                        onClick = {

                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier
                    .padding(horizontal = 7.dp),
                text = "Have any Heart disease:",
                fontSize = 15.sp,
                fontFamily = poppinsFamily
            )

            radioOption.forEach{option->
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = option,
                        fontSize = 15.sp,
                        fontFamily = poppinsFamily
                    )
                    RadioButton(
                        selected = false,
                        onClick = {

                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {

                val healthDetails = HealthDetails(
                    anyHistory = anyHealthHistory,
                    diabetes = diabeticHistory,
                    heartDisease = anyHeartDisease
                )
                val userInfo = UserInfo(
                    name = updatedName,
                    email = currentUser?.email?:"",
                    phoneNumber = updatedPhoneNumber,
                    bloodGroup = updatedBloodGroup,
                    profilePicture = updatedProfilePictureUrl,
                    healthDetails = healthDetails
                )
                authViewmodel.updateUserInformation(userInfo = userInfo)
                navController.popBackStack()
            }
        ) {
            Text(
                text = "Update Profile!!",
                fontSize = 17.sp,
                fontFamily = poppinsFamily
            )
        }
        Spacer(modifier = Modifier.weight(10f))

    }
}