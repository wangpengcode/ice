CREATE TABLE STOCK_QUARTER_PROFIT
(
    id Bigint,
    code  VARCHAR(64)  NOT NULL,
    stat_date VARCHAR(64) NOT NULL,
    pub_date VARCHAR(64) default NULL,
    nr_turn_ratio VARCHAR(64) default NULL,
    nr_turn_days VARCHAR(64) default NULL,
    inv_turn_ratio VARCHAR(64) default NULL,
    inv_turn_days VARCHAR(64) default NULL,
    ca_turn_ratio VARCHAR(64) default NULL,
    asset_turn_ratio VARCHAR(64) default NULL
);

alter table STOCK_QUARTER_PROFIT add constraint stock_quarter_profit_code_pub_date unique (code,stat_date);
