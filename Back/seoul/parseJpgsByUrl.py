import requests
import json
from bs4 import BeautifulSoup

def get_html(url):
    _html = ""
    resp = requests.get(url)
    resp.encoding = 'utf-8'
    if resp.status_code == 200:
        _html = resp.content
    return _html


data = open('./json/1to4.json').read()
Dic = json.loads(data)
### 1to4
for i in Dic:
    for j in Dic[i]:
        unUrl = 'http://www.seoulmetro.co.kr/web_upload/cyberstation/in/station_' + Dic[i][j][1:] + '.jpg'
        result = get_html(unUrl)
        jpgname = i + '_' + j + '역.jpg'
        if result=="":
            with open('./jpgs/'+jpgname, 'wb') as writer:
                with open('./errorMap.png', 'rb') as reader:
                    pngData = reader.read()
                    writer.write(pngData)
                    print(i + '_' + j + '_No!')
        else:
            with open('./jpgs/'+jpgname, 'wb') as writer:
                img = requests.get(unUrl)
                writer.write(img.content)
                print(i + '_' + j + '_Yes!')
### ~1to4
### ***
### 5to8
data = open('./json/5to8.json').read()
Dic = json.loads(data)
for i in Dic:
    for j in Dic[i]:
        unUrl = 'http://www.seoulmetro.co.kr/web_upload/cyberstation/in/in_' + Dic[i][j][1:] + '.jpg'
        result = get_html(unUrl)
        jpgname = i + '_' + j + '역.jpg'
        if result=="":
            with open('./jpgs/'+jpgname, 'wb') as writer:
                with open('./errorMap.png', 'rb') as reader:
                    pngData = reader.read()
                    writer.write(pngData)
                    print('!! ' + i + '_' + j + '_No!!')
        else:
            with open('./jpgs/'+jpgname, 'wb') as writer:
                img = requests.get(unUrl)
                writer.write(img.content)
                print('## ' + i + '_' + j + '_YES ##')
### ~4to8
for i in range(0, 10):
    print('************done!*************')
