package com.bignerdranch.android.geoquiz

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class QuestionUnitTest {

    lateinit var trueAnswerQuestion : Question

    @Before
    fun assign_questions() {
        trueAnswerQuestion = Question(0, true)
    }

    /**
     * 입력된 답과 문제의 답이 일치하면 정답으로 판정한다.
     */
    @Test
    fun checkAnswer_with_same_answer_is_correct_answer(){
        /* Given */
        val trueAnswer = true

        /* When */
        val isCorrectAnswer = trueAnswerQuestion.check(trueAnswer)

        /* Then */
        assertTrue(isCorrectAnswer)
    }

    /**
     * 입력된 답과 문제의 답이 불일치하면 오답으로 판정한다.
     */
    @Test
    fun checkAnswer_with_different_answer_is_wrong_answer(){
        /* Given */
        val falseAnswer = false

        /* When */
        val isCorrectAnswer = trueAnswerQuestion.check(falseAnswer)

        /* Then */
        assertFalse(isCorrectAnswer)
    }
}