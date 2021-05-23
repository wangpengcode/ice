import baostock as bs
import pandas as pd
import datetime
import sys


def download_data(date):
    bs.login()

    # 获取指定日期的指数、股票数据
    stock_rs = bs.query_all_stock(date)
    stock_df = stock_rs.get_data()
    data_df = pd.DataFrame()
    for code in stock_df["code"]:
        print("Downloading :" + code)
        k_rs = bs.query_history_k_data_plus(code, "date,code,open,high,low,close", date, date)
        data_df = data_df.append(k_rs.get_data())
    bs.logout()
    data_df.to_csv("all_stock.csv", encoding="utf8", index=False)
#     print(data_df)


if __name__ == '__main__':
    # 获取三天前的股票列表,由于在周末调用该接口会报错
    csvName = sys.argv[0]
    threeDayAgo = (datetime.datetime.now() - datetime.timedelta(days = 3)).strftime('%Y-%m-%d')
    download_data(threeDayAgo)