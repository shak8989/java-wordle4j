package ru.yandex.practicum;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.PrintWriter;
import java.util.List;

class WordleTest{
@Test
void testCompareExactMatch() {
    String result = WordleDictionary.compare("слово", "слово");
    assertEquals("+++++", result);
}

@Test
void testComparePartialMatch() {
    String result = WordleDictionary.compare("домик", "модик");
    assertEquals("^+^++", result);
}

@Test
void testInvalidWord() {
    WordleDictionary dict = new WordleDictionary(List.of("слово"));
    WordleGame game = new WordleGame(dict, new PrintWriter(System.out));

    assertThrows(WordleException.class, () -> game.makeGuess("abc"));
}
}