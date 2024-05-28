import http from "../http-common";

class UserAuthService {

    signUp(data) {
        return http.put(`/auth/signUp`, data);
    }

    signIn(data) {
        return http.put(`/auth/signIp`, data);
    }

    getInfo() {
        return http.get(`/auth/info`);
    }

    signOut() {
        return http.get(`/auth/signOut`, data);
    }
}