package com.infoshare.logic.validation;

import com.infoshare.logic.domain.User;
import com.infoshare.logic.repository.UsersRepositoryDao;
import lombok.NoArgsConstructor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@NoArgsConstructor
@Stateless
public class UserValidator {

    @EJB
    private UsersRepositoryDao usersRepository;

    private static final int loginLength = 20;
    private static final Pattern loginPattern = Pattern.compile("[$&+,:;=?@#|'<>.-^*()%!\"~]*");
    private static final Pattern namePattern = Pattern.compile("[$&+,:;=?@#|'<>.-^*()%!\"~]*");
    private static final Pattern emailPattern = Pattern.compile("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");

    public static List<String> validationResult = new ArrayList<>();

    public void userValidation(User user) {
        validationResult.clear();
        userToEditValidation(user);
        validatePassword(user.getPassword());
        checkIsEmailExist(user.getEmail());
        checkIsLoginExist(user.getLogin());
    }

    public void userToEditValidation(User user) {
        user.setLogin(validateLogin(user.getLogin()));
        user.setFirstName(validateFirstName(user.getFirstName()));
        user.setLastName(validateLastName(user.getLastName()));
        user.setEmail(validateEmail(user.getEmail()));
    }

    public void isEmailOrLoginExist(User user, HttpServletRequest req) {
        String login = req.getParameter("login");
        String email = req.getParameter("e-mail");

        if (!user.getLogin().equals(login)) {
            checkIsLoginExist(login);
        }
        if (!user.getEmail().equals(email)) {
            checkIsEmailExist(email);
        }
    }

    private String validateLogin(String login) {

        if ((login == null || login.trim().isEmpty() || login.contains(" ")) ||
                (login.length() > loginLength || loginPattern.matcher(login).matches())) {
            validationResult.add("Login nie może być pusty ani przekraczać 20 znaków (typu litery i cyfry oraz podkreślenie)");
        }
        return login.trim();
    }

    private void validatePassword(String password) {

        if (password == null || password.length() > loginLength) {
            validationResult.add("Hasło nie może być puste ani przekraczać 20 znaków");
        }
    }

    private String validateFirstName(String firstName) {

        if (firstName == null || firstName.trim().isEmpty() || firstName.length() > loginLength || namePattern.matcher(firstName).matches()) {
            validationResult.add("Imię nie może być puste ani przekraczać 20 znaków (typu litery i cyfry)");
        }
        return firstName.trim();
    }

    private String validateLastName(String lastName) {

        if (lastName == null || lastName.trim().isEmpty() || lastName.length() > loginLength || namePattern.matcher(lastName).matches()) {
            validationResult.add("Nazwisko nie może być puste ani przekraczać 20 znaków (typu litery i cyfry)");
        }
        return lastName;
    }

    private String validateEmail(String email) {

        if (email == null || email.trim().isEmpty() || emailPattern.matcher(email).matches()) {
            validationResult.add("E-mail musi być prawidłowy");
        }
        return email.trim();
    }

    private void checkIsLoginExist(String login) {

        List<User> checkLoginList = usersRepository.findUserByLogin(login);

        if (checkLoginList.size() > 0) {
            for (User user : checkLoginList) {
                if (!user.getLogin().isEmpty() && user.getLogin().equals(login))
                    validationResult.add("Login jest zajęty");
            }
        }
    }

    private void checkIsEmailExist(String email) {

        List<User> checkEmailList = usersRepository.findUserByEmail(email);

        if (!checkEmailList.isEmpty()) {
            for (User user : checkEmailList) {
                if (!user.getEmail().isEmpty() && user.getEmail().equals(email))
                    validationResult.add("Email jest zajęty");
            }
        }
    }
}
