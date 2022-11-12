create table team (
    id  bigserial not null,
    name varchar(255),
    primary key (id)
);

create table toggle (id  bigserial not null, is_toggled boolean not null, name varchar(255), operator int4, team_id int8, primary key (id));

create table condition (id  bigserial not null, field varchar(255), operator int4, toggle_id int8, primary key (id));

create table content (condition_id int8 not null, contents varchar(255));
alter table condition add constraint FKcao4an0ust7crue84qtp3ey8u foreign key (toggle_id) references toggle;
alter table content add constraint FKkpwq2b6c3xsa9louent3rvjkf foreign key (condition_id) references condition;
alter table toggle add constraint FKj9ka0fvi9pvsyich2pqktu32w foreign key (team_id) references team;
