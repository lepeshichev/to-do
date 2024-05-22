package app.lav.todo.features.tasks.data.request;

import lombok.Data;

@Data
public class AddTaskRequestData {
    String title;

    String description;

    int status;

    String startTime;

    String endTime;

    public AddTaskRequestData(String title, String description, int status, String startTime, String endTime) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
