#!/usr/bin/python
# -*- coding: UTF-8 -*-
import baostock as bs
import pandas as pd
import datetime

def download_data(date):
    bs.login()

    # 获取指定日期的指数、股票数据
    stock_rs = bs.query_all_stock(date)
    stock_df = stock_rs.get_data()
    data_df = pd.DataFrame()
    for code in stock_df["code"]:
        print("Downloading :" + code)
#         k_rs = bs.query_history_k_data_plus(code, "code", date, date)
#         data_df = data_df.append(k_rs.get_data())
    bs.logout()
#     data_df.to_csv("AllStockList.csv", encoding="utf8", index=False)
    print(data_df)


if __name__ == '__main__':
    # 获取指定日期全部股票的日K线数据
    date = datetime.datetime.now().strftime('%Y-%m-%d')
    print(date)
    download_data(date)