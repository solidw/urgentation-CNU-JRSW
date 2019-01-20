import json

data = open('./beforeData.txt', 'r', encoding='utf-8-sig').read() # read
data = data.replace('\n', ',')
data = data.split(',')

buno = '' # call Number
finalList = []
for i in range(0, len(data), 4):
    sj = dict() # dictionary
    print(i,len(data))
    if (i+4) >= len(data): # last of index of list
        break
    sj['name'] = data[i]
    sj['x'] = data[i+1]
    sj['y'] = data[i+2]
    finalList.append(sj)
    buno += '1호선_' + data[i] + ':' + data[i+3] + '\n'
    # finalList = [{"name":"asdf", "x":"x.xx", "y":"y.yy"}, {}, {}...] 
    # station_name, GPS_xPosition, GPS_yPosition

with open('./djGPSData.json', 'w') as f:
    json.dump(finalList, f, ensure_ascii=False) # json dump

with open('./djCallList.txt', 'w') as f:
    f.write(buno) # make CallNumber file
