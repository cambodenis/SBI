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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sbi.R
import com.example.sbi.ui.theme.Active
import com.example.sbi.ui.theme.BackgroundColor
import com.example.sbi.ui.theme.NonActive
import com.example.sbi.ui.theme.White


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImagesPicker(
    iconlist: List<String>,
    selectedIcon: String?,
    onIconSelected: (String) -> Unit, // New selected Icon
    onDismissRequest: () -> Unit // Cancel Request

) {
    val vertScrollState = rememberScrollState()
    val userIconPicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(), onResult = { uri -> onIconSelected(uri.toString()) })

    Column(
        modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight().background(BackgroundColor).padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(
                onClick = {
                    userIconPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = stringResource(R.string.add_your_icon), style = TextStyle(
                    color = White, fontSize = 15.sp
                )
                )
            }
            OutlinedButton(onClick = {
                onDismissRequest()
            }, modifier = Modifier.padding(10.dp))
            {
                Text(text = stringResource(R.string.Save), style = TextStyle(
                    color = White, fontSize = 15.sp
                ))
            }
        }
        FlowRow(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                .verticalScroll(vertScrollState)
                .fillMaxSize(),
            maxItemsInEachRow = 3,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            iconlist.distinct().forEach { iconItem ->
                IconItem(
                    selected = iconItem == selectedIcon,
                    icon = iconItem,
                    onClick = { onIconSelected(iconItem) }
                )

            }
        }
    }

}

@Composable
fun IconItem(
    selected: Boolean,
    icon: String,
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .size(150.dp)
            .padding(10.dp)
            .border(
                width = 1.dp,
                color = White,
                shape = RoundedCornerShape(10.dp)
            )

            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        var color = NonActive
        if(selected) color = Active
        AsyncImage(icon, color, 0.8f)

    }

}