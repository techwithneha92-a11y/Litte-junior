package com.example.data

data class MathProblem(
    val id: Int,
    val num1: Int,
    val num2: Int,
    val operator: Char, // '+' or '-'
    val result: Int,
    val objectEmoji: String,
    val objectName: String,
    val explanation: String
)

object MathRepository {

    val additionLessons = listOf(
        MathProblem(
            id = 1,
            num1 = 1,
            num2 = 1,
            operator = '+',
            result = 2,
            objectEmoji = "🍎",
            objectName = "Apples",
            explanation = "1 apple plus 1 apple equals 2 apples!"
        ),
        MathProblem(
            id = 2,
            num1 = 2,
            num2 = 3,
            operator = '+',
            result = 5,
            objectEmoji = "🎈",
            objectName = "Balloons",
            explanation = "2 balloons plus 3 balloons equals 5 balloons!"
        ),
        MathProblem(
            id = 3,
            num1 = 5,
            num2 = 5,
            operator = '+',
            result = 10,
            objectEmoji = "⭐",
            objectName = "Stars",
            explanation = "5 stars plus 5 stars equals 10 stars!"
        ),
        MathProblem(
            id = 4,
            num1 = 3,
            num2 = 4,
            operator = '+',
            result = 7,
            objectEmoji = "🌸",
            objectName = "Flowers",
            explanation = "3 flowers plus 4 flowers equals 7 flowers!"
        ),
        MathProblem(
            id = 5,
            num1 = 4,
            num2 = 2,
            operator = '+',
            result = 6,
            objectEmoji = "🍬",
            objectName = "Candies",
            explanation = "4 candies plus 2 candies equals 6 candies!"
        ),
        MathProblem(
            id = 6,
            num1 = 6,
            num2 = 3,
            operator = '+',
            result = 9,
            objectEmoji = "🦆",
            objectName = "Ducks",
            explanation = "6 ducks plus 3 ducks equals 9 ducks!"
        ),
        MathProblem(
            id = 7,
            num1 = 7,
            num2 = 1,
            operator = '+',
            result = 8,
            objectEmoji = "🧁",
            objectName = "Cupcakes",
            explanation = "7 cupcakes plus 1 cupcake equals 8 cupcakes!"
        ),
        MathProblem(
            id = 8,
            num1 = 8,
            num2 = 4,
            operator = '+',
            result = 12,
            objectEmoji = "🍓",
            objectName = "Strawberries",
            explanation = "8 strawberries plus 4 strawberries equals 12 strawberries!"
        )
    )

    val subtractionLessons = listOf(
        MathProblem(
            id = 101,
            num1 = 5,
            num2 = 2,
            operator = '-',
            result = 3,
            objectEmoji = "🍎",
            objectName = "Apples",
            explanation = "We have 5 apples. Take away 2 apples, and 3 apples remain!"
        ),
        MathProblem(
            id = 102,
            num1 = 10,
            num2 = 5,
            operator = '-',
            result = 5,
            objectEmoji = "🎈",
            objectName = "Balloons",
            explanation = "We have 10 balloons. Pop 5 balloons, and 5 balloons remain!"
        ),
        MathProblem(
            id = 103,
            num1 = 4,
            num2 = 1,
            operator = '-',
            result = 3,
            objectEmoji = "⭐",
            objectName = "Stars",
            explanation = "We have 4 stars. Remove 1 star, and 3 stars remain!"
        ),
        MathProblem(
            id = 104,
            num1 = 6,
            num2 = 3,
            operator = '-',
            result = 3,
            objectEmoji = "🐟",
            objectName = "Fish",
            explanation = "6 fish swim together. 3 swim away, and 3 fish remain!"
        ),
        MathProblem(
            id = 105,
            num1 = 8,
            num2 = 4,
            operator = '-',
            result = 4,
            objectEmoji = "🌸",
            objectName = "Flowers",
            explanation = "8 garden flowers. Pick 4 flowers, and 4 flowers remain!"
        ),
        MathProblem(
            id = 106,
            num1 = 7,
            num2 = 2,
            operator = '-',
            result = 5,
            objectEmoji = "🧁",
            objectName = "Cupcakes",
            explanation = "7 sweet cupcakes. Eat 2 cupcakes, and 5 cupcakes remain!"
        ),
        MathProblem(
            id = 107,
            num1 = 9,
            num2 = 3,
            operator = '-',
            result = 6,
            objectEmoji = "🪁",
            objectName = "Kites",
            explanation = "9 kites in the sky. Land 3 kites, and 6 kites remain!"
        ),
        MathProblem(
            id = 108,
            num1 = 12,
            num2 = 4,
            operator = '-',
            result = 8,
            objectEmoji = "🍓",
            objectName = "Strawberries",
            explanation = "12 strawberries in the bowl. Share 4 with friends, and 8 remain!"
        )
    )

    fun generateRandomAddition(): MathProblem {
        val emojis = listOf("🍎", "🎈", "⭐", "🌸", "🍬", "🐟", "🧁", "🍓")
        val a = (1..9).random()
        val b = (1..9).random()
        val emoji = emojis.random()
        return MathProblem(
            id = (1000..9999).random(),
            num1 = a,
            num2 = b,
            operator = '+',
            result = a + b,
            objectEmoji = emoji,
            objectName = "Items",
            explanation = "$a plus $b equals ${a + b}!"
        )
    }

    fun generateRandomSubtraction(): MathProblem {
        val emojis = listOf("🍎", "🎈", "⭐", "🌸", "🍬", "🐟", "🧁", "🍓")
        val a = (3..12).random()
        val b = (1 until a).random()
        val emoji = emojis.random()
        return MathProblem(
            id = (1000..9999).random(),
            num1 = a,
            num2 = b,
            operator = '-',
            result = a - b,
            objectEmoji = emoji,
            objectName = "Items",
            explanation = "$a take away $b leaves ${a - b}!"
        )
    }
}
