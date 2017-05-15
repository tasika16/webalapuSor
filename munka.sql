/*
Adminok, akik be tudnak l�pni
*/
CREATE TABLE WEBUSER (
  ID INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
  NAME varchar(255) not null,
  EMAIL varchar(255) not null,
  PASSWORD varchar(60), /*bcrypt*/
  ROLE varchar(255) not null
);
/* email:"admin@localhost.dev" // jelsz�:"admin" */
INSERT INTO WEBUSER (NAME,EMAIL,PASSWORD,ROLE) VALUES('Admin','admin@localhost.dev','$2a$06$i3E7wC6aSX4plZp.24HoC.fchLowVA3FUEFLhJ0frKvSyY/Hmpccq','ADMIN');
/* email:"user@localhost.dev" // jelsz�:"admin" */
INSERT INTO WEBUSER (NAME,EMAIL,PASSWORD,ROLE) VALUES('User','user@localhost.dev','$2a$06$i3E7wC6aSX4plZp.24HoC.fchLowVA3FUEFLhJ0frKvSyY/Hmpccq','USER');


/*
V�s�rl�/megb�z� w/e.
*/
CREATE TABLE CUSTOMER (
  ID INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
  NAME varchar(255) not null,
  PHONE_NUMBER varchar(255),
  ADDRESS varchar(1024)
);
INSERT INTO CUSTOMER (NAME,PHONE_NUMBER,ADDRESS) VALUES('Kov�cs J�nos','+36702223333','Budapest, Rig� utca 7.');
INSERT INTO CUSTOMER (NAME,PHONE_NUMBER,ADDRESS) VALUES('Katona �d�m','+36703334444','Veszpr�m, Iskola utca 7.');
INSERT INTO CUSTOMER (NAME,PHONE_NUMBER,ADDRESS) VALUES('Kiss Bence Kriszti�n','+36304584741','Devecser, Orgona utca 7.');
INSERT INTO CUSTOMER (NAME,PHONE_NUMBER,ADDRESS) VALUES('Kov�cs Dzsenifer Aranka','+36905451548','Sz�kesfeh�rv�r, Iskola utca 7.');
INSERT INTO CUSTOMER (NAME,PHONE_NUMBER,ADDRESS) VALUES('Moln�r Viktor','+36802154562','Sz�kesfeh�rv�r, Temze utca 7.');
INSERT INTO CUSTOMER (NAME,PHONE_NUMBER,ADDRESS) VALUES('Ny�ri Kevin','+36102465451','Sz�kesfeh�rv�r, Rig� utca 7.');
INSERT INTO CUSTOMER (NAME,PHONE_NUMBER,ADDRESS) VALUES('Ny�ri Szilvia','+36602451545','Sz�kesfeh�rv�r, Orgona utca 7.');
INSERT INTO CUSTOMER (NAME,PHONE_NUMBER,ADDRESS) VALUES('V�czi Szab� Dominik','+36545151514','G�d�ll�, F� utca 7.');
INSERT INTO CUSTOMER (NAME,PHONE_NUMBER,ADDRESS) VALUES('Vertetics Lili�na','+36989956554','Debrecen, F� utca 7.');
INSERT INTO CUSTOMER (NAME,PHONE_NUMBER,ADDRESS) VALUES('M�sz�ros M�rk �kos','+36545151551','Debrecen, K�zt�r utca 7.');
INSERT INTO CUSTOMER (NAME,PHONE_NUMBER,ADDRESS) VALUES('S�s L�szl�','+36545151522','�rd, D�zsa Gy�rgy utca 7.');
INSERT INTO CUSTOMER (NAME,PHONE_NUMBER,ADDRESS) VALUES('Tak�cs Zolt�n J�zsef','+36845151554','�rd, Ver�b utca 7.');

