import json
import subprocess
from collections import OrderedDict
import pymysql
result = subprocess.run(['ffprobe', '-v', 'error', '-show_entries', 'format=duration', '-of', 'default=noprint_wrappers=1:nokey=1', 'testV6.mp4'], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
duration = float(result.stdout)
#.replace("\r\n", "")
input = int(duration)
print(type(input))
conn = pymysql.connect('localhost', 'root', 'dntksdlvlfdygo', 'ssafydb')

try:
    with conn.cursor() as cursor:
        sql = 'update subtitle set duration = %s where subid = 1'
        cursor.execute(sql, input)
    conn.commit()
    print(cursor.rowcount)
finally:
    conn.close()
