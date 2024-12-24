package uk.ac.tees.mad.rd.ui.mainapp

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
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.rd.R
import uk.ac.tees.mad.rd.ui.theme.poppinsFamily


@Composable
fun RequestBloodScreen(){

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

    var name by remember { mutableStateOf("") }
    var bloodGroup by remember { mutableStateOf("") }
    var showBloodGroup by remember { mutableStateOf(false) }

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
            text = "Name of Donor",
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
                text = "updatedBloodGroup",
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
                bloodGroups.forEach { bg ->
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
            value = name,
            onValueChange = {
                name = it
            },
            shape = RoundedCornerShape(15.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            shape = RoundedCornerShape(15.dp),
            onClick = {}
        ) {
            Text(
                text = "Upload a report",
                fontSize = 16.sp,
                fontFamily = poppinsFamily
            )
        }

        Spacer(modifier = Modifier.weight(10f))
    }
}