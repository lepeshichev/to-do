import http from "../http-common";

class UserTaskService {

    addTask(data) {
        return http.put(`/task`, data);
    }

    deleteTask(id) {
        return http.delete(`/task/deleteTask/${id}`);
    }

    updateTask(id, data) {
        return http.put(`/task/updateTask/${id}`, data);
    }

    getAllTasks(id) {
        return http.get(`/task/tasks/${id}`);
    }
}