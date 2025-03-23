create table if not exists studySession (
       id uuid primary key default gen_random_uuid(),
       description varchar,
       resourceLink varchar,
       resourceMaterial varchar,
       method varchar,
       timeSpent interval,
       studyDate date,
       created timestamp,
       updated timestamp
);