drop table schedule;
drop table sections;
drop table enrollment;
drop table preferences;
drop table rooms;
drop table department;

drop table login;
drop table eventlog;
drop table distances;

create table login (userid varchar(8) NOT NULL, 
                    password varchar(30) NOT NULL, 
                    usertype char(1) NOT NULL, 
                    primary key (userid));

create table distances (campus varchar(5) NOT NULL, 
                        northdistance double NOT NULL, 
                        southdistance double NOT NULL, 
                        eastdistance double NOT NULL, 
                        westdistance double NOT NULL, 
                        primary key (campus));


create table eventlog (logid bigint NOT NULL, 
                       datetime timestamp NOT NULL, 
                       logitem varchar(255) NOT NULL, 
                       primary key (logid));

create table enrollment (department varchar(10) NOT NULL, 
                        coursenumber bigint NOT NULL,
                        numberenrolled smallint NOT NULL, 
                        primary key (department, coursenumber));

create table preferences (instructor varchar(30) NOT NULL, 
                          userid varchar(8) NOT NULL,
                          department varchar(10) NOT NULL, 
                          sections smallint NOT NULL, 
                          northcampus varchar(3), 
                          southcampus varchar(3), 
                          westcampus varchar(3), 
                          eastcampus varchar(3), 
                          weekend varchar(3), 
                          primary key (instructor, sections),
                          foreign key (userid) references login(userid));


create table rooms (department varchar(10) NOT NULL,
                    room smallint NOT NULL, 
                    campus varchar(5) NOT NULL, 
                    capacity smallint NOT NULL, 
                    mediaavailable boolean, 
                    primary key (department, room, campus));

create table sections (callnumber bigint NOT NULL, 
                       department varchar(10) NOT NULL, 
                       coursenumber bigint NOT NULL, 
                       days char(7) NOT NULL, 
                       starttime time not null, 
                       endtime time not null, 
                       mediarequired varchar(3) not null,
                       primary key (callnumber));

create table schedule (callnumber bigint NOT NULL, 
                       coursenumber bigint NOT NULL, 
                       department varchar(10) NOT NULL,
                       days char(7) NOT NULL, 
                       startime time NOT NULL, 
                       endtime time NOT NULL, 
                       instructor varchar(30) NOT NULL, 
                       room smallint NOT NULL, 
                       building varchar(10) NOT NULL, 
                       primary key (callnumber), 
                       constraint schedule_section
                       foreign key (callnumber) references sections(callnumber));

