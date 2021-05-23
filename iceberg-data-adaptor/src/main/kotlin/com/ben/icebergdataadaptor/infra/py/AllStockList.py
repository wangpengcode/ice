import baostock as bs
import pandas as pd
import datetime
import sys
import requests
import json

stock_list = []

def download_data(date):
    bs.login()

    # 获取指定日期的指数、股票数据
    stock_rs = bs.query_all_stock(date)
    stock_df = stock_rs.get_data()
#     data_df = pd.DataFrame()
    for code in stock_df["code"]:
        stock_list.append(code)
#         k_rs = bs.query_history_k_data_plus(code, "code", date, date)
#         data_df = data_df.append(k_rs.get_data())
    bs.logout()
    requests.post(url='http://localhost:19888/file/upload/all',data=json.dumps(stock_list))

if __name__ == '__main__':
    # 如果不指定日期，则获取三天前的股票列表,由于在周末调用该接口会报错
    day = None
    if (len(sys.argv) == 2):
        day = sys.argv[1]
    if day is None:
        day = (datetime.datetime.now() - datetime.timedelta(days = 3)).strftime('%Y-%m-%d')
    print('执行日期'+day)
    download_data(day)