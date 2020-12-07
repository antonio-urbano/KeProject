import requests
import json
key = ""
#--------- TAGS --------
# EXAMPLE: tag = contributor
#response_contributor = requests.get("https://content.guardianapis.com/tags?type=contributor&api-key="+key)
#-----------------------



response_content = requests.get("https://content.guardianapis.com/search?api-key="+key)
response_tags = requests.get("https://content.guardianapis.com/tags?api-key="+key)
response_sections = requests.get("https://content.guardianapis.com/sections?api-key="+key)
response_editions = requests.get("https://content.guardianapis.com/editions?api-key="+key)

response=[response_content, response_tags, response_sections, response_editions]
output_json_names=["content.json", "tags.json", "sections.json", "editions.json"]

def jprint(obj, filename_output):
    with open(filename_output, 'w') as jsonFile:
        jsonFile.write(json.dumps(obj, indent=4))
       
       
for i, name in enumerate(output_json_names):
    jprint(response[i].json(),name)

