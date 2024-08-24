package com.example.rockpaperscissors1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rockpaperscissors1.ui.theme.RockPaperScissors1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RockPaperScissors1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RockPaperScissors(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun RockPaperScissors(modifier: Modifier = Modifier) {

    var playerChoice = remember { mutableStateOf("") }
    var computerChoice = remember { mutableStateOf("") }
    var matchPoints = remember { mutableStateOf(3) }
    var playerPoints = remember { mutableStateOf(0) }
    var computerPoints = remember { mutableStateOf(0) }
    var output = remember { mutableStateOf("") }
    var Expanded = remember { mutableStateOf(false) }

    fun play() {
        playerChoice.value = playerChoice.value.lowercase()
        if(playerChoice.value!="rock" && playerChoice.value!="paper" && playerChoice.value!="scissors") {
            output.value = "Please enter valid input!"
        }
        else {
            output.value = ""
            val randomNumber = (1..3).random()
            computerChoice.value = when (randomNumber) {
                1 -> "rock"
                2 -> "paper"
                else -> "scissors"
            }
            if (computerPoints.value < matchPoints.value && playerPoints.value < matchPoints.value) {
                if (randomNumber == 1) {
//            if(playerChoice == "rock")
                    if (playerChoice.value == "paper") playerPoints.value++
                    else if (playerChoice.value == "scissors") computerPoints.value++
                } else if (randomNumber == 2) {
//            if(playerChoice == "paper") println("draw")
                    if (playerChoice.value == "scissors") playerPoints.value++
                    else if (playerChoice.value == "rock") computerPoints.value++
                } else if (randomNumber == 3) {
//            if(playerChoice == "scissors") println("draw")
                    if (playerChoice.value == "rock") playerPoints.value++
                    else if (playerChoice.value == "paper") computerPoints.value++
                }
            } else {
                if (computerPoints.value == matchPoints.value)
                    output.value =
                        "Computer has won ${computerPoints.value}-${playerPoints.value}. :("
                else {
                    output.value =
                        "Congratulations, you have won by ${playerPoints.value}-${computerPoints.value}! :)"
                    playerPoints.value = 0
                    computerPoints.value = 0
                }
            }
        }

    }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        Text("Score: User ${playerPoints.value} - ${computerPoints.value} Computer",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(value = playerChoice.value, onValueChange = { it ->
            playerChoice.value = it
            play()
        },
            label = {Text("Rock, Paper or Scissors?")})

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { Expanded.value = true }) {
            Text("Select Number of Points")
            androidx.compose.material3.Icon( imageVector = Icons.Default.ArrowDropDown,
                "Arrow Down")
        }
        DropdownMenu(expanded = Expanded.value, onDismissRequest = { Expanded.value = false }) {
            DropdownMenuItem(text = {Text("3 Points")},
                onClick = {
                    Expanded.value = false
                    matchPoints.value = 3

                })
            DropdownMenuItem(text = {Text("5 Points")},
                onClick = {
                    Expanded.value = false
                    matchPoints.value = 5

                })
            DropdownMenuItem(text = {Text("10 Points")},
                onClick = {
                    Expanded.value = false
                    matchPoints.value = 10

                })
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = output.value,
            style = MaterialTheme.typography.headlineMedium)


    }
}

@Preview(showBackground = true)
@Composable
fun RockPaperScissorsPreview() {
    RockPaperScissors1Theme {
        RockPaperScissors()
    }
}
