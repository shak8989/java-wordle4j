package ru.yandex.practicum;
import java.util.*;
import java.util.List;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {
    private final Random random = new Random();
    private final List<String> words;
    private final Set<String> wordSet; // для быстрого поиска

    public WordleDictionary(List<String> words) {
        this.words = new ArrayList<>();
        this.wordSet = new HashSet<>();

        for (String word : words) {
            String normalized = normalize(word);
            if (isValidWord(normalized)) {
                this.words.add(normalized);
                this.wordSet.add(normalized);
            }
        }
    }

    //  Проверка есть ли слово
    public boolean contains(String word) {
        return wordSet.contains(normalize(word));
    }

    //  Получить случайное слово
    public String getRandomWord() {
        if (words.isEmpty()) {
            throw new IllegalStateException("Словарь пуст");
        }
        return words.get(random.nextInt(words.size()));
    }

    //  Нормализация
    public static String normalize(String word) {
        return word.toLowerCase().replace("ё", "е").trim();
    }

    //  Проверка валидности
    public static boolean isValidWord(String word) {
        return word != null && word.length() == 5 && word.matches("[а-я]+");
    }

    public List<String> getWords() {
        return Collections.unmodifiableList(words);
    }

    //  СРАВНЕНИЕ СЛОВ
    public static String compare(String guess, String answer) {
        char[] result = {'-', '-', '-', '-', '-'};
        boolean[] used = new boolean[5];

        //  точные совпадения
        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                result[i] = '+';
                used[i] = true;
            }
        }

        //  частичные совпадения
        for (int i = 0; i < 5; i++) {
            if (result[i] == '+') continue;

            for (int j = 0; j < 5; j++) {
                if (!used[j] && guess.charAt(i) == answer.charAt(j)) {
                    result[i] = '^';
                    used[j] = true;
                    break;
                }
            }
        }

        return new String(result);
    }
}