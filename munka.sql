/*
Adminok, akik be tudnak lépni
*/
CREATE TABLE "USER" (
  ID INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
  NAME varchar(255) not null,
  EMAIL varchar(255) not null,
  PASSWORD varchar(60) /*bcrypt*/
);

/*
Vásárló/megbízó w/e.
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
Projekt fázis, aka részfeladat
*/
CREATE TABLE PROJECT_PHASE (
  ID INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
  NAME varchar(512) not null,
  VALUE DECIMAL not null
);

/*
Fizetési Tétel
*/
CREATE TABLE PAY_ITEM (
  ID INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
  NAME varchar(255) not null,
  VALUE DECIMAL,
  PROJECT_PHASE_ID INT not null REFERENCES PROJECT_PHASE(ID)
);

/*
Munkások
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
Munkás-projektfázis many-to-many
*/
CREATE TABLE EMPLOYEE_TO_PROJECT_PHASE (
    EMPLOYEE_ID int REFERENCES EMPLOYEE(ID),
    PROJECT_PHASE_ID int REFERENCES PROJECT_PHASE(ID),
    CONSTRAINT EMPLOYEE_PROJECT_PKEY PRIMARY KEY (EMPLOYEE_ID, PROJECT_PHASE_ID)
);

/*
Munkás-skillek many-to-many
*/
CREATE TABLE EMPLOYEE_TO_SKILL (
    EMPLOYEE_ID int REFERENCES EMPLOYEE(ID),
    SKILL_ID int REFERENCES SKILL(ID),
    CONSTRAINT EMPLOYEE_SKILL_PKEY PRIMARY KEY (EMPLOYEE_ID, SKILL_ID)
);