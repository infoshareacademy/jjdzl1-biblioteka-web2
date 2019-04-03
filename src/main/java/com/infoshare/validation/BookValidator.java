package com.infoshare.validation;

import com.infoshare.domain.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookValidator {

    private static final int titleLength = 80;
    private static final int authorsNameLength = 50;

    public static List<String> validationResult = new ArrayList<>();

    public List<String> bookValidation(Book book) {

        validationResult.clear();
        book.setTitle(validateTitle(book.getTitle()));
        book.setAuthorFirstName(validateAutorName(book.getAuthorFirstName(), "Nazwisko autora: "));
        book.setAuthorLastName(validateAutorName(book.getAuthorLastName(), "Imię autora: "));
        validateIsbn(book.getIsbn());
        validateRelaseDate(book.getRelaseDate());

        return validationResult;
    }

    public String validateTitle(String title) {

        if (title == null || title.trim().isEmpty() || title.length() > titleLength) {
            validationResult.add("Tytuł nie może być pusty ani przekraczać 80 znaków");
        }
        return title.trim();
    }

    public String validateAutorName(String name, String fieldName) {

        if (name == null || name.isEmpty() || name.length() > authorsNameLength) {
            validationResult.add(fieldName + "pole nie może być pusty ani przekraczać 50 znaków");
        }
        return name.trim();
    }

    public void validateIsbn(String isbn) {
        String letterRegex = "[a-zA-Z]+";
        if (isbn == null || isbn.isEmpty() || isbn.matches(letterRegex) || !(isbn.length() == 10 || isbn.length() == 13)) {
            validationResult.add("Prawidłowy numer ISBN to 10 lub 13 cyfr");
        }
    }

    public void validateRelaseDate(Integer date) {
        int currentYear = LocalDate.now().getYear();
        if (date == 0 || date > currentYear || date <= 1399) {
            validationResult.add("Rok wydania nie może być pusty, mniejszy niż 1400 lub większy niż " + currentYear + " rok");
        }
    }
}
