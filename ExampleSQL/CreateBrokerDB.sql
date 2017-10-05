create table BROKER
(BrID int, 
BrName varchar(10), 
BrPrice int);

insert into BROKER(BrId, BrName, BrPrice) values (1, 'Thomas', 180);
insert into BROKER(BrId, BrName, BrPrice) values (2, 'Charles', 190); 
insert into BROKER(BrId, BrName, BrPrice) values (3, 'Jack', 150);
insert into BROKER(BrId, BrName, BrPrice) values (4, 'Nora', 160);
insert into BROKER(BrId, BrName, BrPrice) values (5, 'Mila', 170);
insert into BROKER(BrId, BrName, BrPrice) values (6, 'kim', 200);
insert into BROKER(BrId, BrName, BrPrice) values (7, 'art', 180);
insert into BROKER(BrId, BrName, BrPrice) values (8, 'pat', 190);
insert into BROKER(BrId, BrName, BrPrice) values (9, 'lee', 130);
insert into BROKER(BrId, BrName, BrPrice) values (10, 'max', 160);
insert into BROKER(BrId, BrName, BrPrice) values (11, 'sue', 155);
insert into BROKER(BrId, BrName, BrPrice) values (12, 'bob', 180);
insert into BROKER(BrId, BrName, BrPrice) values (13, 'kim', 180);
insert into BROKER(BrId, BrName, BrPrice) values (14, 'mi', 185);
insert into BROKER(BrId, BrName, BrPrice) values (15, 'Jia', 190);
insert into BROKER(BrId, BrName, BrPrice) values (16, 'yuchen', 120);
insert into BROKER(BrId, BrName, BrPrice) values (17, 'zhoukai', 140);
insert into BROKER(BrId, BrName, BrPrice) values (18, 'yanyan', 150);
insert into BROKER(BrId, BrName, BrPrice) values (19, 'yifan', 155);
insert into BROKER(BrId, BrName, BrPrice) values (20, 'qingyu', 150);
insert into BROKER(BrId, BrName, BrPrice) values (21, 'elke', 170);
insert into BROKER(BrId, BrName, BrPrice) values (22, 'erika',140);
insert into BROKER(BrId, BrName, BrPrice) values (23, 'tj', 125);

create table CLIENT
(ClID int, 
ClName varchar(8), 
BrokerID int, 
ClNetWorth int);

insert into CLIENT(ClId, ClName, BrokerID, ClNetWorth) values (10, 'Alexis', 1, 10000);
insert into CLIENT(ClId, ClName, BrokerID, ClNetWorth) values (11, 'Kevin', 3, 50000);
insert into CLIENT(ClId, ClName, BrokerID, ClNetWorth) values (12, 'Gavin', 2, 2000);
insert into CLIENT(ClId, ClName, BrokerID, ClNetWorth) values (13, 'Bella', 4, 3000); 
insert into CLIENT(ClId, ClName, BrokerID, ClNetWorth) values (14, 'Jose', 5, 2500);
insert into CLIENT(ClId, ClName, BrokerID, ClNetWorth) values (15, 'Lincoln', 3, 20000); 





