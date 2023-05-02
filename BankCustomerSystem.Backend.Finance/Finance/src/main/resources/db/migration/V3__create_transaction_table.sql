create table if not exists finance.transaction (
        id uuid not null,
        account_id uuid not null,
        transaction_status text not null,
        transaction_type text not null,
        amount numeric not null,
        created_on date not null,
        primary key (id),
        foreign key (account_id) references account(id) ON DELETE CASCADE
);