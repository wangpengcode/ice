import baostock as bs
import pandas as pd
import datetime
import sys
import requests
import json

stock_list = []
webhook = ''
headers = {'Connection':'close'}
def download_data(stock,webhook,year,quarter):
    bs.login()

    # 获取指定日期的指数、股票数据
    stock_rs = bs.query_operation_data(code=stock, year=year, quarter=quarter)
     #### 打印结果集 ####
    data_list = []
    while (stock_rs.error_code == '0') & stock_rs.next():
         # 获取一条记录，将记录合并在一起
        data_list.append(stock_rs.get_row_data())
    bs.logout()
    print(data_list)
    requests.post(url=webhook,data=json.dumps(data_list),timeout=30,headers=headers)

if __name__ == '__main__':
    s = requests.session()
    s.keep_alive = False
    # 如果不指定日期，则获取三天前的股票列表,由于在周末调用该接口会报错
    end_day = None
    stock = ''
    webhook = sys.argv[1]
    if (len(sys.argv) == 5):
        stock = sys.argv[2]
        year = sys.argv[3]
        quarter = sys.argv[4]
    if end_day is None:
        end_day = datetime.datetime.now().strftime('%Y-%m-%d')
    print('执行日期开始' + end_day)
    download_data(stock,webhook,year,quarter)