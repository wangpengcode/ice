alter table stock_info add column status VARCHAR(20);
update stock_info set status = 'STOCK';
update stock_info set status = 'NOT_STOCK' where stock_no < '1000' and exchange_house = 'sh';