import baostock as bs
import pandas as pd
import datetime
import sys
import requests
import json

stock_list = []
webhook = ''
def download_data(stock,webhook,start_day,end_day):
    bs.login()

    # 获取指定日期的指数、股票数据
    stock_rs = bs.query_history_k_data_plus(stock,"date,code,open,high,low,close,preclose,volume,amount,adjustflag,turn,tradestatus,pctChg,peTTM,pbMRQ,psTTM,pcfNcfTTM,isST",start_date=start_day, end_date=end_day, adjustflag = '2')
     #### 打印结果集 ####
    data_list = []
    while (stock_rs.error_code == '0') & stock_rs.next():
         # 获取一条记录，将记录合并在一起
        data_list.append(stock_rs.get_row_data())
#     result = pd.DataFrame(data_list, columns=rs.fields)
    bs.logout()
    requests.post(url=webhook,data=json.dumps(data_list))

if __name__ == '__main__':
    # 如果不指定日期，则获取三天前的股票列表,由于在周末调用该接口会报错
    end_day = None
    start_day = ''
    stock = ''
    webhook = sys.argv[1]
    if (len(sys.argv) == 5):
        stock = sys.argv[2]
        start_day = sys.argv[3]
        end_day = sys.argv[4]
    if end_day is None:
        end_day = (datetime.datetime.now() - datetime.timedelta(days = 3)).strftime('%Y-%m-%d')
    print('执行日期开始'+start_day + '结束日期' + end_day)
    download_data(stock,webhook,start_day,end_day)