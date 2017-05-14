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

/*
Munk�sok
*/
CREATE TABLE EMPLOYEE_GROUP (
  ID INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
  NAME varchar(255) not null
);

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

CREATE TABLE SKILL (
    ID INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
    NAME varchar(255) not null
);

/*
Munk�s-projektf�zis many-to-many
*/
CREATE TABLE EMPLOYEE_TO_PROJECT_PHASE (
    EMPLOYEE_ID int REFERENCES EMPLOYEE(ID),
    PROJECT_PHASE_ID int REFERENCES PROJECT_PHASE(ID),
    CONSTRAINT EMPLOYEE_PROJECT_PKEY PRIMARY KEY (EMPLOYEE_ID, PROJECT_PHASE_ID)
);

/*
Munk�s-skillek many-to-many
*/
CREATE TABLE EMPLOYEE_TO_SKILL (
    EMPLOYEE_ID int REFERENCES EMPLOYEE(ID),
    SKILL_ID int REFERENCES SKILL(ID),
    CONSTRAINT EMPLOYEE_SKILL_PKEY PRIMARY KEY (EMPLOYEE_ID, SKILL_ID)
);