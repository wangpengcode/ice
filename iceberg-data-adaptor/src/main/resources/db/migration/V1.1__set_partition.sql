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
data varchar(20) not null comment '交易所行情日期',
code varchar(20) not null comment '证券代码',
open varchar(20) not null comment '开盘价',
high varchar(20) not null comment '最高价',
low varchar(20) not null comment '最低价',
close varchar(20) not null comment '收盘价',
preclose varchar(20) not null comment '前收盘价',
volume varchar(20) not null comment '成交量（累计 单位：股）',
amount varchar(20) not null comment '成交额（单位：人民币元）',
adjustflag varchar(20) not null comment '复权状态(1：后复权， 2：前复权，3：不复权）',
turn varchar(20) not null comment '换手率',
tradestatus varchar(20) not null comment '交易状态(1：正常交易 0：停牌）',
pctChg varchar(20) not null comment '涨跌幅（百分比）',
peTTM varchar(20) not null comment '滚动市盈率',
pbMRQ varchar(20) not null comment '市净率',
psTTM varchar(20) not null comment '滚动市销率',
pcfNcfTTM varchar(20) not null comment '滚动市现率',
isST varchar(20) not null comment '是否ST股，1是，0否',
UNIQUE KEY `data_code_key` (`data`,`code`)
) partition by range(code);
create table stock_history_210 partition of stock_history for values from ('1') to ('210');
create table stock_history_420 partition of stock_history for values from ('210') to ('420');
create table stock_history_640 partition of stock_history for values from ('420') to ('640');
create table stock_history_860 partition of stock_history for values from ('640') to ('860');
create table stock_history_1000 partition of stock_history for values from ('860') to ('1000');
create table stock_history_300001 partition of stock_history for values from ('1000') to ('300001');
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
