--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: classicmodels; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE classicmodels WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'de_DE.UTF-8' LC_CTYPE = 'de_DE.UTF-8';


ALTER DATABASE classicmodels OWNER TO postgres;

\connect classicmodels

CREATE ROLE sep with password 'sep' LOGIN;
ALTER ROLE SEP SUPERUSER;

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: customers; Type: TABLE; Schema: public; Owner: sep; Tablespace: 
--

CREATE TABLE customers (
    customernumber integer NOT NULL,
    customername character varying(50) NOT NULL,
    contactlastname character varying(50) NOT NULL,
    contactfirstname character varying(50) NOT NULL,
    phone character varying(50) NOT NULL,
    addressline1 character varying(50) NOT NULL,
    addressline2 character varying(50),
    city character varying(50) NOT NULL,
    state character varying(50),
    postalcode character varying(15),
    country character varying(50) NOT NULL,
    salesrepemployeenumber integer,
    creditlimit numeric NOT NULL
);


ALTER TABLE customers OWNER TO sep;

--
-- Name: employees; Type: TABLE; Schema: public; Owner: sep; Tablespace: 
--

CREATE TABLE employees (
    employeenumber integer NOT NULL,
    lastname character varying(50) NOT NULL,
    firstname character varying(50) NOT NULL,
    extension character varying(10) NOT NULL,
    email character varying(100) NOT NULL,
    officecode integer NOT NULL,
    reportsto integer,
    jobtitle character varying(50) NOT NULL
);


ALTER TABLE employees OWNER TO sep;

--
-- Name: offices; Type: TABLE; Schema: public; Owner: sep; Tablespace: 
--

CREATE TABLE offices (
    officecode integer NOT NULL,
    city character varying(50) NOT NULL,
    phone character varying(50) NOT NULL,
    addressline1 character varying(50) NOT NULL,
    addressline2 character varying(50),
    state character varying(50),
    country character varying(50) NOT NULL,
    postalcode character varying(15) NOT NULL,
    territory character varying(10) NOT NULL
);


ALTER TABLE offices OWNER TO sep;

--
-- Name: orderdetails; Type: TABLE; Schema: public; Owner: sep; Tablespace: 
--

CREATE TABLE orderdetails (
    ordernumber integer NOT NULL,
    productcode character varying(15) NOT NULL,
    quantityordered integer NOT NULL,
    priceeach numeric(6,2) NOT NULL,
    orderlinenumber integer NOT NULL
);


ALTER TABLE orderdetails OWNER TO sep;

--
-- Name: orders; Type: TABLE; Schema: public; Owner: sep; Tablespace: 
--

CREATE TABLE orders (
    ordernumber integer NOT NULL,
    orderdate date NOT NULL,
    requireddate date NOT NULL,
    shippeddate date,
    status character varying(15) NOT NULL,
    comments character varying(1000),
    customernumber integer NOT NULL
);


ALTER TABLE orders OWNER TO sep;

--
-- Name: payments; Type: TABLE; Schema: public; Owner: sep; Tablespace: 
--

CREATE TABLE payments (
    customernumber integer NOT NULL,
    checknumber character varying(50) NOT NULL,
    paymentdate date NOT NULL,
    amount numeric(10,2) NOT NULL
);


ALTER TABLE payments OWNER TO sep;

--
-- Name: productlines; Type: TABLE; Schema: public; Owner: sep; Tablespace: 
--

CREATE TABLE productlines (
    productline character varying(50) NOT NULL,
    textdescription character varying(4000),
    htmldescription character varying(500),
    image bytea
);


ALTER TABLE productlines OWNER TO sep;

--
-- Name: products; Type: TABLE; Schema: public; Owner: sep; Tablespace: 
--

CREATE TABLE products (
    productcode character varying(15) NOT NULL,
    productname character varying(70) NOT NULL,
    productline character varying(50) NOT NULL,
    productscale character varying(10) NOT NULL,
    productvendor character varying(50) NOT NULL,
    productdescription character varying(1000) NOT NULL,
    quantityinstock integer NOT NULL,
    buyprice numeric(6,2) NOT NULL,
    msrp numeric(6,2) NOT NULL
);


ALTER TABLE products OWNER TO sep;

--
-- Name: customers_pkey; Type: CONSTRAINT; Schema: public; Owner: sep; Tablespace: 
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (customernumber);


--
-- Name: employees_pkey; Type: CONSTRAINT; Schema: public; Owner: sep; Tablespace: 
--

ALTER TABLE ONLY employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (employeenumber);


--
-- Name: offices_pkey; Type: CONSTRAINT; Schema: public; Owner: sep; Tablespace: 
--

ALTER TABLE ONLY offices
    ADD CONSTRAINT offices_pkey PRIMARY KEY (officecode);


--
-- Name: orderdetails_pkey; Type: CONSTRAINT; Schema: public; Owner: sep; Tablespace: 
--

ALTER TABLE ONLY orderdetails
    ADD CONSTRAINT orderdetails_pkey PRIMARY KEY (ordernumber, productcode);


--
-- Name: orders_pkey; Type: CONSTRAINT; Schema: public; Owner: sep; Tablespace: 
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (ordernumber);


--
-- Name: payments_pkey; Type: CONSTRAINT; Schema: public; Owner: sep; Tablespace: 
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payments_pkey PRIMARY KEY (customernumber, checknumber);


--
-- Name: productlines_pkey; Type: CONSTRAINT; Schema: public; Owner: sep; Tablespace: 
--

ALTER TABLE ONLY productlines
    ADD CONSTRAINT productlines_pkey PRIMARY KEY (productline);


--
-- Name: products_pkey; Type: CONSTRAINT; Schema: public; Owner: sep; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_pkey PRIMARY KEY (productcode);


--
-- Name: customers_salesrepemployeenumber_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sep
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT customers_salesrepemployeenumber_fkey FOREIGN KEY (salesrepemployeenumber) REFERENCES employees(employeenumber);


--
-- Name: employees_officecode_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sep
--

ALTER TABLE ONLY employees
    ADD CONSTRAINT employees_officecode_fkey FOREIGN KEY (officecode) REFERENCES offices(officecode);


--
-- Name: orderdetails_ordernumber_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sep
--

ALTER TABLE ONLY orderdetails
    ADD CONSTRAINT orderdetails_ordernumber_fkey FOREIGN KEY (ordernumber) REFERENCES orders(ordernumber);


--
-- Name: orderdetails_productcode_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sep
--

ALTER TABLE ONLY orderdetails
    ADD CONSTRAINT orderdetails_productcode_fkey FOREIGN KEY (productcode) REFERENCES products(productcode);


--
-- Name: orders_customernumber_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sep
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_customernumber_fkey FOREIGN KEY (customernumber) REFERENCES customers(customernumber);


--
-- Name: payments_customernumber_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sep
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payments_customernumber_fkey FOREIGN KEY (customernumber) REFERENCES customers(customernumber);


--
-- Name: products_productline_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sep
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_productline_fkey FOREIGN KEY (productline) REFERENCES productlines(productline);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

