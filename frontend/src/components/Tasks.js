import React, { useState, useEffect } from "react";

import UserTaskService from "../services/user.service";

const Tasks = () => {
    const [content, setContent] = useState("");

    useEffect(() => {
        UserTaskService.getAllTasks(id).then(
            (response) => {
                setContent(response.data);
            },
            (error) => {
                const _content =
                    (error.response && error.response.data) ||
                    error.message ||
                    error.toString();
                setContent(_content);
            }
        );
    }, []);

    return (
        <div className="3">
            <header className="3">
                <h3>{content}</h3>
            </header>
        </div>
    );
};

export default Tasks;