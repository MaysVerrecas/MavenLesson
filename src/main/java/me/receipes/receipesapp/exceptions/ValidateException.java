package me.receipes.receipesapp.exceptions;

public class ValidateException extends RuntimeException {
    public ValidateException(String message) {
        super("Ошибка валидации " + message);
    }
}
