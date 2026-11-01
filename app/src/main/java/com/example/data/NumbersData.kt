package com.example.data

data class NumberItem(
    val number: Int,
    val spelling: String,
    val pronunciation: String,
    val objectName: String,
    val objectEmoji: String,
    val sentence: String,
    val countingFact: String,
    val cardColorHex: Long
)

object NumbersRepository {
    val numberList = listOf(
        NumberItem(
            number = 1,
            spelling = "ONE",
            pronunciation = "One.",
            objectName = "Apple",
            objectEmoji = "🍎",
            sentence = "There is 1 red apple.",
            countingFact = "One is the very first counting number!",
            cardColorHex = 0xFFFF5252
        ),
        NumberItem(
            number = 2,
            spelling = "TWO",
            pronunciation = "Two.",
            objectName = "Balloons",
            objectEmoji = "🎈",
            sentence = "There are 2 colorful balloons.",
            countingFact = "Two makes a pair, just like two shoes!",
            cardColorHex = 0xFF448AFF
        ),
        NumberItem(
            number = 3,
            spelling = "THREE",
            pronunciation = "Three.",
            objectName = "Stars",
            objectEmoji = "⭐",
            sentence = "There are 3 shining stars.",
            countingFact = "A triangle has three points and three sides!",
            cardColorHex = 0xFFFFB300
        ),
        NumberItem(
            number = 4,
            spelling = "FOUR",
            pronunciation = "Four.",
            objectName = "Teddy Bears",
            objectEmoji = "🧸",
            sentence = "There are 4 cuddly teddy bears.",
            countingFact = "A square has four equal sides and corners!",
            cardColorHex = 0xFF8D6E63
        ),
        NumberItem(
            number = 5,
            spelling = "FIVE",
            pronunciation = "Five.",
            objectName = "Flowers",
            objectEmoji = "🌸",
            sentence = "There are 5 pretty flowers.",
            countingFact = "You have five little fingers on each hand!",
            cardColorHex = 0xFFEC407A
        ),
        NumberItem(
            number = 6,
            spelling = "SIX",
            pronunciation = "Six.",
            objectName = "Fish",
            objectEmoji = "🐟",
            sentence = "There are 6 swimming fish.",
            countingFact = "Insects have six tiny legs to walk with!",
            cardColorHex = 0xFF00BCD4
        ),
        NumberItem(
            number = 7,
            spelling = "SEVEN",
            pronunciation = "Seven.",
            objectName = "Rainbows",
            objectEmoji = "🌈",
            sentence = "There are 7 magic rainbows.",
            countingFact = "A rainbow has seven gorgeous colors!",
            cardColorHex = 0xFF7E57C2
        ),
        NumberItem(
            number = 8,
            spelling = "EIGHT",
            pronunciation = "Eight.",
            objectName = "Butterflies",
            objectEmoji = "🦋",
            sentence = "There are 8 fluttering butterflies.",
            countingFact = "An octopus has eight arms in the sea!",
            cardColorHex = 0xFF26A69A
        ),
        NumberItem(
            number = 9,
            spelling = "NINE",
            pronunciation = "Nine.",
            objectName = "Cupcakes",
            objectEmoji = "🧁",
            sentence = "There are 9 sweet cupcakes.",
            countingFact = "Nine is the biggest single-digit number!",
            cardColorHex = 0xFFFF7043
        ),
        NumberItem(
            number = 10,
            spelling = "TEN",
            pronunciation = "Ten.",
            objectName = "Apples",
            objectEmoji = "🍎",
            sentence = "There are 10 juicy apples.",
            countingFact = "You have ten toes on your two feet!",
            cardColorHex = 0xFF43A047
        ),
        NumberItem(
            number = 11,
            spelling = "ELEVEN",
            pronunciation = "Eleven.",
            objectName = "Ducks",
            objectEmoji = "🦆",
            sentence = "There are 11 yellow ducks.",
            countingFact = "Eleven is ten plus one more!",
            cardColorHex = 0xFFFDD835
        ),
        NumberItem(
            number = 12,
            spelling = "TWELVE",
            pronunciation = "Twelve.",
            objectName = "Strawberries",
            objectEmoji = "🍓",
            sentence = "There are 12 sweet strawberries.",
            countingFact = "Twelve is also called one dozen!",
            cardColorHex = 0xFFE91E63
        ),
        NumberItem(
            number = 13,
            spelling = "THIRTEEN",
            pronunciation = "Thirteen.",
            objectName = "Kites",
            objectEmoji = "🪁",
            sentence = "There are 13 flying kites.",
            countingFact = "Thirteen is the first teen number!",
            cardColorHex = 0xFF00ACC1
        ),
        NumberItem(
            number = 14,
            spelling = "FOURTEEN",
            pronunciation = "Fourteen.",
            objectName = "Hearts",
            objectEmoji = "💖",
            sentence = "There are 14 loving hearts.",
            countingFact = "Two full weeks have fourteen days!",
            cardColorHex = 0xFFFF4081
        ),
        NumberItem(
            number = 15,
            spelling = "FIFTEEN",
            pronunciation = "Fifteen.",
            objectName = "Candies",
            objectEmoji = "🍬",
            sentence = "There are 15 sweet candies.",
            countingFact = "Fifteen minutes is a quarter of an hour!",
            cardColorHex = 0xFFFFA000
        ),
        NumberItem(
            number = 16,
            spelling = "SIXTEEN",
            pronunciation = "Sixteen.",
            objectName = "Leaves",
            objectEmoji = "🍃",
            sentence = "There are 16 green leaves.",
            countingFact = "Four groups of four make sixteen!",
            cardColorHex = 0xFF66BB6A
        ),
        NumberItem(
            number = 17,
            spelling = "SEVENTEEN",
            pronunciation = "Seventeen.",
            objectName = "Diamonds",
            objectEmoji = "💎",
            sentence = "There are 17 sparkling diamonds.",
            countingFact = "Seventeen is a very special lucky number!",
            cardColorHex = 0xFF29B6F6
        ),
        NumberItem(
            number = 18,
            spelling = "EIGHTEEN",
            pronunciation = "Eighteen.",
            objectName = "Cherries",
            objectEmoji = "🍒",
            sentence = "There are 18 red cherries.",
            countingFact = "Two groups of nine make eighteen!",
            cardColorHex = 0xFFD81B60
        ),
        NumberItem(
            number = 19,
            spelling = "NINETEEN",
            pronunciation = "Nineteen.",
            objectName = "Pencils",
            objectEmoji = "✏️",
            sentence = "There are 19 coloring pencils.",
            countingFact = "Nineteen is just one step away from twenty!",
            cardColorHex = 0xFFFF9800
        ),
        NumberItem(
            number = 20,
            spelling = "TWENTY",
            pronunciation = "Twenty.",
            objectName = "Oranges",
            objectEmoji = "🍊",
            sentence = "There are 20 ripe oranges.",
            countingFact = "Twenty is two whole sets of ten!",
            cardColorHex = 0xFFEF6C00
        )
    )
}