/*
Projekt
*/
CREATE TABLE PROJECT (
  ID INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
  NAME varchar(255) not null,
  CUSTOMER_ID INT not null REFERENCES CUSTOMER(ID),
  CREATED_AT DATE not null default CURRENT_DATE,
  CLOSED_AT DATE
);
INSERT INTO PROJECT (NAME,CUSTOMER_ID,CREATED_AT,CLOSED_AT) VALUES('Honda S2000',1,'2014-09-27','2014-09-28');
INSERT INTO PROJECT (NAME,CUSTOMER_ID,CREATED_AT,CLOSED_AT) VALUES('Honda NSX',10,'2014-09-27','2014-09-28');
INSERT INTO PROJECT (NAME,CUSTOMER_ID,CREATED_AT,CLOSED_AT) VALUES('Suzuki Swift',7,'2017-04-27',NULL);
INSERT INTO PROJECT (NAME,CUSTOMER_ID,CREATED_AT,CLOSED_AT) VALUES('Tesla',5,'2017-05-14',NULL);

/*
Projekt f�zis, aka r�szfeladat
*/
CREATE TABLE PROJECT_PHASE (
  ID INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
  NAME varchar(512) not null,
  COMPLETED boolean default false,
  ESTIMATED_PRICE INT not null,
  PROJECT_ID INT not null REFERENCES PROJECT(ID)
);
INSERT INTO PROJECT_PHASE (NAME,COMPLETED,ESTIMATED_PRICE,PROJECT_ID) VALUES('F�nyez�s', true, 470000, 1);

INSERT INTO PROJECT_PHASE (NAME,COMPLETED,ESTIMATED_PRICE,PROJECT_ID) VALUES('F�nyez�s', true, 700000, 2);
INSERT INTO PROJECT_PHASE (NAME,COMPLETED,ESTIMATED_PRICE,PROJECT_ID) VALUES('Olajcsere', true, 10000, 2);
INSERT INTO PROJECT_PHASE (NAME,COMPLETED,ESTIMATED_PRICE,PROJECT_ID) VALUES('F�kbet�t csere', true, 250000, 2);

INSERT INTO PROJECT_PHASE (NAME,COMPLETED,ESTIMATED_PRICE,PROJECT_ID) VALUES('Karossz�ria jav�t�s', true, 35000, 3);
INSERT INTO PROJECT_PHASE (NAME,COMPLETED,ESTIMATED_PRICE,PROJECT_ID) VALUES('Olajcsere', false, 7000, 3);
INSERT INTO PROJECT_PHASE (NAME,COMPLETED,ESTIMATED_PRICE,PROJECT_ID) VALUES('V�zpumpa csere', false, 30000, 3);

INSERT INTO PROJECT_PHASE (NAME,COMPLETED,ESTIMATED_PRICE,PROJECT_ID) VALUES('Akkumul�tor csere', false, 750000, 4);
INSERT INTO PROJECT_PHASE (NAME,COMPLETED,ESTIMATED_PRICE,PROJECT_ID) VALUES('Firmware jailbreak', false, 100000, 4);

/*
Fizet�si T�tel
*/
CREATE TABLE PAY_ITEM (
  ID INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
  NAME varchar(255) not null,
  VALUE INT not null,
  CREATED_AT DATE not null default CURRENT_DATE,
  PROJECT_PHASE_ID INT not null REFERENCES PROJECT_PHASE(ID)
);
INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('20mc fekete fest�k 5L', 300999, '2014-09-28', 1);
INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('Fest�s munkad�j', 120000, '2014-09-28', 1);
INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('35mc fekete fest�k 5L', 520500, '2014-09-28', 2);
INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('Fest�s munkad�j', 520500, '2014-09-28', 2);
INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('Turbo-hyperX olaj', 9000, '2014-09-28', 3);
INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('Olajcsere munkad�j', 1000, '2014-09-28', 3);
INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('Speci�lis f�kbet�tek x4', 180000, '2014-09-28', 4);
INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('F�kbet�tek beszerel�se', 600000, '2014-09-28', 4);

INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('Lakatol�s', 30000, '2017-05-14', 5);
INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('Anyagd�j / lemezek', 5000, '2017-05-14', 5);
INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('Sima olaj', 6000, '2017-05-14', 6);
INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('Olajcsere munkad�j', 1000, '2017-05-14', 6);
INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('V�zpumpa �ra', 20000, '2017-05-14', 7);
INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('V�zpumpa beszerel�se', 10000, '2017-05-14', 7);

INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('Akkumul�tor �ra', 700000, '2017-05-14', 8);
INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('Akkumul�tor beszerel�se', 50000, '2017-05-14', 8);
INSERT INTO PAY_ITEM (NAME,VALUE,CREATED_AT,PROJECT_PHASE_ID) VALUES('Firmware jailbreak', 100000, '2017-05-14', 9);

/*
Munk�sok
*/
CREATE TABLE EMPLOYEE_GROUP (
  ID INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
  NAME varchar(255) not null
);
INSERT INTO EMPLOYEE_GROUP (NAME) VALUES('Slick Daddy Club');
INSERT INTO EMPLOYEE_GROUP (NAME) VALUES('Arany csapat');
INSERT INTO EMPLOYEE_GROUP (NAME) VALUES('Ez�st csapat');

CREATE TABLE EMPLOYEE (
  ID INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
    NAME varchar(255) not null,
    PHONE_NUMBER varchar(255) not null,
	HOURLY_WAGE INT not null,
    EMPLOYEE_GROUP_ID int REFERENCES EMPLOYEE_GROUP(ID),
    SOFT_DELETED boolean default false
);
INSERT INTO EMPLOYEE (NAME,PHONE_NUMBER,HOURLY_WAGE,EMPLOYEE_GROUP_ID) VALUES('Nagy Imre', '+36', 1500, 1);
INSERT INTO EMPLOYEE (NAME,PHONE_NUMBER,HOURLY_WAGE,EMPLOYEE_GROUP_ID) VALUES('Kiss Norbert', '+36', 1000, 1);
INSERT INTO EMPLOYEE (NAME,PHONE_NUMBER,HOURLY_WAGE,EMPLOYEE_GROUP_ID) VALUES('Ferenc J�zsef', '+36', 1700, 1);
INSERT INTO EMPLOYEE (NAME,PHONE_NUMBER,HOURLY_WAGE,EMPLOYEE_GROUP_ID) VALUES('Juh�sz �kos', '+36', 1200, 1);
INSERT INTO EMPLOYEE (NAME,PHONE_NUMBER,HOURLY_WAGE,EMPLOYEE_GROUP_ID) VALUES('T�rai Ferenc', '+36', 2000, 1);

CREATE TABLE SKILL (
    ID INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
    NAME varchar(255) not null
);
INSERT INTO SKILL (NAME) VALUES('Karossz�rialakatos');
INSERT INTO SKILL (NAME) VALUES('F�nyez�');
INSERT INTO SKILL (NAME) VALUES('Motortechnikus');
INSERT INTO SKILL (NAME) VALUES('Szoftverfejleszt�');
INSERT INTO SKILL (NAME) VALUES('Tesztel�');

