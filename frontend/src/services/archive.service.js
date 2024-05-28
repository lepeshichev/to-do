import http from "../http-common";

class UserArchiveService {

    addTaskToArchive(id) {
        return http.put(`/archive/moveTaskToArchive/${id}`);
    }

    getArchivedTasks() {
        return http.get(`/archive/archivedTasks`);
    }

    clearArchive() {
        return http.delete(`/archive/clearArchive`);
    }
}