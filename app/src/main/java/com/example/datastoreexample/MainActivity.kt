package com.example.datastoreexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.datastoreexample.ui.theme.DataStoreExampleTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Layout()
                }
            }
        }
    }
}
@Preview
@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun Layout(){
    var name by remember{ mutableStateOf("") }
    var age by remember{ mutableStateOf("") }

    val context = LocalContext.current
    val userManager = UserManager(context)

    val name_text = userManager.userNameFlow.collectAsState(initial = "")
    val age_text  = userManager.userAgeFlow.collectAsState(initial = 0)
    Column(
        Modifier
            .fillMaxWidth()
            .absolutePadding(10.dp, 10.dp, 10.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        OutlinedTextField(value = name , modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                name = it
            },
            label = { Text("Name") },)
        
        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(value = age , modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                age = it
            },
            label = { Text("Age") },)

        Spacer(modifier =Modifier.padding(10.dp))

        OutlinedButton(onClick = {

            GlobalScope.launch {
                userManager.storeUser(name, age.toInt())
            }



        }
        , modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Blue, contentColor = Color.White)
        ) {
           Text(text = "Save")
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = name_text.value)

        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = age_text.value.toString())
    }
}



