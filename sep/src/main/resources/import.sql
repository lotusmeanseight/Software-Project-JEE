INSERT INTO OFFICES (OFFICECODE, CITY, PHONE, ADDRESSLINE1, ADDRESSLINE2, STATE, COUNTRY, POSTALCODE, TERRITORY)
VALUES (4, 'Paris', '+33 14 723 4404', '43 Rue Jouffroy Dabbans', null, null, 'France', 75017, 'EMEA');
INSERT INTO EMPLOYEES (EMPLOYEENUMBER, LASTNAME, FIRSTNAME, EXTENSION, EMAIL, OFFICECODE, REPORTSTO, JOBTITLE)
VALUES (1370, 'Hernandez', 'Gerard', 'x2028', 'ghernande@classicmodelcars.com', 4, 1102, 'Sales Rep');
INSERT INTO CUSTOMERS (CUSTOMERNUMBER,
                       CUSTOMERNAME,
                       CONTACTLASTNAME,
                       CONTACTFIRSTNAME,
                       PHONE,
                       ADDRESSLINE1,
                       ADDRESSLINE2,
                       CITY,
                       STATE,
                       POSTALCODE,
                       COUNTRY,
                       SALESREPEMPLOYEENUMBER,
                       CREDITLIMIT)
VALUES (103,
        'Atelier graphique',
        'Schmitt',
        'Carine',
        '40.32.2555',
        '54, rue Royale',
        null,
        'Nantes',
        null,
        '44000',
        'France',
        1370,
        21000.0);