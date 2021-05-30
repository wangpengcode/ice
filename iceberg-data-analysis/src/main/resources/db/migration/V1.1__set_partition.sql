-- adjustflag	复权状态(1：后复权， 2：前复权，3：不复权）
-- turn	换手率	[指定交易日的成交量(股)/指定交易日的股票的流通股总股数(股)]*100%
-- tradestatus	交易状态(1：正常交易 0：停牌）
-- pctChg	涨跌幅（百分比）	日涨跌幅=[(指定交易日的收盘价-指定交易日前收盘价)/指定交易日前收盘价]*100%
-- peTTM	滚动市盈率	(指定交易日的股票收盘价/指定交易日的每股盈余TTM)=(指定交易日的股票收盘价*截至当日公司总股本)/归属母公司股东净利润TTM
-- pbMRQ	市净率	(指定交易日的股票收盘价/指定交易日的每股净资产)=总市值/(最近披露的归属母公司股东的权益-其他权益工具)
-- psTTM	滚动市销率	(指定交易日的股票收盘价/指定交易日的每股销售额)=(指定交易日的股票收盘价*截至当日公司总股本)/营业总收入TTM
-- pcfNcfTTM	滚动市现率	(指定交易日的股票收盘价/指定交易日的每股现金流TTM)=(指定交易日的股票收盘价*截至当日公司总股本)/现金以及现金等价物净增加额TTM
-- isST	是否ST股，1是，0否

create table stock_history(
id Bigint,
date varchar(20) not null ,
code Bigint ,
stock_no varchar(20) not null,
open varchar(20) not null ,
high varchar(20) not null ,
low varchar(20) not null ,
close varchar(20) not null,
preclose varchar(20) not null ,
volume varchar(20) not null ,
amount varchar(20) not null ,
adjust_flag varchar(20) not null ,
turn varchar(20) not null  ,
trade_status varchar(20) not null,
pct_chg varchar(20) not null,
pe_ttm varchar(20) not null,
pb_mrq varchar(20) not null,
ps_ttm varchar(20) not null,
pcf_ncfttm varchar(20) not null ,
is_st varchar(20) not null ) partition by range(code);

create sequence HIBERNATE_SEQUENCE
minvalue 1
maxvalue 9999999999999999
start with 1
increment by 1
cache 20;

alter table stock_history add constraint date_code_key_stock_no unique (date,code,stock_no);

create table stock_history_210 partition of stock_history for values from ('1') to ('210');
create table stock_history_420 partition of stock_history for values from ('210') to ('420');
create table stock_history_640 partition of stock_history for values from ('420') to ('640');
create table stock_history_860 partition of stock_history for values from ('640') to ('860');
create table stock_history_1000 partition of stock_history for values from ('860') to ('1001');
create table stock_history_300001 partition of stock_history for values from ('1001') to ('300001');
create table stock_history_300200 partition of stock_history for values from ('300001') to ('300200');
create table stock_history_300400 partition of stock_history for values from ('300200') to ('300400');
create table stock_history_300600 partition of stock_history for values from ('300400') to ('300600');
create table stock_history_300800 partition of stock_history for values from ('300600') to ('300800');
create table stock_history_301000 partition of stock_history for values from ('300800') to ('301000');
create table stock_history_302000 partition of stock_history for values from ('301000') to ('302000');
create table stock_history_303000 partition of stock_history for values from ('302000') to ('303000');
create table stock_history_306000 partition of stock_history for values from ('303000') to ('306000');
create table stock_history_310000 partition of stock_history for values from ('306000') to ('310000');
create table stock_history_600000 partition of stock_history for values from ('310000') to ('600000');
create table stock_history_600200 partition of stock_history for values from ('600000') to ('600200');
create table stock_history_600400 partition of stock_history for values from ('600200') to ('600400');
create table stock_history_600600 partition of stock_history for values from ('600400') to ('600600');
create table stock_history_601000 partition of stock_history for values from ('600600') to ('601000');
create table stock_history_601500 partition of stock_history for values from ('601000') to ('601500');
create table stock_history_602000 partition of stock_history for values from ('601500') to ('602000');
create table stock_history_604000 partition of stock_history for values from ('602000') to ('604000');
create table stock_history_680000 partition of stock_history for values from ('604000') to ('680000');
create table stock_history_688200 partition of stock_history for values from ('680000') to ('688200');
create table stock_history_688400 partition of stock_history for values from ('688200') to ('688400');
create table stock_history_688800 partition of stock_history for values from ('688400') to ('688800');
create table stock_history_689800 partition of stock_history for values from ('688800') to ('689800');
create table stock_history_700000 partition of stock_history for values from ('689800') to ('700000');
