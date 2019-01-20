# make justNameList.json

import json

finalList = []
data = open('./Line.json').read()
Dic = json.loads(data)
temp_name = ""
yesList = ['1','2','3','4','5','6','7','8']

for j in Dic['DATA']:
    if temp_name != j['station_nm']:
        print(j['station_nm'])
        temp_name = j['station_nm']
        finalList.append(j['station_nm'])

print(finalList)
with open('./justNameList.json', 'w') as f:
    json.dump(finalList, f, ensure_ascii=False)


