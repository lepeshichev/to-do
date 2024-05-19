package app.lav.todo.features.auth.data.request;

public class AuthRequestData {
    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AuthRequestData(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
