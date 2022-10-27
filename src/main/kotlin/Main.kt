package tictactoe

fun field() = mutableListOf(
    MutableList(3) { '_' },
    MutableList(3) { '_' },
    MutableList(3) { '_' },
)

fun printField(list: List<MutableList<Char>>) {
    val field = """
        ---------
        | ${list[0].joinToString(" ")} |
        | ${list[1].joinToString(" ")} |
        | ${list[2].joinToString(" ")} |
        ---------
    """.trimIndent()
    println(field)
}

fun userInput() = readln().replace(" ".toRegex(), "")

fun printResult(list: List<MutableList<Char>>) {
    val userInput = userInput()
    checkError(userInput, list)
}

fun numError(userInput: String, list: List<MutableList<Char>>): Int {
    when {
        userInput.toIntOrNull() == null -> return 1
        else -> {
            val first = userInput[0].toString().toInt() - 1
            val left = userInput[1].toString().toInt() - 1
            when {
                first !in 0..2 || left !in 0..2 -> return 2
                list[first][left] == 'X' || list[first][left] == 'O' -> return 3
            }
        }
    }
    return 0
}

fun checkError(input: String, list: List<MutableList<Char>>) {
    when (numError(input, list)) {
        1 -> {
            println("You should enter numbers!")
            printResult(list)
        }
        2 -> {
            println("Coordinates should be from 1 to 3!")
            printResult(list)
        }
        3 -> {
            println("This cell is occupied! Choose another one!")
            printResult(list)
        }
        0 -> createNewField(input, list)
    }
}

fun createNewField(input: String, list: List<MutableList<Char>>) {
    var xCount = 0
    var oCount = 0
    for (i in list.indices) {
        xCount += list[i].count { it == 'X' }
        oCount += list[i].count { it == 'O' }
    }
    val first = input[0].toString().toInt() - 1
    val left = input[1].toString().toInt() - 1
    if (xCount - oCount == 0) {
        xCount++
        list[first][left] = 'X'
        printField(list)
        printWins(xCount, oCount, list)
    } else {
        oCount++
        list[first][left] = 'O'
        printField(list)
        printWins(xCount, oCount, list)
    }
}

fun checkWins(list: List<MutableList<Char>>): Int {
    when {
        checkDiagonal(list) == 1 ||
                checkHorizontal(list) == 1 ||
                checkVertical(list) == 1 -> return 1
        checkDiagonal(list) == 2 ||
                checkHorizontal(list) == 2 ||
                checkVertical(list) == 2 -> return 2
    }
    return 0
}

fun checkDiagonal(list: List<MutableList<Char>>): Int {
    val firstList = listOf(list[0][0], list[1][1], list[2][2])
    val lastList = listOf(list[0][2], list[1][1], list[2][0])
    when {
        firstList.count { it == 'X' } == 3 ||
                lastList.count { it == 'X' } == 3 -> return 1
        firstList.count { it == 'O' } == 3 ||
                lastList.count { it == 'O' } == 3 -> return 2
    }
    return 0
}

fun checkVertical(list: List<MutableList<Char>>): Int {
    val rotateList = field()
    for (i in list.indices) {
        for (j in list.indices) {
            rotateList[i][j] = list[j][i]
        }
        when {
            rotateList[i].count { it == 'X' } == 3 -> return 1
            rotateList[i].count { it == 'O' } == 3 -> return 2
        }
    }
    return 0
}

fun checkHorizontal(list: List<MutableList<Char>>): Int {
    for (i in list.indices) {
        when {
            list[i].count { it == 'X' } == 3 -> return 1
            list[i].count { it == 'O' } == 3 -> return 2
        }
    }
    return 0
}

fun printWins(xCount: Int, oCount: Int, list: List<MutableList<Char>>) {
    when (checkWins(list)) {
        1 -> println("X wins")
        2 -> println("O wins")
        else -> {
            if (xCount + oCount == 9) {
                println("Draw")
            }
            else {
                printResult(list)
            }
        }
    }
}

fun main() {
    val field = field()
    printField(field)
    printResult(field)
}
