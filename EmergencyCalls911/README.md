# 911 Emergencycall Ontology

This ontology includes a set of classes and properties modelled for Emergency response calls data.

The data include the data about 911 dispatches.Details included in the data:

      Address	 - Location of Incident
      Response Type
      Datetime - The date and time of the call.
      Latitude	- This is the latitude value.
      Longitude - longitude value.
      Report Location	- (latitude, longitude)
      Incident Number

## Data Linking

API REQUEST: The data is retrieved from API provided by the Seattle Fire department. The data is downloaded in json file. 
Link for the API : https://data.seattle.gov/resource/fire-911.json

YARRRML (https://rml.io/yarrrml/matey/#): A set of rules has been written to fit the 911 ontology model. These R2RML rules have been written and parsed using the Yarrrml matey tool. An example of rules can be seen in the 911.rml.ttl file.

RMLMAPPER (https://github.com/RMLio/rmlmapper-java ): The RDF triples have been generated from the rml rules. For this we used the rmlmapper.jar.
Command: java -jar rmlmapper-4.9.0.jar.jar -m 911.rml.ttl -o 911_output.rdf

911_output.rdf contains the genearated RDF triples in N-triple format. They can be used in the for building the Jena Model.

## Querying

JENA MODEL: Starting from the RDF triples we build a Jena Model. The Main.java class also contains several queries that can be exuted over the model.

SPARQL QUERIES: A set of queries which are meant to translate some competency questions that you can find in the following section have been implemented in the Main.java class. Furthermore the notebook 911_query.ipynb can be used to run the same queries. 
The Jupyter notebook docker stack was used to ceate the python notebook.
The sparqk kernel was installed using : https://github.com/paulovn/sparql-kernel
SPARQL endpoint was created using - Jena-Fuseki. 

## Competency Questions
      - All incidents of response type AId response reported between two dates
      - All response types of incidents that happened in place with longitude "-122.318034"
      - All incident numbers of all incidents that happened at 517 3rd Av
      - All incident numbers of incidents that was reported from POINT(-122.228261 47.589118)

