import os
import sys
import subprocess
import urllib.request
import json

from pytube import YouTube
print("download Link : " + sys.argv[1])

yt = YouTube(sys.argv[1]) 
path = '/home/ubuntu/resources/'
yt.streams \
    .filter(progressive=True, file_extension='mp4') \
    .order_by('resolution') \
    .desc() \
    .first() \
    .download(path + 'mp4/')
    
fileName = yt.streams.first().default_filename




os.rename( path + 'mp4/' + fileName, path + 'mp4/' + sys.argv[2] + '.mp4')
result = subprocess.run(['ffprobe', '-v', 'error', '-show_entries', 'format=duration', '-of', 'default=noprint_wrappers=1:nokey=1', 'testV6.mp4'], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
duration = float(result.stdout)

resultFile = path + 'jpg/' + sys.argv[2] + '.jpg'

print(resultFile)

urllib.request.urlretrieve(yt.thumbnail_url, resultFile)
