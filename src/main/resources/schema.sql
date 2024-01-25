create table TB_USER (
  ID int not null AUTO_INCREMENT,
  FIRST_NAME varchar(200) not null,
  LAST_NAME varchar(200) not null,
  USERNAME varchar(100) not null,
  PASSWORD varchar(100) not null,
  PRIMARY KEY ( ID )
);

INSERT INTO TB_USER(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD) VALUES ('John', 'Doe', 'admin', '$2a$10$NpzbGPkB96SVU1JFGgIWdetMBfsghbaTOB5r0wF3E9Dl8jIrxJnx2');

create table TB_SESSION (
  ID int not null AUTO_INCREMENT,
  INITIATED_USER_ID int not null,
  SESSION_ID varchar(100) not null,
  IS_ACTIVE BIT not null,
  PRIMARY KEY ( ID )
);

create table TB_RESTAURANT (
  ID int not null AUTO_INCREMENT,
  SESSION_ID int not null,
  PERSON_NAME varchar(200) not null,
  RESTAURANT_NAME varchar(100) not null,
  RESTAURANT_LOCATION varchar(100) not null,
  IS_SELECTED BIT not null,
  PRIMARY KEY ( ID )
);