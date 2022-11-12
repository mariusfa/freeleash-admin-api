create table condition (id  bigserial not null, field varchar(255), operator int4, toggle_id int8, primary key (id));
create table content (condition_id int8 not null, contents varchar(255));
create table team (id  bigserial not null, name varchar(255), primary key (id));
create table toggle (id  bigserial not null, is_toggled boolean not null, name varchar(255), operator int4, team_id int8, primary key (id));
alter table condition add constraint FK_toggle foreign key (toggle_id) references toggle;
alter table content add constraint FK_condition foreign key (condition_id) references condition;
alter table toggle add constraint FK_team foreign key (team_id) references team;
