package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */

public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter("log.txt");
             Scanner scanner = new Scanner(System.in)) {

            //  загрузчик словаря
            WordleDictionaryLoader loader = new WordleDictionaryLoader(log);

            // загрузка словаря
            WordleDictionary dictionary = loader.load("words_ru.txt");

            //  создание игры
            WordleGame game = new WordleGame(dictionary, log);

            System.out.println("Игра началась! Угадайте слово из 5 букв.");

            //  игровой цикл
            while (!game.isGameOver()) {

                System.out.print("Введите слово: ");
                String input = scanner.nextLine();

                // подсказка если пустая строка
                if (input.isEmpty()) {
                    String hint = game.getHint();
                    System.out.println("Подсказка: " + hint);
                    continue;
                }

                try {
                    String result = game.makeGuess(input);
                    System.out.println(result + " | Осталось: " + game.getAttempts());
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }

            //  результат
            if (game.isWin()) {
                System.out.println("Вы выиграли!");
            } else {
                System.out.println("Вы проиграли!");
            }

            System.out.println("Загаданное слово: " + game.getAnswer());

        } catch (Exception e) {
            System.out.println("Критическая ошибка: " + e.getMessage());
            e.printStackTrace();

        } // в лог можно тоже писать

    }
}
