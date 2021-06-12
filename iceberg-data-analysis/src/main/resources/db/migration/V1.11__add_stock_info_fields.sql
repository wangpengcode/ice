alter table stock_info add column is_download boolean default false;
alter table stock_info add column last_download_at varchar(20) default null;