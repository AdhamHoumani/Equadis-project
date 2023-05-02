create table if not exists finance.account (
        id uuid not null,
        initial_balance numeric not null,
        balance numeric not null,
        account_number text not null unique,
        account_status text not null,
        account_type text not null,
        currency text not null,
        customer_id uuid not null,
        primary key (id)
);


