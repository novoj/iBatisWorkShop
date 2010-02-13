CREATE TABLE user (
  id INT NOT NULL IDENTITY,
  login VARCHAR(80) NOT NULL,
  firstName VARCHAR(120) NULL,
  lastName VARCHAR(120) NULL,
  birthDate DATE NULL,
  state SMALLINT NULL,
  type SMALLINT NOT NULL,
  employeeNumber VARCHAR(5),
  companyName VARCHAR(80),
  companyIdNumber VARCHAR(11),
  CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE role (
  id INT NOT NULL IDENTITY,
  systemName VARCHAR(80) NOT NULL,
  humanName VARCHAR(120) NULL,
  CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE userRole (
  id INT NOT NULL IDENTITY,
  idUser INT NOT NULL,
  idRole INT NOT NULL,
  CONSTRAINT pk_userRole PRIMARY KEY (id),
  CONSTRAINT fk_userRole_user FOREIGN KEY (idUser)
  REFERENCES user (id),
  CONSTRAINT fk_userRole_role FOREIGN KEY (idRole)
  REFERENCES role (id)
);

CREATE TABLE productGroup (
  id INT NOT NULL IDENTITY,
  name varchar(50) NOT NULL,
  groupType varchar(30) NOT NULL,
  CONSTRAINT pk_productGroup PRIMARY KEY (id)
);

CREATE TABLE tag (
  id INT NOT NULL IDENTITY,
  name varchar(50) NOT NULL,
  CONSTRAINT pk_tag PRIMARY KEY (id)
);

CREATE TABLE product (
  id INT NOT NULL IDENTITY,
  name varchar(100) NOT NULL,
  idGroup INT NOT NULL,
  CONSTRAINT pk_product PRIMARY KEY (id),
  CONSTRAINT fk_product_group FOREIGN KEY (idGroup)
  REFERENCES productGroup (id)
);

CREATE TABLE productTag (
  id INT NOT NULL IDENTITY,
  idProduct INT NOT NULL,
  idTag INT NOT NULL,
  CONSTRAINT pk_productTag PRIMARY KEY (id),
  CONSTRAINT fk_productTag_tag FOREIGN KEY (idTag)
  REFERENCES tag (id),
  CONSTRAINT fk_productTag_product FOREIGN KEY (idProduct)
  REFERENCES product (id)
);