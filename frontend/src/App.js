import React, { useState, useEffect } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/auth.service";

import Login from "./components/Login";
import Register from "./components/Register";
import Tasks from "./components/Tasks";
import User from "./user-types/User";
import Manager from "./user-types/Manager";

// import AuthVerify from "./common/AuthVerify";
import EventBus from "./common/EventBus";

const App = () => {
    const [getTasksManager, setGetTasksManager] = useState(false);
    const [currentUser, setCurrentUser] = useState(undefined);

    useEffect(() => {
        const user = AuthService.getInfo();

        if (user) {
            setCurrentUser(User);
            setGetTasksManager(user.includes("Manager"));
        }

        EventBus.on("logout", () => {
            AuthService.signOut();
            setCurrentUser(undefined);
        });

        return () => {
            EventBus.remove("logout");
        };
    }, []);

    return (
        <div>
            <nav className="navbar">
                <div className="navbar">
                    <li className="nav-item">
                        <Link to={"/task"} className="nav-link">
                            Мой день
                        </Link>
                    </li>

                    {getTasksManager && (
                        <li className="nav-item">
                            <Link to={"/allTasks"} className="nav-link">
                                Все задачи
                            </Link>
                        </li>
                    )}

                    {currentUser && (
                        <li className="nav-item">
                            <Link to={"/archive"} className="nav-link">
                                Архив
                            </Link>
                        </li>
                    )}
                </div>

                {currentUser ? (
                    <div className="navbar">
                        <li className="nav-item">
                            <Link to={"/profile"} className="nav-link">
                                {currentUser.username}
                            </Link>
                        </li>
                        <li className="nav-item">
                            <a href="/auth/signOut" className="nav-link" onClick={AuthService.signOut()}>
                                Выход
                            </a>
                        </li>
                    </div>
                ) : (
                    <div className="navbar">
                        <li className="nav-item">
                            <Link to={"/auth/signIn"} className="nav-link">
                                Вход
                            </Link>
                        </li>

                        <li className="nav-item">
                            <Link to={"/auth/signUp"} className="nav-link">
                                Регистрация
                            </Link>
                        </li>
                    </div>
                )}
            </nav>

            <div className="routes">
                <Routes>
                    <Route exact path={"/"} element={<Tasks />} />
                    <Route exact path={"/tasks"} element={<Tasks />} />
                    <Route exact path="/signIn" element={<Login />} />
                    <Route exact path="/signOut" element={<Register />} />
                    <Route path="/user" element={<User />} />
                    <Route path="/manager" element={<Manager />} />
                </Routes>
            </div>

            {/* <AuthVerify logOut={logOut}/> */}
        </div>
    );
};

export default App;