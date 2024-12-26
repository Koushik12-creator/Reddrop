package uk.ac.tees.mad.rd.ui.mainapp

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
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
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EditProfileScreen(
    navController: NavHostController,
    authViewmodel: AuthViewmodel
){

    val context = LocalContext.current

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
    var showDialog by remember { mutableStateOf(false) }


    LaunchedEffect(currentUser){
        authViewmodel.fetchCurrentUser()
    }


    //Launching Gallery to select the picture
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri? ->
        uri?.let {
            authViewmodel.updateProfileImage(uri)
        }
    }

    // Launcher for taking a picture with the camera
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            authViewmodel.updateProfileImage(bitmapToUri(context, bitmap))
        }
    }

    //Launcher for asking permission to access the camera.
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {isGranted->
            if (isGranted){
                cameraLauncher.launch(null)
            }else{
                Toast.makeText(context, "Camera Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    )

    //Launcher for asking permission to access the gallery.
    val galleryPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {isGranted->
            if (isGranted){
                galleryLauncher.launch("images/*")
            }else{
                Toast.makeText(context, "Gallery Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    )



    if (showDialog){
        ImageSelectionSource(
            onDismiss = { showDialog = false },
            onCameraClick = {
                cameraPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            },
            onGalleryClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    galleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    galleryLauncher.launch("image/*")
                } else {
                    galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        )
    }


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
            model = updatedProfilePictureUrl,
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
                showDialog = true
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
                        selected = (option == "Yes" && anyHealthHistory) || (option == "No" && !anyHealthHistory),
                        onClick = {
                            anyHealthHistory = option == "Yes"
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
                        selected = (option == "Yes" && diabeticHistory) || (option == "No" && !diabeticHistory),
                        onClick = {
                            diabeticHistory = option == "Yes"
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
                        selected = (option == "Yes" && anyHeartDisease) || (option == "No" && !anyHeartDisease),
                        onClick = {
                            anyHeartDisease = option == "Yes"
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


@Composable
fun ImageSelectionSource(
    onDismiss: ()->Unit,
    onCameraClick: ()->Unit,
    onGalleryClick: ()->Unit
){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Choose Image Source")
        },
        text = {
            Column {
                Button(
                    onClick = {
                        onCameraClick()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Take a Photo",
                        fontSize = 13.sp,
                        fontFamily = poppinsFamily
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        onGalleryClick()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Choose from Gallery",
                        fontFamily = poppinsFamily,
                        fontSize = 13.sp
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}



fun bitmapToUri(context: Context, bt: Bitmap): Uri{
    val image = File(context.cacheDir, "${UUID.randomUUID()}.jpg")
    val outStream = FileOutputStream(image)
    bt.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
    outStream.flush()
    outStream.close()
    return Uri.fromFile(image)
}


