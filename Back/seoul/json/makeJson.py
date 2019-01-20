import requests
import json
from bs4 import BeautifulSoup

#req = requests.get("http://www.seoulmetro.co.kr/kr/getLineData.do")
#soup = BeautifulSoup(req.text, "html.parser") # already parsing
#f = open("data.json", 'wb') #
#f.write(soup.encode('utf-8')) # 

data = open('./beforeData.json').read()
Dic = json.loads(data)

hosun1to4 = ["1", "2", "3", "4"]
hosun5to8 = ["5", "6", "7", "8"]

linesj = dict()
for i in hosun1to4:
    sj = dict()
    hosun = Dic[i]["attr"].get("data-label")
    for j in range(0, len(Dic[str(i)]["stations"])):
        cur = Dic[i]["stations"][j].get("station-nm", "empty")
        cur = cur.replace("\n", "")
        if cur != "empty":
            sj[cur] = "S" + Dic[str(i)]["stations"][j].get("station-cd")

    linesj[hosun] = sj

with open('1to4.json', 'w') as f:
    json.dump(linesj, f, ensure_ascii=False)

### *** Dictionary generate 1to4 *** ###

secondSj=dict()
for i in hosun5to8:
    sj = dict()
    hosun = Dic[i]["attr"].get("data-label")
    for j in range(0, len(Dic[str(i)]["stations"])):
        cur = Dic[i]["stations"][j].get("station-nm", "empty")
        cur = cur.replace("\n", "")
        if cur != "empty":
            sj[cur] = "S" + Dic[str(i)]["stations"][j].get("station-cd")

    linesj[hosun] = sj
    secondSj[hosun] = sj

with open('5to8.json', 'w') as f:
    json.dump(secondSj, f, ensure_ascii=False)

### *** Dictionary generate 5to8 *** ###

with open('allJson.json', 'w')as f:
    json.dump(linesj, f, ensure_ascii=False)

