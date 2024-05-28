
create table if not exists users (
    id int primary key,
    username text,
    password text,
    isManager boolean
);

create table if not exists tasks (
    id int primary key,
    title text,
    description text,
    status int,
    start_Time date,
    end_Time date,
    main_User_Id int,
    constraint fk_owner foreign key (main_User_Id) references users(id)
);

create table if not exists task_assignments (
    userId int,
    taskId int,
    constraint fk_user foreign key (userId) references users(id),
    constraint fk_task foreign key (taskId) references tasks(id),
    primary key (userId, taskId)
);

create table if not exists archived_tasks (
    id int primary key,
    title text,
    description text,
    status int,
    start_Time date,
    end_Time date,
    main_User_Id int,
    constraint fk_main_user_archived foreign key (main_User_Id) references users(id)
);