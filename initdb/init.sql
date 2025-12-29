DO
$do$
BEGIN
    IF NOT EXISTS (
       SELECT FROM pg_catalog.pg_roles
       WHERE rolname = 'atlasbase_admin'
    ) THEN
       CREATE ROLE atlasbase_admin LOGIN PASSWORD 'atlasbase_admin';
    END IF;
END
$do$;

CREATE DATABASE atlasbasedb OWNER atlasbase_admin;
