from firebase import firebase
from pyfcm import FCMNotification
import requests
import time
from bs4 import BeautifulSoup

def parseFromNaverNews():
    url = 'https://news.naver.com/main/list.nhn?mode=LS2D&mid=sec&sid1=102&sid2=249' # naverNews accident&incident page
    html = requests.get(url).text # requests

    soup = BeautifulSoup(html, 'lxml') # beautifulsoup parser
    title = []
    page = soup.find_all('dt') # headline parsing

    for i in page:
        hl = i.text.replace('\t', '').replace('\n', '').replace('\r', '') # delete needless character
        title.append(hl) # make list
    return title


def send_msg_to_users(uToken, newsMessage):
    server_key = open('./serverKey.txt').read().replace('\n', '') # need to hide
    user_token = uToken # from firebase
    push_service = FCMNotification(api_key=server_key) # object that need to send notification to app

    result = push_service.notify_multiple_devices(registration_ids=user_token, message_title='[News]', message_body=newsMessage) # send notification
# send notification to multiple devices

def getUserToken_from_fb():
    url = open('./serverUrl.txt').read().replace('\n', '') # firebase url
    fb = firebase.FirebaseApplication(url, None)
    result = fb.get('/', None) # get from firebase DB
    temp = result.keys() # parse keys
    listKeys = []
    for i in temp:
        listKeys.append(i) # save keys in list
    return listKeys
#list return

#main
if __name__ == "__main__":
    tempHeadLine = '' # prevent overlap
    while 1:
        print(tempHeadLine)
        main_keyword = '칼' # necessary keyword
        bonus_keyword = ['승객','사고', '중단', '정지', '중지'] # bonus keyword
        head = parseFromNaverNews() # parse news headline from naver_news
        title = head
        for i in title: # for loof
            if main_keyword in i: # '지하철' and
                for j in bonus_keyword: # bonus_keyword[0] or bonus_keyword[1] or bonus_keyword[2] or ...
                    if j in i: # find
                        if tempHeadLine != i:
                            tempHeadLine = i
                            usersToken = getUserToken_from_fb() # get token from firebase
                            newsMessage = i # headline
                            send_msg_to_users(usersToken, newsMessage) # call send_msg_to_user func
                            continue
#        time.sleep(60) # set interval 60

