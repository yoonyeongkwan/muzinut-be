--insert into "user" (username, password, nickname, activated, declaration, vote, user_id) values ('admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1, 1, 3, 1);
--insert into "user" (username, password, nickname, activated, declaration, vote, user_id) values ('user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 1, 1, 1, 3, 2);

insert into "user" (username, password, nickname, activated, user_id) values ('admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1, 1);
insert into "user" (username, password, nickname, activated, user_id) values ('user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 1, 2);

insert into authority (authority_id, authority_name) values (1, 'ROLE_USER');
insert into authority (authority_id, authority_name) values (2, 'ROLE_ADMIN');

insert into user_authority (user_authority_id, user_id, authority_name) values (1, 1, 'ROLE_USER');
insert into user_authority (user_authority_id, user_id, authority_name) values (2, 1, 'ROLE_ADMIN');
insert into user_authority (user_authority_id, user_id, authority_name) values (3, 2, 'ROLE_USER');