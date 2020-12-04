import requests
import json

#--------- TAGS --------
# EXAMPLE: tag = contributor
#response_contributor = requests.get("https://content.guardianapis.com/tags?type=contributor&api-key=405dd2d3-a2c3-4fbe-8b6a-df9ab26326d6")
#-----------------------



response_content = requests.get("https://content.guardianapis.com/search?api-key=405dd2d3-a2c3-4fbe-8b6a-df9ab26326d6")
response_tags = requests.get("https://content.guardianapis.com/tags?api-key=405dd2d3-a2c3-4fbe-8b6a-df9ab26326d6")
response_sections = requests.get("https://content.guardianapis.com/sections?api-key=405dd2d3-a2c3-4fbe-8b6a-df9ab26326d6")
response_editions = requests.get("https://content.guardianapis.com/editions?api-key=405dd2d3-a2c3-4fbe-8b6a-df9ab26326d6")

response=[response_content, response_tags, response_sections, response_editions]
output_json_names=["content.json", "tags.json", "sections.json", "editions.json"]

def jprint(obj, filename_output):
    with open(filename_output, 'w') as jsonFile:
        jsonFile.write(json.dumps(obj, indent=4))
       
       
for i, name in enumerate(output_json_names):
    jprint(response[i].json(),name)

