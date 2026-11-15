-- Schema for SOTCABackend, derived from the JPA entities.
--
-- Run against an existing database (this file does not create the database):
--   /Library/PostgreSQL/18/bin/psql -U postgres -h localhost -p 5432 -d texnoprom -f sql/postgresql-schema.sql
--
-- Prepare once, as postgres, before the first run:
--   CREATE ROLE sotca LOGIN PASSWORD '...';
--   CREATE DATABASE texnoprom OWNER sotca;
--
-- The script is safe to run again: existing tables and rows are kept.
-- Schemas, tables and the id sequence are owned by sotca, which is the role
-- the application uses. Hibernate connects with currentSchema=tctdb11, so
-- hibernate_sequence lives in that schema.
--
-- There is no SQL Server syntax here (no IDENTITY, NVARCHAR, GETDATE, BIT or
-- bracketed identifiers).

\set ON_ERROR_STOP on

BEGIN;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'sotca') THEN
        RAISE EXCEPTION 'Role sotca does not exist. Create it, then run this script against database texnoprom.';
    END IF;
END $$;

CREATE SCHEMA IF NOT EXISTS mdam_registers;
CREATE SCHEMA IF NOT EXISTS tctdb11;

ALTER SCHEMA mdam_registers OWNER TO sotca;
ALTER SCHEMA tctdb11 OWNER TO sotca;

CREATE SEQUENCE IF NOT EXISTS tctdb11.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE tctdb11.hibernate_sequence OWNER TO sotca;

CREATE TABLE IF NOT EXISTS mdam_registers.register_batch (
    id integer NOT NULL,
    "timestamp" character varying(255),
    type character varying(255),
    CONSTRAINT register_batch_pkey PRIMARY KEY (id),
    CONSTRAINT uk_vkcbn7iyn06l0rgj82xa9d37 UNIQUE ("timestamp")
);

CREATE TABLE IF NOT EXISTS mdam_registers.register (
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

CREATE TABLE IF NOT EXISTS tctdb11.dbversion (
    iddbversion integer NOT NULL,
    dbversionchange timestamp without time zone,
    dbversionname character varying(255),
    CONSTRAINT dbversion_pkey PRIMARY KEY (iddbversion)
);

CREATE TABLE IF NOT EXISTS tctdb11.parametertypes (
    parametertypesid integer NOT NULL,
    devtypeid integer,
    extraid character varying(255),
    paramtypename character varying(255),
    parametertypeclassid integer,
    typerelation integer,
    writable boolean,
    CONSTRAINT parametertypes_pkey PRIMARY KEY (parametertypesid)
);

ALTER TABLE mdam_registers.register_batch OWNER TO sotca;
ALTER TABLE mdam_registers.register OWNER TO sotca;
ALTER TABLE tctdb11.dbversion OWNER TO sotca;
ALTER TABLE tctdb11.parametertypes OWNER TO sotca;

GRANT USAGE ON SCHEMA mdam_registers, tctdb11 TO sotca;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA mdam_registers TO sotca;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA tctdb11 TO sotca;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE tctdb11.hibernate_sequence TO sotca;

DO $$
DECLARE
    missing text;
BEGIN
    SELECT string_agg(obj, ', ')
      INTO missing
      FROM (
            SELECT 'schema mdam_registers' AS obj
             WHERE NOT EXISTS (
                 SELECT 1 FROM pg_namespace WHERE nspname = 'mdam_registers'
             )
            UNION ALL
            SELECT 'schema tctdb11'
             WHERE NOT EXISTS (
                 SELECT 1 FROM pg_namespace WHERE nspname = 'tctdb11'
             )
            UNION ALL
            SELECT 'sequence tctdb11.hibernate_sequence'
             WHERE NOT EXISTS (
                 SELECT 1
                   FROM pg_class c
                   JOIN pg_namespace n ON n.oid = c.relnamespace
                  WHERE n.nspname = 'tctdb11'
                    AND c.relname = 'hibernate_sequence'
                    AND c.relkind = 'S'
             )
      ) checks;

    IF missing IS NOT NULL THEN
        RAISE EXCEPTION 'Schema script incomplete, missing: %', missing;
    END IF;

    IF EXISTS (
        SELECT 1
          FROM pg_namespace
         WHERE nspname IN ('mdam_registers', 'tctdb11')
           AND nspowner <> 'sotca'::regrole
    ) THEN
        RAISE EXCEPTION 'Schema owner is not sotca';
    END IF;
END $$;

COMMIT;
