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
resultFile = path + 'jpg/' + sys.argv[2] + '.jpg'

print(resultFile)

urllib.request.urlretrieve(yt.thumbnail_url, resultFile)

print("jpg : " + yt.thumbnail_url)
