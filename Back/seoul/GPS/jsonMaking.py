# make finalJson.json that about GPSPosition

import json

finalList = []
data = open('./Line.json').read()
Dic = json.loads(data)
yesList = ['1','2','3','4','5','6','7','8'] # catch 1~8Line's GPS

for j in Dic['DATA']:
    if type(j['xpoint_wgs']) != type(None): # when no gps position
        if j['line_num'] in yesList:
            xydict = dict() # reset dict
            print('line_num : ' + j['line_num'] + ' station_nm : ' + j['station_nm'] + ' xpoint_wgs : ' + j['xpoint_wgs'] + ' ypoint_wgs : ' + j['ypoint_wgs'])
            xydict['name'] = j['station_nm']
            xydict['x'] = j['xpoint_wgs']
            xydict['y'] = j['ypoint_wgs']
            finalList.append(xydict)
            # [{"name":"station_name","x":"x.xx","y":"y.yy"},{},{},..] name, GPS_x, GPS_y

with open('./finalJson.json', 'w') as f:
    json.dump(finalList, f, ensure_ascii=False) # json dump

