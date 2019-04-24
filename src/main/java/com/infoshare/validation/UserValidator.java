package com.infoshare.validation;

import com.infoshare.domain.User;
import com.infoshare.repository.UsersRepositoryDao;
import lombok.NoArgsConstructor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
        userToEditValidation(user);
        validationResult.clear();
        validatePassword(user.getPassword());
    }

    public void userToEditValidation(User user) {
        validationResult.clear();
        user.setLogin(validateLogin(user.getLogin()));
        user.setFirstName(validateFirstName(user.getFirstName()));
        user.setLastName(validateLastName(user.getLastName()));
        user.setEmail(validateEmail(user.getEmail()));
        checkIsLoginOrEmailExist(user.getEmail(), user.getLogin());
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

    public void checkIsLoginOrEmailExist(String email, String login) {

        List<User> checkLoginAndEmailList = usersRepository.findUserByEmailOrLogin(email, login);

        if (checkLoginAndEmailList.size() > 0) {

            for (User user : checkLoginAndEmailList) {
                if (!user.getLogin().isEmpty() && user.getLogin().equals(login))
                    validationResult.add("Login jest zajęty");
                if (!user.getEmail().isEmpty() && user.getEmail().equals(email))
                    validationResult.add("Email jest zajęty");
            }
        }
    }
}
