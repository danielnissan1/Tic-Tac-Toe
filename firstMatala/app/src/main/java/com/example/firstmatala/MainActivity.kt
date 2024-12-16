package com.example.firstmatala

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firstmatala.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    enum class Turn {
        X,
        O
    }

    private var firstTurn = Turn.X
    private var currTurn = Turn.X

    private var boardList = mutableListOf<Button>()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initBoard()
    }

    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTapped(view: View) {
        if( view !is Button)
            return
        addToBoard(view)
        
        if(checkIfWon(X)){
            result("X Won!")
        } else if(checkIfWon(O)){
            result("O Won!")
        }else if(fullBoard()){
            result("Game Ended :(")
        }
    }

    private fun checkIfWon(player: String): Boolean {
        if(match(binding.a1, player) && match(binding.a2, player) && match(binding.a3, player))
            return true
        if(match(binding.b1, player) && match(binding.b2, player) && match(binding.b3, player))
            return true
        if(match(binding.c1, player) && match(binding.c2, player) && match(binding.c3, player))
            return true

        if(match(binding.a1, player) && match(binding.b1, player) && match(binding.c1, player))
            return true
        if(match(binding.a2, player) && match(binding.b2, player) && match(binding.c2, player))
            return true
        if(match(binding.a3, player) && match(binding.b3, player) && match(binding.c3, player))
            return true

        if(match(binding.a1, player) && match(binding.b2, player) && match(binding.c3, player))
            return true
        if(match(binding.a3, player) && match(binding.b2, player) && match(binding.c1, player))
            return true

        return false
    }

    private fun match (button: Button, symbol: String) = button.text == symbol
    private fun result(title: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setPositiveButton("Play Again")
            {_,_ ->
                resetBoard()
            }.setCancelable(false).show()
    }

    private fun resetBoard() {
        for (button in boardList){
            button.text = ""
        }

        currTurn = firstTurn
        setTurnLable()
    }

    private fun fullBoard(): Boolean {
        for(button in boardList){
            if (button.text == "")
                return false
        }
        return true
    }

    private fun addToBoard(button: Button) {
        if(button.text != "")
            return

        if(currTurn == Turn.O){
            button.text = O
            currTurn = Turn.X
        }
        else if (currTurn == Turn.X){
            button.text = X
            currTurn = Turn.O
        }

        setTurnLable()
    }

    private fun setTurnLable() {
        var turnText = ""
        if (currTurn == Turn.X)
            turnText = "turn $X"
        else if (currTurn == Turn.O)
            turnText = "turn $O"
        binding.turnTV.text = turnText
    }

    companion object {
        const val O = "O"
        const val X = "X"
    }


}