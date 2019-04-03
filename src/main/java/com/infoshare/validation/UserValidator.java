package com.infoshare.validation;

import com.infoshare.domain.User;
import com.infoshare.query.UsersQuery;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@NoArgsConstructor
public class UserValidator {

    private static final int loginLength = 20;
    private static final Pattern loginPattern = Pattern.compile("[$&+,:;=?@#|'<>.-^*()%!\"~]*");
    private static final Pattern namePattern = Pattern.compile("[$&+,:;=?@#|'<>.-^*()%!\"~]*");
    private static final Pattern emailPattern = Pattern.compile("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");

    public static List<String> validationResult = new ArrayList<>();

    public void userValidation(User user) {

        validationResult.clear();
        user.setLogin(validateLogin(user.getLogin()));
        validatePassword(user.getPassword());
        user.setFirstName(validateFirstName(user.getFirstName()));
        user.setLastName(validateLastName(user.getLastName()));
        user.setEmail(validateEmail(user.getEmail()));
    }

    public String validateLogin(String login) {

        if ((login == null || login.trim().isEmpty() || login.contains(" ")) ||
                (login.length() > loginLength || loginPattern.matcher(login).matches())) {
            validationResult.add("Login nie może być pusty ani przekraczać 20 znaków (typu litery i cyfry oraz podkreślenie)");
        }
        return login.trim();
    }

    public void validatePassword(String password) {

        if (password == null || password.length() > loginLength) {
            validationResult.add("Hasło nie może być puste ani przekraczać 20 znaków");
        }
    }

    public String validateFirstName(String firstName) {

        if (firstName == null || firstName.trim().isEmpty() || firstName.length() > loginLength || namePattern.matcher(firstName).matches()) {
            validationResult.add("Imię nie może być puste ani przekraczać 20 znaków (typu litery i cyfry)");
        }
        return firstName.trim();
    }

    public String validateLastName(String lastName) {

        if (lastName == null || lastName.trim().isEmpty() || lastName.length() > loginLength || namePattern.matcher(lastName).matches()) {
            validationResult.add("Nazwisko nie może być puste ani przekraczać 20 znaków (typu litery i cyfry)");
        }
        return lastName;
    }

    public String validateEmail(String email) {

        if (email == null || email.trim().isEmpty() || emailPattern.matcher(email).matches()) {
            validationResult.add("E-mail musi być prawidłowy");
        }
        return email.trim();
    }

    public void checkIsLoginOrEmailExist(String login, String email) throws SQLException, ClassNotFoundException {
        ResultSet rs = UsersQuery.findUserByEmailOrLogin(email, login);
        while (rs.next()) {
            if (!rs.getString("login").isEmpty() && rs.getString("login").equals(login)) {
                validationResult.add("Login jest zajęty");
            }
            if (!rs.getString("email").trim().isEmpty() && rs.getString("email").equals(email)) {
                validationResult.add("E-mail jest zajęty");
            }
        }
        rs.close();
    }
}
