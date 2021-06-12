CREATE TABLE if not exists super_star
(
    id Bigint,
    code_with_ex  VARCHAR(64) NOT NULL,
    date  VARCHAR(64) NOT NULL,
    year  VARCHAR(10) NOT NULL,
    month VARCHAR(10) NOT NULL,
    code_name VARCHAR(64)  DEFAULT NULL,
    industry VARCHAR(64) DEFAULT NULL,
    zt boolean default false,
    dt boolean default false,
    change VARCHAR(64) DEFAULT NULL
);

alter table super_star add constraint code_with_ex_stock_no_key unique (date,code_with_ex);
