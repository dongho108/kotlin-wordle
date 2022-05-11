package wordle.domain

import io.kotest.assertions.throwables.shouldThrow

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import wordle.domain.Tile.*

import java.time.LocalDate

internal class GameTest {

    @Test
    @DisplayName("플레이의 단어가 words에 포함되어있지 않다면 예외를 던져야 한다.")
    fun containsWord() {
        //given
        val wordsResponse = WordsResponse("words.txt")
        val words = Words(wordsResponse.words)
        val game = Game(words, LocalDate.of(2021, 6, 19))

        //when

        //then
        shouldThrow<IllegalArgumentException> { game.matchResult(Word("xxxxx")) }
    }

    @ParameterizedTest
    @MethodSource("validParameters")
    @DisplayName("플레이어의 답과 정답을 매칭한다.")
    fun matchAnswer(word: Word, expectedTiles: Tiles) {
        //given
        val wordsResponse = WordsResponse("words.txt")
        val words = Words(wordsResponse.words)
        val game = Game(words, LocalDate.of(2021, 6, 19))

        //when
        val actualTiles = game.matchResult(word)

        //then
        assertThat(actualTiles).isEqualTo(expectedTiles)
    }

    companion object {
        @JvmStatic
        fun validParameters() = listOf(
            Arguments.of(Word("cigar"), Tiles(listOf(GREEN, GREEN, GREEN, GREEN, GREEN))),
            Arguments.of(Word("scare"), Tiles(listOf(GRAY, YELLOW, YELLOW, YELLOW, GRAY))),
            Arguments.of(Word("solar"), Tiles(listOf(GRAY, GRAY, GRAY, GREEN, GREEN))),
            Arguments.of(Word("tiger"), Tiles(listOf(GRAY, GREEN, GREEN, GRAY, GREEN))),
            Arguments.of(Word("query"), Tiles(listOf(GRAY, GRAY, GRAY, YELLOW, GRAY)))
        )
    }
}


