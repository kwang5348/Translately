import json
import subprocess
from collections import OrderedDict

result = subprocess.run(['ffprobe', '-v', 'error', '-show_entries', 'format=duration', '-of', 'default=noprint_wrappers=1:nokey=1', 'testV6.mp4'], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
duration = float(result.stdout)
file_name = "test"
file_data = OrderedDict()



file_data["name"] = file_name
file_data["duration"] = duration
file_data["url"] = "youtube.com"

with open(file_name + '.json', 'w', encoding="utf-8") as make_file:
    json.dump(file_data, make_file, ensure_ascii=False, indent="\t")
