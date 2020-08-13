import os
import sys
import subprocess
import urllib.request

from pytube import YouTube
print("download Link : " + sys.argv[1])

yt = YouTube(sys.argv[1]) 
path = '/home/ubuntu/resources/'
yt.streams \
    .filter(progressive=True, file_extension='mp4') \
    .order_by('resolution') \
    .desc() \
    .first() \
    .download(path + 'wav/')
    
os.rename( path + 'wav/' + yt.streams.first().default_filename, path + 'wav/' + sys.argv[2] + '.mp4')

resultFile = path + 'thumbnail/' + sys.argv[2] + '.jpg'

print(resultFile)

urllib.request.urlretrieve(yt.thumbnail_url, resultFile)
