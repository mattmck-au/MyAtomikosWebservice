
CREATE TABLE person (
  person_id SERIAL PRIMARY KEY, 
  first_name VARCHAR (50) NOT NULL, 
  surname VARCHAR (50) NOT NULL 
);

CREATE TABLE address (
  address_id SERIAL PRIMARY KEY,
  address_desc VARCHAR (100) NOT NULL
);

INSERT INTO person (person_id, first_name, surname) VALUES (DEFAULT, 'Joe', 'Bloggs');
INSERT INTO person (person_id, first_name, surname) VALUES (DEFAULT, 'Fred', 'Smith');
INSERT INTO person (person_id, first_name, surname) VALUES (DEFAULT, 'Sally', 'Jones');
INSERT INTO person (person_id, first_name, surname) VALUES (DEFAULT, 'Jane', 'Smith');


INSERT INTO address (address_id, address_desc) VALUES (DEFAULT, '1 Main Street Balcatta');
INSERT INTO address (address_id, address_desc) VALUES (DEFAULT, '254 St Georges Terrace Perth');
