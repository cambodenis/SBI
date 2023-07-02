package com.example.sbi.utils

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sbi.R
import com.example.sbi.ui.theme.BackgroundColor
import com.example.sbi.ui.theme.White


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImagePickUp(
    iconlist: List<String>,
    onIconSelected: (String) -> Unit, // New selected Icon
    onDismissRequest: () -> Unit // Cancel Request
) {
    val context = LocalContext.current
    val vertScrollState = rememberScrollState()
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                onIconSelected(uri.toString())
            })
    Surface(
        modifier = Modifier
            .fillMaxSize()
           // .width(WindowWidth() / 2).height(MainHeight())
            // .padding(20.dp)
            .background(BackgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(BackgroundColor).padding(20.dp)

        ) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = stringResource(R.string.add_your_icon), color = White)
                }
                OutlinedButton(onClick = {
                    onDismissRequest()
                }, modifier = Modifier.padding(10.dp)) {
                    Text(text = stringResource(R.string.Save),color = White)
                }
            }
            FlowRow(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                    .verticalScroll(vertScrollState)
                    .fillMaxSize(),
                maxItemsInEachRow = 3,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                iconlist.forEach { icon ->
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .padding(10.dp)
                            .border(
                                width = 1.dp,
                                color = White,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { onIconSelected(icon) },
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(icon, White, 1f)
                    }

                }
            }
        }


    }
}
