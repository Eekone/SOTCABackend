-- PostgreSQL schema derived from the JPA entities.
-- Hibernate (Spring Boot 1.5, ddl-auto=create) emits these tables.
-- The connection uses currentSchema=tctdb11, so the shared id sequence
-- is created in tctdb11. Run this script against an empty database.

CREATE SCHEMA mdam_registers;
CREATE SCHEMA tctdb11;

CREATE SEQUENCE tctdb11.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE mdam_registers.register_batch (
    id integer NOT NULL,
    "timestamp" character varying(255),
    type character varying(255),
    CONSTRAINT register_batch_pkey PRIMARY KEY (id),
    CONSTRAINT uk_vkcbn7iyn06l0rgj82xa9d37 UNIQUE ("timestamp")
);

CREATE TABLE mdam_registers.register (
    id integer NOT NULL,
    command integer,
    name character varying(255),
    number integer,
    value real,
    register_batch_id integer,
    CONSTRAINT register_pkey PRIMARY KEY (id),
    CONSTRAINT fk7oiktfcae70kjou8gna5q1x05
        FOREIGN KEY (register_batch_id) REFERENCES mdam_registers.register_batch (id)
);

CREATE TABLE tctdb11.dbversion (
    iddbversion integer NOT NULL,
    dbversionchange timestamp without time zone,
    dbversionname character varying(255),
    CONSTRAINT dbversion_pkey PRIMARY KEY (iddbversion)
);

CREATE TABLE tctdb11.parametertypes (
    parametertypesid integer NOT NULL,
    devtypeid integer,
    extraid character varying(255),
    paramtypename character varying(255),
    parametertypeclassid integer,
    typerelation integer,
    writable boolean,
    CONSTRAINT parametertypes_pkey PRIMARY KEY (parametertypesid)
);
