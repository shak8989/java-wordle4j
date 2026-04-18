package ru.yandex.practicum;
import java.io.PrintWriter;
import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */

public class WordleGame {
    private final Random random = new Random();
    private final WordleDictionary dictionary;
    private final PrintWriter log;

    private final String answer;
    private int attempts = 6;

    private final List<String> guesses = new ArrayList<>();
    private final List<String> hints = new ArrayList<>();

    public WordleGame(WordleDictionary dictionary, PrintWriter log) {
        this.dictionary = dictionary;
        this.log = log;
        this.answer = dictionary.getRandomWord();
    }



    //  Сделать ход
    public String makeGuess(String input) throws WordleException {

        if (isGameOver()) {
            throw new WordleException("Игра уже закончена");
        }
        String guess = WordleDictionary.normalize(input);

        validate(guess);

        guesses.add(guess);
        attempts--;

        String result = WordleDictionary.compare(guess, answer);
        hints.add(result);

        log.println("Guess: " + guess + " -> " + result);
        log.flush();



        return result;
    }

    //  Проверка слова
    private void validate(String word) throws WordleException {

        if (word.isEmpty()) {
            throw new InvalidInputException("Пустой ввод");
        }

        if (!WordleDictionary.isValidWord(word)) {
            throw new InvalidInputException("Слово должно быть из 5 букв");
        }
        if (guesses.contains(word)) {
            throw new InvalidInputException("Слово уже вводилось");
        }

        if (!dictionary.contains(word)) {
            throw new WordNotFoundException("Слова нет в словаре");
        }
    }

    //  Подсказка
    public String getHint() {
        List<String> candidates = new ArrayList<>();

        for (String word : dictionary.getWords()) {
            boolean matches = true;

            for (int i = 0; i < guesses.size(); i++) {
                String expected = hints.get(i);
                String actual = WordleDictionary.compare(guesses.get(i), word);

                if (!expected.equals(actual)) {
                    matches = false;
                    break;
                }
            }

            if (matches) {
                candidates.add(word);
            }
        }

        if (candidates.isEmpty()) {
            return "Нет вариантов";
        }

        // 👉 возвращаем случайное слово из подходящих
        return candidates.get(random.nextInt(candidates.size()));
    }

    // Состояние игры
    public boolean isGameOver() {
        return attempts <= 0 || isWin();
    }

    public boolean isWin() {
        return !guesses.isEmpty() &&guesses.get(guesses.size() - 1).equals(answer);
    }

    public String getAnswer() {
        return answer;
    }

    public int getAttempts() {
        return attempts;
    }
}
