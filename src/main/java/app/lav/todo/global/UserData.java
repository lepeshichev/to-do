package app.lav.todo.global;

import app.lav.todo.features.auth.entity.User;

public class UserData {

    private static final UserData INSTANCE = new UserData();

    private UserData() {

    }

    private static long id = -1;
    private static String username;
    private static String password;
    private static boolean isManager;

    private static boolean isLogged = false;

    public static boolean IsLogged() {
        return isLogged;
    }

    public static long getId() {
        return id;
    }

    public static boolean isIsManager() {
        return isManager;
    }

    public static String getUsername() {
        return username;
    }

    public static UserData getInstance() {
        return INSTANCE;
    }

    public static void setData(User user) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        isManager = user.isManager();
        isLogged = true;
    }

    public static void sighOut() {
        isLogged = false;
    }
}
