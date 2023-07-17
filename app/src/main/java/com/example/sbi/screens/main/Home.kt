package com.example.sbi.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.sbi.ui.theme.White
import com.example.sbi.utils.AsyncImage


@Composable
fun Home_Screen(navController: NavHostController) {
    BoxWithConstraints() {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxSize()
        ) {
            //  LeftFrame()
            //Central

            AsyncImage("file:///android_asset/weatherIcon/sun.svg", White, 1f)

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    //  .width((WindowWidth() / 100) * 76)
                    .fillMaxHeight()


            ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                }
                //          CenterPanel()
            }
            //Right
//        RightFrame()
        }


//Main Frame
    }
}

/*
@Composable
private fun CenterPanel() {

    data class CardWidget(
        val type: String,
        var width: Dp,
        val imagevector: Int,
        val color: Color,
        val title: String,
        val data: Double,
        val unit: String,

        )

    val cardWidgetlist = listOf(
        CardWidget(
            "vertical_right",
            0.dp,
            R.drawable.ic_launcher_foreground,
            Red,
            "MPPT",
            220.0,
            "V",

            ),
        CardWidget(
            "vertical_right",
            0.dp,
            R.drawable.ic_launcher_foreground,
            Red,
            "AC Ground",
            238.0,
            "V",

            ),
        CardWidget(
            "vertical_right",
            0.dp,
            R.drawable.ic_launcher_foreground,
            Red,
            "Solar",
            85.5,
            "W",

            ),
        CardWidget(
            "vertical_right",
            0.dp,
            R.drawable.ic_launcher_foreground,
            Red,
            "Generator",
            215.0,
            "V",

            ),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .drawBehind {
                drawLine(
                    brush = Brush.horizontalGradient(colors = BorderColorTwoWay),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = BorderSize.toPx(),
                )
            },
        horizontalArrangement = Arrangement.Center,
    ) {
        cardWidgetlist.forEachIndexed { index, element ->
           // element.width = MainColumnWidth() / cardWidgetlist.size
            Column(
                modifier = Modifier
                    .drawBehind {
                        if (cardWidgetlist.lastIndex != index) {
                            drawLine(
                                brush = Brush.verticalGradient(colors = BorderColorOneWay),
                                start = Offset(size.width, 0f),
                                end = Offset(size.width, size.height),
                                strokeWidth = BorderSize.toPx(),
                            )
                        }
                    }
                    .width(element.width)
                    .fillMaxHeight()
                    .wrapContentHeight()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = element.title, textAlign = TextAlign.Center,
                    color = White, fontSize = 30.sp,
                )
                Image(
                    painter = painterResource(id = element.imagevector),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(start = 5.dp, end = 5.dp),
                    contentScale = ContentScale.Fit,
                )
                Text(
                    text = "${element.data} ${element.unit}", textAlign = TextAlign.Center,
                    color = White, fontSize = 24.sp
                )
            }

        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
private fun LeftFrame() {

    data class CardWidget(
        //val modifier: Modifier,
        var height: Dp,
        val foregroundColor: Brush,
        val percent: Int,
        val total: Int,
        val title: String,
        val imagevector: Int,

        )


    val cardWidgetList = listOf(
        CardWidget(
            0.dp,
            Brush.horizontalGradient(FuelGauge),
            55,
            300,
            "Fuel",
            R.drawable.ic_launcher_foreground,

            ),
        CardWidget(
            0.dp,
            Brush.horizontalGradient(WaterGauge),
            85,
            150,
            "Water",
            R.drawable.ic_launcher_foreground,

            ),
        CardWidget(
            0.dp,
            Brush.horizontalGradient(GrayGauge),
            65,
            25,
            "Waste",
            R.drawable.ic_launcher_foreground,

            ),
    )


    //Left Column
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .width(SideColumnWidth())
            .fillMaxHeight()
            .drawBehind {
                drawLine(
                    brush = Brush.verticalGradient(colors = BorderColorTwoWay),
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth = BorderSize.toPx(),
                )
            }

    ) {
        cardWidgetList.forEachIndexed { index, element ->
            element.height = MainHeight() / cardWidgetList.size
            val remaining = (element.total * element.percent) / 100
            Box(contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .height(element.height)
                    .fillMaxWidth()
                    .drawBehind {
                        if (cardWidgetList.lastIndex != index) {
                            rotate(degrees = 180F) {
                                //need invert coordinates for rotate
                                drawLine(
                                    color = White,
                                    //brush = Brush.horizontalGradient(colors = BorderColorOneWay),
                                    start = Offset(0f, 0f),
                                    end = Offset(size.width, 0f),
                                    strokeWidth = BorderSize.toPx(),
                                )
                            }

                        }

                    }


            ) {
                Box(
                    modifier = Modifier

                        .fillMaxWidth()
                        .height(element.height * element.percent / 100)
                        .clip(shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
                        .background(element.foregroundColor)
                )
                Column(
                    modifier = Modifier
                        .height(element.height)
                        .fillMaxWidth()
                        .padding(5.dp, 10.dp, 5.dp, 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = element.title, textAlign = TextAlign.Center,
                        color = White, fontSize = 30.sp, modifier = Modifier

                    )
                    Image(
                        painter = painterResource(id = element.imagevector),
                        contentDescription = "",
                        modifier = Modifier
                            .size(size = 50.dp)

                    )
                    Text(
                        text = "${element.percent} %", textAlign = TextAlign.Center,
                        color = White, fontSize = 25.sp, modifier = Modifier

                    )
                    Text(
                        text = "$remaining / ${element.total} L", textAlign = TextAlign.Center,
                        color = White, fontSize = 25.sp, modifier = Modifier

                    )
                }
            }


        }
    }

}

@Composable
private fun RightFrame() {
    data class CardWidget(
        val type: String,
        var height: Dp,
        val imagevector: Int,
        val color: Color,
        val title: String,
        val data: Double,
        val unit: String,

        )

    val CardWidgetList = listOf(
        CardWidget(
            "vertical_right",
            0.dp,
            R.drawable.ic_launcher_foreground,
            Red,
            "Starter",
            12.5,
            "V",

            ),
        CardWidget(
            "vertical_right",
            0.dp,
            R.drawable.ic_launcher_foreground,
            Red,
            "Comfort",
            24.25,
            "V",

            ),
        CardWidget(
            "vertical_right",
            0.dp,
            R.drawable.ic_launcher_foreground,
            Red,
            "Winch",
            23.50,
            "V",

            ),
    )


    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .width(SideColumnWidth())
            .fillMaxHeight()
            .drawBehind {
                drawLine(
                    brush = Brush.verticalGradient(colors = BorderColorTwoWay),
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = BorderSize.toPx(),
                )
            }
    ) {
        val height = MainHeight() / CardWidgetList.size
        CardWidgetList.forEachIndexed { index, element ->

            Column(
                modifier = Modifier
                    .drawBehind {
                        if (CardWidgetList.lastIndex != index) {
                            drawLine(
                                brush = Brush.horizontalGradient(colors = BorderColorOneWay),
                                start = Offset(size.width, size.height),
                                end = Offset(0f, size.height),
                                strokeWidth = BorderSize.toPx(),
                            )
                        }
                    }
                    .fillMaxWidth()
                    .height(height)
                    .wrapContentHeight()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = element.title, textAlign = TextAlign.Center,
                    color = White, fontSize = 30.sp,
                )
                Image(
                    painter = painterResource(id = element.imagevector),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(start = 5.dp, end = 5.dp),
                    contentScale = ContentScale.Fit,
                )
                Text(
                    text = "${element.data} ${element.unit}", textAlign = TextAlign.Center,
                    color = White, fontSize = 24.sp
                )
            }

        }

    }

}

 */
