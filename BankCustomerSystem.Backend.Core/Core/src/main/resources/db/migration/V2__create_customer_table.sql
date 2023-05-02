create table if not exists core.customer (
        id uuid not null,
        first_name text not null,
        last_name text not null,
        email text not null unique,
        phone_number text not null unique,
        address text null,
        primary key (id)
);