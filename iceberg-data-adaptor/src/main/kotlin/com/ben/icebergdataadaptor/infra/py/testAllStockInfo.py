import baostock as bs
import pandas as pd
import datetime
import sys
import requests
import json

webhook = ''
def download_data(date,webhook):
    data_list = []
    bs.login()

    # 获取指定日期的指数、股票数据
    stock_rs = bs.query_all_stock(date)
    stock_df = stock_rs.get_data()
    i = 0
    for d in stock_df['code']:
        rs = bs.query_stock_basic(code=d)
        # 打印结果集
        while (rs.error_code == '0') & rs.next():
            i = i + 1
            data_list.append(rs.get_row_data())
            if (i == 20):
                requests.post(url=webhook,data=json.dumps(data_list).encode('utf-8'))
                data_list = []
                i = 0
    bs.logout()
#     print(data_list)
    requests.post(url=webhook,data=json.dumps(data_list).encode('utf-8'))

if __name__ == '__main__':
    # 如果不指定日期，则获取三天前的股票列表,由于在周末调用该接口会报错
    day = None
    webhook = sys.argv[1]
    if (len(sys.argv) == 3):
        day = sys.argv[2]
    if day is None:
        day = (datetime.datetime.now() - datetime.timedelta(days = 3)).strftime('%Y-%m-%d')
    print('执行日期'+day)
    download_data(day,webhook)