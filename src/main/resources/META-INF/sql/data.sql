insert into roles (name, enabled, created_date_time, last_modified_date_time) value ("ROLE_ADMIN", true, localtime, localtime);
insert into roles (name, enabled, created_date_time, last_modified_date_time) value ("ROLE_USER", true, localtime, localtime);

insert into users (first_name, last_name, email, password, enabled, username, created_date_time, last_modified_date_time) values ("Artem", "Ko=)", "admin@gmail.com", "$2a$10$ybkcBrDI9rv/PchX4Yfdg.GIJVJYTbVQFxXGiKGIM1HLs/qgVB5oi", true, "admin", localtime, localtime);
insert into user_roles (user_id, role_id) VALUES (1, 1);

insert into users (first_name, last_name, email, password, enabled, username, created_date_time, last_modified_date_time) values ("Artem", "Ko=)", "user@gmail.com", "$2a$10$ybkcBrDI9rv/PchX4Yfdg.GIJVJYTbVQFxXGiKGIM1HLs/qgVB5oi", true, "user", localtime, localtime);
insert into user_roles (user_id, role_id) VALUES (2, 2);
;