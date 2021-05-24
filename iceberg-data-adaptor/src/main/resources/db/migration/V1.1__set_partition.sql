CREATE TABLE stock_info
(
    stock_no  VARCHAR(64)   PRIMARY KEY NOT NULL
);

create table stock_history(
id int,
code varchar(20)
) partition by range(code);
create table stock_history_700 partition of stock_history for values from ('1') to ('210');
create table stock_history_700 partition of stock_history for values from ('210') to ('420');
create table stock_history_700 partition of stock_history for values from ('420') to ('640');
create table stock_history_700 partition of stock_history for values from ('640') to ('860');
create table stock_history_700 partition of stock_history for values from ('860') to ('1000');
create table stock_history_700 partition of stock_history for values from ('1000') to ('300001');
create table stock_history_700 partition of stock_history for values from ('300001') to ('300200');
create table stock_history_700 partition of stock_history for values from ('300200') to ('300400');
create table stock_history_700 partition of stock_history for values from ('300400') to ('300600');
create table stock_history_700 partition of stock_history for values from ('300600') to ('300800');
create table stock_history_700 partition of stock_history for values from ('300800') to ('301000');
create table stock_history_700 partition of stock_history for values from ('301000') to ('302000');
create table stock_history_700 partition of stock_history for values from ('302000') to ('303000');
create table stock_history_700 partition of stock_history for values from ('303000') to ('306000');
create table stock_history_700 partition of stock_history for values from ('306000') to ('310000');
create table stock_history_700 partition of stock_history for values from ('310000') to ('600000');
create table stock_history_700 partition of stock_history for values from ('600000') to ('600200');
create table stock_history_700 partition of stock_history for values from ('600200') to ('600400');
create table stock_history_700 partition of stock_history for values from ('600400') to ('600600');
create table stock_history_700 partition of stock_history for values from ('600600') to ('601000');
create table stock_history_700 partition of stock_history for values from ('601000') to ('601500');
create table stock_history_700 partition of stock_history for values from ('601500') to ('602000');
create table stock_history_700 partition of stock_history for values from ('602000') to ('604000');
create table stock_history_700 partition of stock_history for values from ('604000') to ('680000');
create table stock_history_700 partition of stock_history for values from ('680000') to ('688200');
create table stock_history_700 partition of stock_history for values from ('688200') to ('688400');
create table stock_history_700 partition of stock_history for values from ('688400') to ('688800');
create table stock_history_700 partition of stock_history for values from ('688800') to ('689800');
create table stock_history_700 partition of stock_history for values from ('689800') to ('700000');
