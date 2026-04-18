package ru.yandex.practicum;

public class InvalidInputException extends WordleException {
    public InvalidInputException(String message) {
        super(message);
    }
}