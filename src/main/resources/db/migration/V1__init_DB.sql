create table public.usr (
    id int8 not null,
    activation_code varchar(255),
    active boolean not null,
    email varchar(255),
    first_name varchar(255),
    password varchar(255),
    surname varchar(255),
    username varchar(255),
    primary key (id));
create sequence hibernate_sequence start 2 increment 1;
create table post (
    id int8 not null,
    announce varchar(255),
    count int4, name varchar(255),
    text varchar(255),
    user_id int8,
    primary key (id));
create table user_role (
    user_id int8 not null,
    role varchar(255));

alter table if exists post add constraint post_user_fk foreign key (user_id) references public.usr;
alter table if exists user_role add constraint user_role_user_fk foreign key (user_id) references public.usr;