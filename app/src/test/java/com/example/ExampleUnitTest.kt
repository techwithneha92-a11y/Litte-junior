package com.example

import com.example.data.AlphabetRepository
import com.example.data.GeneralKnowledgeRepository
import com.example.data.MathRepository
import com.example.data.NumbersRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun testAlphabetCompleteness() {
    val list = AlphabetRepository.alphabetList
    assertEquals(26, list.size)
    assertEquals('A', list.first().letter)
    assertEquals('Z', list.last().letter)
    assertTrue(list.all { it.word.isNotEmpty() && it.sentence1.isNotEmpty() && it.funFact.isNotEmpty() })
  }

  @Test
  fun testNumbersCompleteness() {
    val list = NumbersRepository.numberList
    assertEquals(20, list.size)
    assertEquals(1, list.first().number)
    assertEquals(20, list.last().number)
    assertTrue(list.all { it.spelling.isNotEmpty() && it.sentence.isNotEmpty() && it.objectEmoji.isNotEmpty() })
  }

  @Test
  fun testMathContent() {
    val addition = MathRepository.additionLessons
    val subtraction = MathRepository.subtractionLessons
    assertTrue(addition.isNotEmpty())
    assertTrue(subtraction.isNotEmpty())
    assertTrue(addition.all { it.num1 + it.num2 == it.result })
    assertTrue(subtraction.all { it.num1 - it.num2 == it.result && it.result >= 0 })

    val randomAdd = MathRepository.generateRandomAddition()
    assertEquals(randomAdd.num1 + randomAdd.num2, randomAdd.result)

    val randomSub = MathRepository.generateRandomSubtraction()
    assertEquals(randomSub.num1 - randomSub.num2, randomSub.result)
  }

  @Test
  fun testGkCategories() {
    val cats = GeneralKnowledgeRepository.categories
    assertTrue(cats.size >= 9)
    assertTrue(cats.all { it.items.isNotEmpty() })
    val scienceQ = GeneralKnowledgeRepository.scienceQuestions
    assertEquals(7, scienceQ.size)
    assertTrue(scienceQ.all { it.options.contains(it.answer) })
  }
}
