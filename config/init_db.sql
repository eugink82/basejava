create table resume
(
  uuid      char(36) not null
    constraint resume_pk
      primary key,
  full_name text     not null
);

CREATE TABLE contact
(
  id          SERIAL,
  resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
  type        TEXT     NOT NULL,
  value       TEXT     NOT NULL
);

create unique index contact_uuid_type_index
  on contact (resume_uuid, type);



