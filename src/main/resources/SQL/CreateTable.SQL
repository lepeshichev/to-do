
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
    status text,
    startTime text,
    endTime text,
    mainUserId int,
    constraint employees
        foreign key(mainUserId)
            references users(id)
);

create table if not exists archive (

)