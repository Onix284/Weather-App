package org.example.room_contactpp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import org.example.room_contactpp.Models.WeatherApiResponse
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun App() {
        val  viewModel = viewModel { MyViewModel() }
        val state by viewModel.state.collectAsState()
        var city by remember { mutableStateOf("Mumbai") }


        if(state.ideal){

                LaunchedEffect(city) {
                        viewModel.getWeather(city)
                }

                Box(modifier = Modifier.fillMaxSize().background(color = Color.Red)){

                        WeatherHeader(value = city, onValueChange = {city = it}, onSearchClick = {
                                viewModel.getWeather(city)
                        })

                }
        }

        else if(state.isLoading){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text("Loading...")
                }
        }

        else if(!state.error.isNullOrEmpty()){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text("Error ${state.error}")
                }
        }
        else{
                Box (modifier = Modifier.fillMaxSize().background(color = Color.Transparent)){
                    AsyncImage(
                        model = "https://images.pexels.com/photos/2344227/pexels-photo-2344227.jpeg?auto=compress&cs=tinysrgb&w=600",
                        contentDescription = "backgroundImage",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                        Column( modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Transparent) // Make sure it's transparent
                                .padding(16.dp)) {

                                WeatherHeader(
                                        value = city,
                                        onValueChange = { city = it },
                                        onSearchClick = {
                                                viewModel.getWeather(city)
                                        })

                                state.WeatherResponse?.let {
                                        WeatherInfo(it)
                                }
                        }
                }
        }
}

@Preview()
@Composable
fun WeatherHeader(value : String = "", onValueChange : (String) -> Unit = {}, onSearchClick : () -> Unit = {}){
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                OutlinedTextField(

                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                                            .padding(horizontal = 15.dp, vertical = 0.dp)
                                            .background(color = Color.Transparent)
                                            .weight(100f),
                        value = value,
                        onValueChange = onValueChange,
                        label = { Text("Search City")},
                        trailingIcon = {
                                Icon(
                                        imageVector = Icons.Rounded.Search,
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp).weight(5f).clickable { onSearchClick() })},

                        colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Color.Black.copy(alpha = 1f),
                        unfocusedLabelColor = Color.Black.copy(alpha = 0.7f),
                        focusedBorderColor = Color.Black.copy(alpha = 2f),
                        unfocusedBorderColor = Color.Black.copy(alpha = 0.5f)),

                        shape = RoundedCornerShape(8.dp),
                )
        }
}


