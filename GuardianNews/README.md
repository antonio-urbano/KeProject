# Guardian News ontology

This ontology includes a set of classes and properties built to model the endpoints of the ['The Guardian OpenPlatform'](https://open-platform.theguardian.com/documentation/). <br>
In the ontology four endpoints have been modeled that can be used to retrieve different items associated to guardian news:

* <i>Contents</i>: model of all the pieces of content in the Guardian APIs. For example, a Guardian intem may have a content on political debates;
* <i>Tags</i>: model the tags in the APIs. A tag is a piece of data used to categorise the Guardian contents. There are more than 50,000 and all the guardian conten have been categorised.
* <i>Sections</i>: model of the sections in the APIs. Sections are used to logically group Guardian contents. For example,if an article article contains a section "technology" then technologically-related content will be clustered within technology section. Every tag has a type and may have a description. A particular type of tag is the contributor which represents the author of a content. For that tags important information are the author bio, his name and his associated twitter.
* <i>Edition</i>: Editions are the different front main pages of the Guardian site. There are editions for the United Kingdom, the United States and Australia.

To each endpoint is associated a webUri to see the web representation, or the apiUrl to see the API's representation of that specific endpoint.
<br>
Furthermore each tag may have associated one or more *activeSponsorship* for which has been modeled its name, the logo and the 
sponsorStartDate and sponsorEndDate which represent the date in which the sponsor respectively starts and ends to be valid for that specific tag.

# Data Linking
**API REQUEST**: the infomation about endpoints and sponsors above described is retrieved from a get call. 
The python script [NEWSpyscriptJSON.py](NEWSpyscriptJSON.py) can be used for this purpose which will produce four different .json  files having the name of the four endpoints.
For more details about the guardian endpoints you can visit the following link:
https://open-platform.theguardian.com/documentation/

**YARRRML** (https://rml.io/yarrrml/matey/#): A set of rules has been written to fit the ontology model. Due to the fact of multiple sources a list of rules 
have been written and parsed using the Yarrrml matey tool. More precisely:
* [guardian_rules_source.rml.ttl](guardian_rules_source.rml.ttl): rules mapping of all the endpoints and sponsors. For each class the respective json file is used (e.g. for the content is used the content.json, for the section is used the sections.json, etc.)
* [guardian_rules_tags.rml.ttl](guardian_rules_editions.rml.ttl): set of rules used for mapping the tags associated to content endpoint
* [guardian_rules_sections.rml.ttl](guardian_rules_sections.rml.ttl): set of rules used for mapping the sections associated to content endpoint
* [guardian_rules_editions.rml.ttl](guardian_rules_editions.rml.ttl): set of rules used for mapping the editions associated to section endpoint

**Important**: We were not able to maps some predicate due to the structure of the json files. More precisely the following predicates have not been mapped:
* :hasTag associating a tag to a content
* :hasSponsor associating a sponsor to a tag
* :hasEdition associating an edition to a section


**RMLMAPPER** (https://github.com/RMLio/rmlmapper-java ): Starting from the previously described rules a set of RDF triples have been generated. For this we used the [rmlmapper.jar](./../rmlmapper-4.9.0.jar).
<br> *Command*: java -jar rmlmapper-4.9.0.jar.jar -m guardian_rules.rml.ttl -o output.rdf<br>

The several genearated output.rdf contains the RDF triples in N-triple format that can be used for building the Jena Model.<br>
**Important**: We have genearated different RDF triple files, all of them having the same URI scheme. 
The several RDF outputs have been added to the Jena and thanks to the Unique Name Assumption the same merge will be merged


**JENA MODEL**: Starting from the several RDF output containing the triples we built a Jena Model. The [Main.java](guardianNews/src/main/java/Main.java) class also contains several queries that can be exuted over the model.

**SPARQL QUERIES**: A set of queries which are meant to translate some competency questions that you can find in the following section
 have been implemented in the Main.java class.

# Competency Questions

* The webUrl of the tags
* The sponsors having sponsor name Google
* The twitter name of the authors
* The contents and their apiUrl having a section with title Football

# Future working

A future working is to try to fix the problems we found in mappings the several endpoints. 
Once the mappings is done, more interesting competency questions can be formulated, like:

* The contents having author scott murray
* The bio of authors which have written a content published in December 2020
* The contents having section with an edition name = "international"
* The sponsor logo of the tags of type  "series"

In the [Other interesting Queries](./Other interesting Queries.md) file you can find the SPARQL translation to these questions.