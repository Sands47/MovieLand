package com.voligov.movieland.util.entity;

public class UserCredentials {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isInvalid() {
        return (login == null) || (password == null);
    }

    @Override
    public String toString() {
        return "UserCredentials{" +
                "login='" + login + '\'' +
                '}';
    }
}
