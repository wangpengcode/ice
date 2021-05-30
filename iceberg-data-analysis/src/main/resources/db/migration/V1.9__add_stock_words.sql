CREATE TABLE if not exists stock_words
(
    id Bigint,
    code_with_ex  VARCHAR(64) NOT NULL,
    date  VARCHAR(64) NOT NULL,
    code_name VARCHAR(64)  DEFAULT NULL,
    industry VARCHAR(64) DEFAULT NULL,
    lowest VARCHAR(64) DEFAULT NULL,
    words_is_valid boolean default false,
    is_st boolean default false,
    out_standing VARCHAR(64)  DEFAULT NULL
);

alter table stock_words add constraint stock_words_code_with_ex_key unique (date,code_with_ex);
