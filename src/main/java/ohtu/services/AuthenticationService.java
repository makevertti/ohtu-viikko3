package ohtu.services;

import ohtu.domain.User;
import ohtu.data_access.UserDao;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (checkLogin(username, password, user)) return true;
        }
        return false;
    }

    private boolean checkLogin(String username, String password, User user) {
        boolean usernameOK = checkUsername(username, user);
        boolean passwordOK = checkPassword(password, user);
        if (usernameOK && passwordOK) {
            return true;
        }
        return false;
    }

    private boolean checkUsername(String username, User user) {
        return user.getUsername().equals(username);
    }

    private boolean checkPassword(String password, User user) {
        return user.getPassword().equals(password);
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }
        if (invalid(username, password)) {
            return false;
        }
        userDao.add(new User(username, password));
        return true;
    }

    private boolean invalid(String username, String password) {
        // validity check of username and password
        if (usernameAndPasswordAreValid(username, password)){
            return false;
        } else {
            return true;
        }
    }

    private boolean usernameAndPasswordAreValid(String username, String password) {
        return username.matches("[a-z]{3,}") && password.length() > 7 && password.matches(".*[^a-zA-Z].*");
    }
}
