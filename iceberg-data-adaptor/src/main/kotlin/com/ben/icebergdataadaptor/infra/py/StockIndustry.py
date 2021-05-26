import baostock as bs
import sys
import requests
import json

webhook = ''
def download_data(webhook):
    bs.login()

    # 获取指定日期的指数、股票数据
    industry_rs = bs.query_stock_industry()
    industry_list = []
    while (industry_rs.error_code == '0') & industry_rs.next():
        # 获取一条记录，将记录合并在一起
        industry_list.append(industry_rs.get_row_data())
    bs.logout()
#     print(data_list)
    requests.post(url=webhook,data=json.dumps(industry_list).encode('utf-8'))

if __name__ == '__main__':
    # 如果不指定日期，则获取三天前的股票列表,由于在周末调用该接口会报错
    day = None
    webhook = sys.argv[1]
    download_data(webhook)