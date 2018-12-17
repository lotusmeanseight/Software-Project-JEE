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
-- Name: blz; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE blz WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'de_DE.UTF-8' LC_CTYPE = 'de_DE.UTF-8';
ALTER DATABASE blz OWNER TO postgres;

\connect blz

CREATE USER sep;

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
-- Name: bankleitzahl; Type: TABLE; Schema: public; Owner: sep; Tablespace: 
--

CREATE TABLE bankleitzahl (
    bankleitzahl character varying,
    merkmal character varying,
    bezeichnung character varying,
    plz character varying,
    ort character varying,
    kurzbezeichnung character varying,
    pan character varying,
    bic character varying,
    pruefzifferberechnungsmethode character varying,
    datensatznummer character varying,
    aenderungskennzeichen character varying,
    bankleitzahlloeschung character varying,
    nachfolgebankleitzahl character varying
);


ALTER TABLE bankleitzahl OWNER TO sep;

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