/*
Munk�s-projektf�zis many-to-many
*/
CREATE TABLE EMPLOYEE_TO_PROJECT_PHASE (
    EMPLOYEE_ID int REFERENCES EMPLOYEE(ID),
    PROJECT_PHASE_ID int REFERENCES PROJECT_PHASE(ID),
    CONSTRAINT EMPLOYEE_PROJECT_PKEY PRIMARY KEY (EMPLOYEE_ID, PROJECT_PHASE_ID)
);
INSERT INTO EMPLOYEE_TO_PROJECT_PHASE (EMPLOYEE_ID,PROJECT_PHASE_ID) VALUES(1,1);
INSERT INTO EMPLOYEE_TO_PROJECT_PHASE (EMPLOYEE_ID,PROJECT_PHASE_ID) VALUES(1,2);
INSERT INTO EMPLOYEE_TO_PROJECT_PHASE (EMPLOYEE_ID,PROJECT_PHASE_ID) VALUES(1,3);
INSERT INTO EMPLOYEE_TO_PROJECT_PHASE (EMPLOYEE_ID,PROJECT_PHASE_ID) VALUES(1,4);
INSERT INTO EMPLOYEE_TO_PROJECT_PHASE (EMPLOYEE_ID,PROJECT_PHASE_ID) VALUES(1,6);
INSERT INTO EMPLOYEE_TO_PROJECT_PHASE (EMPLOYEE_ID,PROJECT_PHASE_ID) VALUES(3,2);
INSERT INTO EMPLOYEE_TO_PROJECT_PHASE (EMPLOYEE_ID,PROJECT_PHASE_ID) VALUES(3,3);
INSERT INTO EMPLOYEE_TO_PROJECT_PHASE (EMPLOYEE_ID,PROJECT_PHASE_ID) VALUES(4,2);
INSERT INTO EMPLOYEE_TO_PROJECT_PHASE (EMPLOYEE_ID,PROJECT_PHASE_ID) VALUES(4,5);
INSERT INTO EMPLOYEE_TO_PROJECT_PHASE (EMPLOYEE_ID,PROJECT_PHASE_ID) VALUES(4,8);
INSERT INTO EMPLOYEE_TO_PROJECT_PHASE (EMPLOYEE_ID,PROJECT_PHASE_ID) VALUES(5,4);
INSERT INTO EMPLOYEE_TO_PROJECT_PHASE (EMPLOYEE_ID,PROJECT_PHASE_ID) VALUES(5,5);
INSERT INTO EMPLOYEE_TO_PROJECT_PHASE (EMPLOYEE_ID,PROJECT_PHASE_ID) VALUES(5,6);
INSERT INTO EMPLOYEE_TO_PROJECT_PHASE (EMPLOYEE_ID,PROJECT_PHASE_ID) VALUES(5,8);


/*
Munk�s-skillek many-to-many
*/
CREATE TABLE EMPLOYEE_TO_SKILL (
    EMPLOYEE_ID int REFERENCES EMPLOYEE(ID),
    SKILL_ID int REFERENCES SKILL(ID),
    CONSTRAINT EMPLOYEE_SKILL_PKEY PRIMARY KEY (EMPLOYEE_ID, SKILL_ID)
);
INSERT INTO EMPLOYEE_TO_SKILL (EMPLOYEE_ID,SKILL_ID) VALUES(1,2);
INSERT INTO EMPLOYEE_TO_SKILL (EMPLOYEE_ID,SKILL_ID) VALUES(1,5);
INSERT INTO EMPLOYEE_TO_SKILL (EMPLOYEE_ID,SKILL_ID) VALUES(2,1);
INSERT INTO EMPLOYEE_TO_SKILL (EMPLOYEE_ID,SKILL_ID) VALUES(2,3);
INSERT INTO EMPLOYEE_TO_SKILL (EMPLOYEE_ID,SKILL_ID) VALUES(4,4);
INSERT INTO EMPLOYEE_TO_SKILL (EMPLOYEE_ID,SKILL_ID) VALUES(4,5);
INSERT INTO EMPLOYEE_TO_SKILL (EMPLOYEE_ID,SKILL_ID) VALUES(5,1);
INSERT INTO EMPLOYEE_TO_SKILL (EMPLOYEE_ID,SKILL_ID) VALUES(5,2);
INSERT INTO EMPLOYEE_TO_SKILL (EMPLOYEE_ID,SKILL_ID) VALUES(5,3);
INSERT INTO EMPLOYEE_TO_SKILL (EMPLOYEE_ID,SKILL_ID) VALUES(5,4);
INSERT INTO EMPLOYEE_TO_SKILL (EMPLOYEE_ID,SKILL_ID) VALUES(5,5);
