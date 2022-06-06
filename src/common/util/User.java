package common.util;

import serverModule.util.DataHasher;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private final String login;
    private final String password;

    public User(String login, String password) {
        this.login = login;
        this.password = DataHasher.hash(password);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof User) {
            User userObj = (User) o;
            return login.equals(userObj.getLogin()) && DataHasher.hash(password).equals(userObj.getPassword());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    @Override
    public String toString() {
        return login + ":" + password;
    }
}
