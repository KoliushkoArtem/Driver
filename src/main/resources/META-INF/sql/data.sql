insert into roles (name, enabled, created_date_time, last_modified_date_time) value ("ROLE_ADMIN", true, localtime, localtime);
insert into roles (name, enabled, created_date_time, last_modified_date_time) value ("ROLE_USER", true, localtime, localtime);

insert into users (first_name, last_name, email, password, enabled, username, created_date_time,
                   last_modified_date_time)
values ("Artem", "Ko=)", "admin@gmail.com", "$2y$12$gqcbdJKT75lfxy3SBLFTpu.O259pUS5rQ8hVX.OPnuf8TlkxvtAWS", true,
        "admin", localtime, localtime);
insert into user_roles (user_id, role_id)
values (1, 1);

insert into users (first_name, last_name, email, password, enabled, username, created_date_time,
                   last_modified_date_time)
values ("Artem", "Ko=)", "user@gmail.com", "$2y$12$f0YMfwHRrVYRVN0H80AybuxitvWzYu28QpCtu1M5ikYcL3.avpBmu", true, "user",
        localtime, localtime);
insert into user_roles (user_id, role_id)
values (2, 2);