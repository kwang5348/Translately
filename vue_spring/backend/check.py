import os
import sys
import subprocess
import urllib.request
import json
import time

from pytube import YouTube

start = time.time()

print("download Link : " + sys.argv[1])

yt = YouTube(sys.argv[1]) 

print("fileName=", yt.title)
print("fileLength=", yt.length)
