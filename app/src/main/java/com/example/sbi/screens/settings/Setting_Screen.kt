package com.example.sbi.screens.settings


import androidx.compose.runtime.Composable



@Composable
fun Settings_Screen() {



    // A surface container using the 'background' color from the theme
    /*
        PermissionsState {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                   // .fillMaxWidth().height(MainHeight())

            ) {
                val openDialog = remember { mutableStateOf(false) }

                Button(onClick = {
                    openDialog.value = true
                }) {
                    Text("Click me")
                }

                if (openDialog.value) {

                    AlertDialog(
                        modifier = Modifier.fillMaxSize(),
                        onDismissRequest = {
                            // Dismiss the dialog when the user clicks outside the dialog or on the back
                            // button. If you want to disable that functionality, simply use an empty
                            // onCloseRequest.
                            openDialog.value = true
                        },


                        text = {
                            Text("Here is a text ")
                        },


                        confirmButton = {
                            Button(

                                onClick = {
                                    // mDeviceViewModel.addDevice(deviceItem)
                                    openDialog.value = false
                                }) {
                                Text("This is the Confirm Button")
                            }
                        },
                        dismissButton = {
                            Button(

                                onClick = {
                                    openDialog.value = false
                                }) {
                                Text("This is the dismiss Button")
                            }
                        }
                    )
                }


                Text(
                    text = "Home_Settings_Screen", textAlign = TextAlign.Center,
                    color = White, fontSize = 30.sp,
                )
            }
        }

     */

}
