package uk.ac.tees.mad.rd.ui.mainapp

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.rd.R
import uk.ac.tees.mad.rd.mainapp.model.RequestModel
import uk.ac.tees.mad.rd.mainapp.viewmodel.MainViewModel
import uk.ac.tees.mad.rd.ui.theme.poppinsFamily


@Composable
fun RequestBloodScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
){

    val bloodGroups = listOf(
        "Select Blood Group",
        "A Positive(A+)",
        "A Negative(A-)",
        "B positive (B+)",
        "B negative (B-)",
        "AB positive (AB+)",
        "AB negative (AB-)",
        "O positive (O+)",
        "O negative (O-)"
    )

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var bloodGroup by remember { mutableStateOf(bloodGroups[0]) }
    var showBloodGroup by remember { mutableStateOf(false) }
    var contact by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5B3AF)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Spacer(modifier = Modifier.weight(4f))

        Text(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            text = "Request the blood",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            fontFamily = poppinsFamily,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(3f))

        Text(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            text = "Name of the Patient",
            fontFamily = poppinsFamily,
            fontSize = 16.sp
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.85f),
            value = name,
            onValueChange = {
                name = it
            },
            shape = RoundedCornerShape(15.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Column {
            Row(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth(0.8f)
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
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                text = bloodGroup,
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
                bloodGroups.forEachIndexed {index, bg ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = bg,
                                fontFamily = poppinsFamily,
                                fontSize = 15.sp
                            )
                        },
                        onClick = {
                            showBloodGroup = false
                            bloodGroup = bg
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            text = "Contact Information",
            fontFamily = poppinsFamily,
            fontSize = 16.sp
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.85f),
            value = contact,
            onValueChange = {
                contact = it
            },
            shape = RoundedCornerShape(15.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            Button(
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                ),
                onClick = {}
            ) {
                Text(
                    text = "Upload a report",
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily
                )
            }

            Text(
                text = "No file selected",
                fontSize = 17.sp,
                fontFamily = poppinsFamily
            )
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Remove file"
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color(0xFFF5B3AF)
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(10.dp),
            border = BorderStroke(width = 1.dp, color = Color.Black),
            onClick = {
                if (name.isNotEmpty() && bloodGroup!=bloodGroups[0] && contact.isNotEmpty()){
                    val requestBlood = RequestModel(
                        name = name,
                        requiredBlood = bloodGroup,
                        contact = contact,
                        reportUrl = ""
                    )

                    mainViewModel.addRequests(requestBlood)

                    navController.popBackStack()
                }else{
                    Toast.makeText(context, "All fields are manadatory!!", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(
                text = "Raise the Request!!",
                fontSize = 17.sp,
                fontFamily = poppinsFamily
            )
        }

        Spacer(modifier = Modifier.weight(10f))
    }
}