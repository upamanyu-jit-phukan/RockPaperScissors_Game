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

    var playerChoice by remember { mutableStateOf("") }
    var computerChoice by remember { mutableStateOf("") }
    var matchPoints by remember { mutableStateOf(3) }
    var playerPoints by remember { mutableStateOf(0) }
    var computerPoints by remember { mutableStateOf(0) }
    val output = remember { mutableStateOf("") }
    var Expanded by remember { mutableStateOf(false) }

    fun play() {
        playerChoice = playerChoice.lowercase()
        while(playerChoice!="rock" && playerChoice!="paper" && playerChoice!="scissors") {
            output.value = "Please enter valid input!"
        }
        output.value = ""
        val randomNumber = (1..3).random()
        if(randomNumber==1) {
            computerChoice = "rock"
        }
        else if(randomNumber==2) {
            computerChoice = "paper"
        }
        else if(randomNumber==3) {
            computerChoice = "scissors"
        }
        if(computerPoints<matchPoints && playerPoints<matchPoints) {
            if (randomNumber == 1) {
//            if(playerChoice == "rock")
                if (playerChoice == "paper") playerPoints++
                else if (playerChoice == "scissors") computerPoints++
            } else if (randomNumber == 2) {
//            if(playerChoice == "paper") println("draw")
                else if (playerChoice == "scissors") playerPoints++
                else if (playerChoice == "rock") computerPoints++
            } else if (randomNumber == 3) {
//            if(playerChoice == "scissors") println("draw")
                if (playerChoice == "rock") playerPoints++
                else if (playerChoice == "paper") computerPoints++
            }
        }
        else {
            if (computerPoints == matchPoints)
                output.value = "Computer has won $computerPoints-$playerPoints. :("
            else {
                output.value = "Congratulations, you have won by $playerPoints-$computerPoints! :)"
                playerPoints = 0
                computerPoints = 0
            }
        }

    }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        Text("Score: User $playerPoints - $computerPoints Computer",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedTextField(value = playerChoice, onValueChange = { it ->
            playerChoice = it
            play()
        },
            label = {Text("Rock, Paper or Scissors?")})

        Spacer(modifier = Modifier.width(16.dp))

        Button(onClick = { Expanded = true }) {
            Text("Select Number of Points")
            androidx.compose.material3.Icon( imageVector = Icons.Default.ArrowDropDown,
                "Arrow Down")
        }
        DropdownMenu(expanded = Expanded, onDismissRequest = { Expanded = false }) {
            DropdownMenuItem(text = {Text("3 Points")},
                onClick = {
                    Expanded = true
                    matchPoints = 3
                    play()
                })
            DropdownMenuItem(text = {Text("5 Points")},
                onClick = {
                    Expanded = true
                    matchPoints = 5
                    play()
                })
            DropdownMenuItem(text = {Text("10 Points")},
                onClick = {
                    Expanded = true
                    matchPoints = 10
                    play()
                })
        }
        Text("${output.value}",
            Style = MaterialTheme.typography.headlineMedium
        )


    }
}

@Preview(showBackground = true)
@Composable
fun RockPaperScissorsPreview() {
    RockPaperScissors1Theme {
        RockPaperScissors()
    }
}