@Composable
fun WeatherInfo(weather: WeatherApiResponse){

        val icon = weather.weather?.firstOrNull()?.icon
        val iconUrl = icon?.let { "https://openweathermap.org/img/wn/${it}@2x.png" }

        Box(modifier = Modifier.fillMaxSize())
        {
               Column(modifier = Modifier.fillMaxWidth().padding(top = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                       if (!weather.weather.isNullOrEmpty()) {

                           Text(
                                 text = "Air Condition",
                                 modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 20.dp),
                                 textAlign = TextAlign.Center,
                                 fontSize = 15.sp,
                                 fontFamily = FontFamily.Serif,
                                 fontWeight = FontWeight.SemiBold
                                )

                           Spacer(modifier = Modifier.height(15.dp))

                           Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {

                               Icon(
                                   imageVector = Icons.Default.LocationOn,
                                   contentDescription = "LocationIcon",
                                   modifier = Modifier.size(50.dp).padding(end = 10.dp).padding(vertical = 5.dp)
                               )


                               Text(
                                   text = "${weather.name}",
                                   modifier = Modifier.padding(end = 30.dp)
                                       .padding(vertical = 5.dp),
                                   textAlign = TextAlign.Center,
                                   fontSize = 40.sp,
                                   fontFamily = FontFamily.Serif,
                                   fontWeight = FontWeight.Bold,
                                   style = TextStyle(
                                       shadow = Shadow(
                                           color = Color.White,
                                           offset = Offset(5f, 5f),
                                           blurRadius = 10f
                                       )
                                   )
                               )

                           }

                           Spacer(modifier = Modifier.height(10.dp))


                           AsyncImage(
                                     model = iconUrl,
                                     contentDescription = "Condition Icon",
                                     modifier = Modifier.size(100.dp),
                               contentScale = ContentScale.Fit
                                     )


                           //Card UI
                           Spacer(modifier = Modifier.height(10.dp))

                               Card(elevation = 20.dp,
                                   shape = RoundedCornerShape(30.dp),
                                   modifier = Modifier.fillMaxWidth()
                                       .height(500.dp)
                                       .padding(horizontal = 0.dp)
                                       .padding(bottom = 10.dp)) {

                                   //Card Background
                                   Column(modifier = Modifier.fillMaxSize().background(color = Color.Transparent)) {
                                       AsyncImage(
                                           model = "https://images.pexels.com/photos/1632781/pexels-photo-1632781.jpeg?auto=compress&cs=tinysrgb&w=600",
                                           contentDescription = "CardImage",
                                           modifier = Modifier.fillMaxSize(),
                                           contentScale = ContentScale.Crop
                                       )
                                   }

                                   //Card Content
                                   Column(modifier = Modifier.fillMaxSize()) {

                                       Text(
                                           text = "${weather.weather.firstOrNull()?.description}",
                                           modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp, horizontal = 20.dp),
                                           textAlign = TextAlign.Center,
                                           fontSize = 25.sp,
                                           fontFamily = FontFamily.Serif,
                                       )

                                       Text(
                                           text = " ${(weather.main?.temp?.minus(273.15))?.toInt()}°",
                                           modifier = Modifier.fillMaxWidth().padding(vertical = 0.dp, horizontal = 20.dp),
                                           textAlign = TextAlign.Center,
                                           fontSize = 100.sp,
                                           color = Color.White,
                                           style = TextStyle(
                                               shadow = Shadow(
                                                   color = Color.Black,
                                                   offset = Offset(10f, 10f),
                                                   blurRadius = 20f
                                               )
                                           ),
                                           fontFamily = FontFamily.Default,
                                           fontWeight = FontWeight.ExtraLight
                                       )

                                       Spacer(modifier = Modifier.height(20.dp))

                                       //Weather Details

                                       Row(modifier = Modifier.fillMaxWidth().weight(1f)) {

                                           //Wind
                                           Column(modifier = Modifier.fillMaxWidth().padding(5.dp).weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                                               Text("Wind",
                                                   fontSize = 20.sp,
                                                   fontFamily = FontFamily.Default,
                                                   fontWeight = FontWeight.SemiBold,
                                                   style = TextStyle(
                                                       shadow = Shadow(
                                                           color = Color.LightGray,
                                                           offset = Offset(5f, 5f),
                                                           blurRadius = 10f
                                                       )
                                                   )
                                               )

                                               Spacer(modifier = Modifier.height(10.dp))

                                               Text("${weather.wind?.speed} km/h",
                                                   fontSize = 27.sp,
                                                   fontFamily = FontFamily.Default,
                                                   fontWeight = FontWeight.Bold,
                                                   color = Color.White,
                                                   style = TextStyle(
                                                       shadow = Shadow(
                                                           color = Color.Black,
                                                           offset = Offset(5f, 5f),
                                                           blurRadius = 10f
                                                       )
                                                   )
                                               )
                                           }

                                           //Feels Like
                                           Column(modifier = Modifier.fillMaxWidth().padding(5.dp).weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                                               Text("Feels Like",
                                                   fontSize = 20.sp,
                                                   fontFamily = FontFamily.Default,
                                                   fontWeight = FontWeight.SemiBold,
                                                   style = TextStyle(
                                                       shadow = Shadow(
                                                           color = Color.LightGray,
                                                           offset = Offset(5f, 5f),
                                                           blurRadius = 10f
                                                       )
                                                   )
                                               )

                                               Spacer(modifier = Modifier.height(10.dp))

                                               Text("${weather.main?.feels_like} °F",
                                                   fontSize = 27.sp,
                                                   fontFamily = FontFamily.Default,
                                                   fontWeight = FontWeight.Bold,
                                                   color = Color.White,
                                                   style = TextStyle(
                                                       shadow = Shadow(
                                                           color = Color.Black,
                                                           offset = Offset(5f, 5f),
                                                           blurRadius = 10f
                                                       )
                                                   )
                                               )
                                           }

                                       }

                                       Row(modifier = Modifier.fillMaxWidth().weight(1f)) {

                                           //Humidity
                                           Column(modifier = Modifier.fillMaxWidth().padding(5.dp).weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                                               Text("Humidity",
                                                   fontSize = 20.sp,
                                                   fontFamily = FontFamily.Default,
                                                   fontWeight = FontWeight.SemiBold,
                                                   style = TextStyle(
                                                       shadow = Shadow(
                                                           color = Color.LightGray,
                                                           offset = Offset(5f, 5f),
                                                           blurRadius = 10f
                                                       )
                                                   )
                                               )

                                               Spacer(modifier = Modifier.height(10.dp))

                                               Text("${weather.main?.humidity} %",
                                                   fontSize = 27.sp,
                                                   fontFamily = FontFamily.Default,
                                                   fontWeight = FontWeight.Bold,
                                                   color = Color.White,
                                                   style = TextStyle(
                                                       shadow = Shadow(
                                                           color = Color.Black,
                                                           offset = Offset(5f, 5f),
                                                           blurRadius = 10f
                                                       )
                                                   )
                                               )
                                           }

                                           //Pressure
                                           Column(modifier = Modifier.fillMaxWidth().padding(5.dp).weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                                               Text("Pressure",
                                                   fontSize = 20.sp,
                                                   fontFamily = FontFamily.Default,
                                                   fontWeight = FontWeight.SemiBold,
                                                   style = TextStyle(
                                                       shadow = Shadow(
                                                           color = Color.LightGray,
                                                           offset = Offset(5f, 5f),
                                                           blurRadius = 10f
                                                       )
                                                   )
                                               )

                                               Spacer(modifier = Modifier.height(10.dp))

                                               Text("${weather.main?.pressure} mb",
                                                   fontSize = 27.sp,
                                                   fontFamily = FontFamily.Default,
                                                   fontWeight = FontWeight.Bold,
                                                   color = Color.White,
                                                   style = TextStyle(
                                                       shadow = Shadow(
                                                           color = Color.Black,
                                                           offset = Offset(5f, 5f),
                                                           blurRadius = 10f
                                                       )
                                                   )
                                               )
                                           }

                                       }

                                   }

                               }

                       }
                       else{
                            Text(
                                 text = "Enter correct city Name",
                                 modifier = Modifier.padding(
                                 vertical = 30.dp, horizontal = 20.dp),
                                 textAlign = TextAlign.Center,
                                 fontSize = 20.sp,
                                 fontFamily = FontFamily.Serif
                                 )
                           }

               }
        }
}
