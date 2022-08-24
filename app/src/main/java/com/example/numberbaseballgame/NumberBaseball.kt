package com.example.numberbaseballgame

class NumberBaseballGamePlayHistory constructor(val inputNumber: String, val result: String) {
    // - 사용자가 정답으로 시도한 값과 결과를 저장하기 위한 클래스 입니다.
    // - inputNumber는 사용자가 정답으로 시도한 값입니다.
    //    - ex) 123, 493, 263
    // - result는 사용자가 정답으로 시도한 값의 결과 입니다
    //    - ex) 1S 1B 1O, 3S, 2S 1B
}

class NumberBaseballGame {
    val answer: String // 정답을 담고 있는 변수 입니다.

    init {
        answer = makeAnswer() // 객체를 생성할 때 정답을 생성해줍니다.
    }

    fun makeAnswer(): String {
        // [코드 작성 필요]
        // - 야구게임이 사용될 정답을 만들어 냅니다 (사용자가 맞춰야 하는 정답 숫자)
        // - 정답의 경우 0~9로만 이루어진 3자리 숫자이어야 하며, 같은 숫자는 한번만 사용될 수 있습니다
        //      ex) 001, 010, 100, 000 -> 불가능 (0이 복수번 사용되어 불가능)
        //      ex) 012, 045, -> 가능
        // - getRandomNumberBetweenZeroToNine함수를 사용하시면 0~9까지 숫자중 한개를 랜덤으로 얻을 수 있습니다
        var tempString = ""
        while(tempString.length<3) {
            val tempString1 = getRandomNumberBetweenZeroToNine().toString()
            if( tempString1 !in tempString ){
                tempString += tempString1
            }
        }

        return tempString
    }

    fun getRandomNumberBetweenZeroToNine(): Int {
        // 0~9 까지의 숫자중 랜덤하게 하나를 반환합니다
        return (0 until 10).random()
    }

    fun checkAnswer(inputString: String): NumberBaseballGamePlayHistory {
        // [코드 작성 필요]
        // - 정답과 비교하는 함수 이며 반환 타입은 NumberBaseballGamePlayHistory 입니다.
        // - 비교 결과 표기 방법
        //   - 스트라이크의 경우 S, 볼의 경우 B, 아웃의 경우 O로 표기 합니다
        //   ex) 1S 1O 1B -> 1스트라이크, 1아웃, 1볼
        //   ex) 2S 1O -> 2스트라이크, 1아웃
        //   ex) 3S -> 3스트라이크 (정답)


        var strike : Int = 0
        var ball : Int = 0
        var out : Int = 0

        for (i in 0..2) {
            if( answer[i] == inputString[i]){
                strike++
            }else if (inputString[i] in answer) {
                ball++
            } else {
                out++
            }
        }

        if(strike == 3) {
            return NumberBaseballGamePlayHistory(inputString, "3S")
        } else if (strike == 2 && out == 1) {
            return  NumberBaseballGamePlayHistory(inputString, "2S 1O")
        } else if (strike == 2 && ball == 1){
            return  NumberBaseballGamePlayHistory(inputString, "2S 1B")
        } else if (strike == 1 && out == 2) {
            return NumberBaseballGamePlayHistory(inputString, "1S 2O")
        } else if (strike == 1 && ball == 2) {
            return NumberBaseballGamePlayHistory(inputString, "1S 2B")
        } else if (strike == 1 && out == 1 && ball == 1) {
            return NumberBaseballGamePlayHistory(inputString, "1S 1O 1B")
        } else if (strike == 0 && out == 1 && ball == 2) {
            return NumberBaseballGamePlayHistory(inputString, "1O 2B")
        } else if (strike == 0 && out == 2 && ball == 1) {
            return NumberBaseballGamePlayHistory(inputString, "2O 1B")
        } else {
            return NumberBaseballGamePlayHistory(inputString, "3O")
        }
    }
}
