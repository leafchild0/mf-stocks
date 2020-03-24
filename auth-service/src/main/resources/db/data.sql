insert into users values (1, 'user1', 'user', 'test@user.com', true);
insert into users values (2, 'user2', '$2a$10$OZThCVKbYkTPCxtw5kMtPO9JbiBbxX64oPySasLZpipvFNVWrW7di', 'test@user.com', true);

insert into roles values (1, 'ROLE_USER');
insert into roles values (2, 'ROLE_ADMIN');

insert into user_roles values (1, 1);
insert into user_roles values (2, 2);