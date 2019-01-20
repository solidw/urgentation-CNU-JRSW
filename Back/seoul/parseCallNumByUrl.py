import requests
import json
from bs4 import BeautifulSoup

def get_html(url):
    _html = ""
    resp = requests.get(url)
    resp.encoding = 'utf-8'
    if resp.status_code == 200:
        _html = resp.text
    return _html


data = open('./json/allJson.json').read()
Dic = json.loads(data)
### Json loaded

for i in Dic:
    for j in Dic[i]:
        unUrl = 'http://www.seoulmetro.co.kr/kr/getStationInfo.do?stationId='+Dic[i][j][1:]
        result = get_html(unUrl)
        if result=="":
            for l in range(0, 10):
                print('#### Fail #####')

        else:
            with open('./callNumberList.txt', 'a') as writer:
                soup = BeautifulSoup(result,'html.parser')
                temp = soup.find('table', {'class':'stationInfoTable'}).find_all('td')
                buno = temp[1].text
                writer.write(i + '_' + j + ':' + buno + '\n')
                print(i + '_' + j + ':' + buno)

    for k in range (0, 10):
        print(i+'Done ! ##')
