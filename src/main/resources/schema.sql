drop table if exists test.students;
drop table if exists test.groups;

create schema test;

create table test.groups(
    id serial not null,
    name varchar,
    constraint group_pk PRIMARY KEY (id)
);

create table test.students(
    id serial not null,
    name varchar,
    group_id integer not null,
    constraint student_pk primary key (id),
    constraint group_fk foreign key (group_id) references test.groups(id)
);