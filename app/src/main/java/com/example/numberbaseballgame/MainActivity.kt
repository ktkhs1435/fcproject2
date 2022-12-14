package com.example.numberbaseballgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val numberBaseballGame = NumberBaseballGame()


    private val baseballGameHistory = mutableListOf<NumberBaseballGamePlayHistory>()

    private lateinit var userInputNumberEditTextView: EditText

//    private lateinit var baseballGameHistoryBoardAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        userInputNumberEditTextView = findViewById(R.id.user_input_numbers)

        setupUserInput()
        setupCheckAnswer()
        setUpResultRecyclerView()
    }

    private fun setUpResultRecyclerView() {

        val recyclerView = findViewById<RecyclerView>(R.id.baseball_game_history_board)
        recyclerView.adapter = RecyclerViewAdapter(baseballGameHistory, LayoutInflater.from(this))


    }

    private fun setupCheckAnswer() {
        findViewById<TextView>(R.id.check_answer).setOnClickListener {
            val tmpInputNumber = findViewById<EditText>(R.id.user_input_numbers).text
            if(tmpInputNumber.length < 3) {
                showIntpuMaxDigitWarnningToast()
            } else {
                baseballGameHistory.add(numberBaseballGame.checkAnswer(tmpInputNumber.toString()))
                setUpResultRecyclerView()
                userInputNumberEditTextView.text.clear()
            }

            val imm = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(userInputNumberEditTextView.windowToken,0)
        }
    }

    private fun checkDuplicatedUserInputNumberValidation(userInput: String): Boolean {

        return !(userInput[0] == userInput[1] || userInput[0] == userInput[2] || userInput[1] == userInput[2])
    }

    private fun showIntputDuplicatedWarnningToast() {

        Toast.makeText(this@MainActivity,"????????? ????????? ????????????.", Toast.LENGTH_SHORT).show()
    }

    private fun showIntpuMaxDigitWarnningToast() {

        Toast.makeText(this@MainActivity,"3?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show()
    }

    private fun setupUserInput() {
        val userInputNumberEditTextView = findViewById<EditText>(R.id.user_input_numbers)
        userInputNumberEditTextView.doAfterTextChanged {

            if(it.toString().length>3) {
                showIntpuMaxDigitWarnningToast()
                userInputNumberEditTextView.text.clear()
            } else if(it.toString().length==3) {
                if(!checkDuplicatedUserInputNumberValidation(it.toString())) {
                    showIntputDuplicatedWarnningToast()
                    userInputNumberEditTextView.text.clear()
                }
            }

        }
    }
}

class RecyclerViewAdapter(
    private var resultList: MutableList<NumberBaseballGamePlayHistory>,
    private val inflater: LayoutInflater
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var result: TextView // ???????????? ??????????????? ????????? ???????????? ?????? ????????????.
        var inputNumber: TextView // ???????????? ????????? ??????????????? ???????????? ?????? ??? ?????????
        var tryCount: TextView // ??? ?????? ?????? ????????? ???????????? ?????? ????????????

        init {

            result = itemView.findViewById(R.id.result)
            inputNumber = itemView.findViewById(R.id.input_number)
            tryCount = itemView.findViewById(R.id.try_count)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = inflater.inflate(R.layout.baseball_item_view,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.result.text = resultList.get(position).result
        holder.inputNumber.text = resultList.get(position).inputNumber
        val tryCount = position + 1
        holder.tryCount.text = tryCount.toString()
    }

    override fun getItemCount(): Int {
        return resultList.size
    }
}