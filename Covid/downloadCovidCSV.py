import glob
import csv, json
import urllib.request


def download_csv():
    path = "https://storage.googleapis.com/covid19-open-data/v2/latest/main.csv"
    try:
        urllib.request.urlretrieve(path, "covid_sources.csv")
    except:
        print("INVALID URL")

download_csv()