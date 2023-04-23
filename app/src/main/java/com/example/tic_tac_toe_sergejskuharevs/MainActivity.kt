package com.example.tic_tac_toe_sergejskuharevs



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.RadioGroup
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import java.util.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val buttons: Array<Array<Button?>> = Array<Array<Button?>>(3) { arrayOfNulls<Button>(3) }
    private var player1Turn = true
    private var round = 0
    private var player1Points = 0
    private var player2Points = 0
    private lateinit var textviewP1: TextView
    private lateinit var texviewP2: TextView
    private var playerFirst = ""
    private var playerSecond = ""
    private var isAgainstComputer = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadBoard()
    }

    private fun loadBoard(){
        textviewP1 = findViewById(R.id.text_viewP1)
        texviewP2 = findViewById(R.id.text_viewP2)
        startGame()
          //  getPlayerName()
        for (i in buttons.indices) {
            for (j in buttons.indices) {
                val buttonID = "button_$i$j"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                buttons[i][j] = findViewById(resID)
                buttons[i][j]!!.setOnClickListener(this)
            }
        }
        val buttonReset: Button = findViewById(R.id.resetBtn)
        buttonReset.setOnClickListener { resetGame() }
        val buttonGame: Button = findViewById(R.id.newgame)
        buttonGame.setOnClickListener {
            newgame()
        }
    }


   private fun startGame() {
        val inflater1 = LayoutInflater.from(this@MainActivity)
        val plView1 = inflater1.inflate(R.layout.mode_choise,null)
        val plDialog1 = AlertDialog.Builder(this@MainActivity)
        val radioGroup = plView1.findViewById<RadioGroup>(R.id.gameModeRadioGroup)
        val radioButtonComputer = plView1.findViewById<RadioButton>(R.id.humanVsCompRadioBtn)

        plDialog1.setView(plView1)
        plDialog1.setPositiveButton("OK"){
            dialog,_->
            isAgainstComputer = radioButtonComputer.isChecked()
            if(isAgainstComputer){
                getPlayerName()
            } else {
                getPlayersName()
            }

        }
        plDialog1.create()
        plDialog1.show()
    }
    private fun getPlayerName(){
        val infalter = LayoutInflater.from(this@MainActivity)
        val plView = infalter.inflate(R.layout.player_name,null)
        val player1Name = plView.findViewById<EditText>(R.id.firstPl)
        val player2Name = ""
        val plDialog = AlertDialog.Builder(this@MainActivity)
        plDialog.setView(plView)
        plDialog.setPositiveButton("OK"){
                dialog,_->
            if (player1Name.text.toString().isEmpty()

            ){
                playerFirst = "Player1"
                playerSecond = "COMPUTER"

                textviewP1.text = "$playerFirst : 0"
                texviewP2.text = "$playerSecond : 0"
            }
            else{
                playerFirst = player1Name.text.toString()
                playerSecond = "COMPUTER"

                textviewP1.text = "$playerFirst : 0"
                texviewP2.text = "$playerSecond : 0"
            }
        }
        plDialog.create()
        plDialog.show()

    }
    private fun getPlayersName() {
        val infalter2 = LayoutInflater.from(this@MainActivity)
        val plView = infalter2.inflate(R.layout.players_name,null)
        val player1Name = plView.findViewById<EditText>(R.id.firstPl1)
        val player2Name = plView.findViewById<EditText>(R.id.firstPl2)
        val plDialog2 = AlertDialog.Builder(this@MainActivity)
        plDialog2.setView(plView)
        plDialog2.setPositiveButton("Add"){
                dialog,_->
            if (player1Name.text.toString().isEmpty()
                && player2Name.text.toString().isEmpty()
            ){
                playerFirst = "Player1"
                playerSecond = "Player2"

                textviewP1.text = "$playerFirst : 0"
                texviewP2.text = "$playerSecond : 0"
            }
            else{
                playerFirst = player1Name.text.toString()
                playerSecond = player2Name.text.toString()

                textviewP1.text = "$playerFirst : 0"
                texviewP2.text = "$playerSecond : 0"
            }

        }

        plDialog2.create()
        plDialog2.show()
    }

    override fun onClick(button: View) {
        if (!(button as Button).text.toString().equals("")) {
            return
        }
        if (player1Turn) {
            button.text = "X"
            button.setTextColor(resources.getColor(R.color.red))
            round++
            if (checkForWin()) {
                player1Wins()
            } else if (isAgainstComputer) {
                computerMove()
            } else if (round == 9) {
                draw()
            } else {
                player1Turn = !player1Turn
            }
        } else {
            button.text = "O"
            button.setTextColor(resources.getColor(R.color.green))
            round++
            if (checkForWin()) {
                player2Wins()
            } else if (round == 9) {
                draw()
            } else {
                player1Turn = !player1Turn
            }
        }
    }

    private fun computerMove() {

        val rand = Random()


        while (true) {
            val row = rand.nextInt(3)
            val col = rand.nextInt(3)
            val button = buttons[row][col]
            if (button?.text.toString() == "") {
                if (button != null) {
                    button.text = "O"
                }
                if (button != null) {
                    button.setTextColor(resources.getColor(R.color.green))
                }
                round++
                if (checkForWin()) {
                    player2Wins()
                } else if (round == 9) {
                    draw()
                } else {
                    player1Turn = true
                }
                break
            } else if (round == 9) {
                draw()
                break
            }
        }
    }


    private fun checkForWin(): Boolean {
        val field = Array(3) { arrayOfNulls<String>(3) }
        for (i in buttons.indices) {
            for (j in buttons.indices) {
                field[i][j] = buttons[i][j]!!.text.toString()
            }
        }
        for (i in buttons.indices) {
            if ((field[i][0] == field[i][1]) &&
                (field[i][0] == field[i][2]) &&
                (field[i][0] != "")
            ) {
                return true
            }
        }
        for (i in buttons.indices) {
            if ((field[0][i] == field[1][i]) &&
                (field[0][i] == field[2][i]) &&
                (field[0][i] != "")
            ) {
                return true
            }
        }
        if ((field[0][0] == field[1][1]) &&
            (field[0][0] == field[2][2]) &&
            (field[0][0] != "")
        ) {
            return true
        }
        return field[0][2] == field[1][1] &&
                field[0][2] == field[2][0] &&
                field[0][2] != ""
    }

    private fun player1Wins() {
        player1Points++
        AlertDialog.Builder(this@MainActivity)
            .setTitle("Congratulation !!")
            .setIcon(R.drawable.ic_launcher_background)
            .setMessage("Congratulation $playerFirst, you are Win ... !!")
            .create()
            .show()
        updatePointsText()
        resetBoard()
    }

    private fun player2Wins() {
        player2Points++
        AlertDialog.Builder(this@MainActivity)
            .setTitle("Congratulation !!")
            .setIcon(R.drawable.ic_launcher_background)
            .setMessage("Congratulation $playerSecond, you are Win ... !!")
            .create()
            .show()
        updatePointsText()
        resetBoard()
    }

    private fun draw() {
        AlertDialog.Builder(this)
            .setTitle("Draw !!")
            .setMessage("The game is drawn, Please Play again...!!")
            .setPositiveButton("ok"){dialog,_->
                resetBoard()

            }
            .setNeutralButton("Cancel"){dialog,_->resetBoard() }
            .create()
            .show()
    }


    private fun updatePointsText() {
        textviewP1.text = "$playerFirst  :  $player1Points"
        texviewP2.text = "$playerSecond  : $player2Points"

    }

    private fun resetBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j]!!.text = ""
            }
        }
        round = 0
        player1Turn = true
    }

    private fun resetGame() {
        player1Points = 0
        player2Points = 0
        updatePointsText()
        resetBoard()
    }
private fun newgame(){
    loadBoard()
}
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("roundCount", round)
        outState.putInt("player1Points", player1Points)
        outState.putInt("player2Points", player2Points)
        outState.putBoolean("player1Turn", player1Turn)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        round = savedInstanceState.getInt("roundCount")
        player1Points = savedInstanceState.getInt("player1Points")
        player2Points = savedInstanceState.getInt("player2Points")
        player1Turn = savedInstanceState.getBoolean("player1Turn")
    }
}

