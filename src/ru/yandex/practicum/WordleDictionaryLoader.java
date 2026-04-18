package ru.yandex.practicum;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */

public class WordleDictionaryLoader {

    private final PrintWriter log;

    public WordleDictionaryLoader(PrintWriter log) {
        this.log = log;
    }

    public WordleDictionary load(String fileName) throws WordleException {

        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileName, StandardCharsets.UTF_8))) {

            String line;

            while ((line = reader.readLine()) != null) {
                words.add(line);
            }

        } catch (IOException e) {
            log.println("Ошибка чтения файла: " + fileName);
            log.flush();
            throw new WordleException("Ошибка чтения файла: " + fileName);
        }


        if (words.isEmpty()) {
            log.println("Словарь пуст!");
            throw new WordleException("Словарь пуст");
        }

        log.println("Словарь загружен. Количество слов: " + words.size());
        log.flush();

        return new WordleDictionary(words);
    }
}