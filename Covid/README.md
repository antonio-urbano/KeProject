# Covid Ontology description

This ontology includes a set of classes and properties able to model 
daily time-series data related to COVID-19 for over 20,000 distinct locations around the world.<br>
<p>
The data is at the spatial resolution of states/provinces for most regions and at 
county/municipality resolution for many countries such as Argentina, Brazil, Chile, United Kingdom, and USA. 
All regions are assigned a unique location key. The different aggregation levels are:

0. *Country*
1. *Subregion1*:  Province, state, or local equivalent
2. *Subregion2*: Municipality, county, or local equivalent
3. *Locality*: which may not follow strict hierarchical order, such as "city"

For the modeling of the Place and the Country, the classes https://schema.org/Place and https://schema.org/Country have been respectively used. 
Then the class Subregion1, Subregion2 and Locality have been introduced to represent in our knowledge base the the places having a less aggregation level.<br>
To each place several statistics have been associated. Mainly we can distinguish three different types of statistics:

* WeatherStatistics: used for modeling the weather info. More precisely we have modeled the following properties:
	* Minimum, maximum and average temperature
	* Humidity
	* Rainfall
* LifestyleStatistics: to model statistics relative to the lifestyle of a place. Important properties are:
	* adult_female_mortality_rate
	* adult_male_mortality_rate
	* comorbidity_mortality_rate
	* infant_mortality_rate
	* life_expectancy
	* pollution_mortality_rate
* CovidStatistics: all the infomation relative to COVID-19. It represents the core of our model for which we model both:
	* statistics associated to the cases (e.g. the number of recovered, deceased, new confirmed, etc.); and
	* statistics associated to resources (e.g. new ventilator, hospital beds, etc.)

Each statistics is then identified by the place id to which is associated and the date in which it has been registered. <br>
Finally some properties related to the place population have been modeled. 
More precisely to each place may be defined the male and the female population and the population density.<br>


# Data Linking
**API REQUEST**: the infomation about places and their associated statistics is retrieved from a get call. 
The python script [downloadCovidCSV.py](downloadCovidCSV.py) can be used for this purpose. 
For more details about the dataset you can visit the following link:
https://github.com/GoogleCloudPlatform/covid-19-open-data/

**YARRRML** (https://rml.io/yarrrml/matey/#): A set of rules has been written to fit the covid ontology model. 
These rules have been written and parsed using the Yarrrml matey tool. An example of rules can be seen in the [covid_rml_rules.rml.ttl](covid_rml_rules.rml.ttl) file.

**RMLMAPPER** (https://github.com/RMLio/rmlmapper-java ): The RDF triples have been generated from the rml rules. For this we used the [rmlmapper.jar](./../rmlmapper-4.9.0.jar).
<br> *Command*: java -jar rmlmapper-4.9.0.jar.jar -m covid_rml_rules.rml.ttl -o output.rdf

[covid_output.rdf](covid_output.rdf) contains the genearated RDF triples in N-triple format. They can be used in the for building the Jena Model.<br>
**Important**: The original csv source file has encoding='cp1252'. In order to be used in Jena some manipulation of the data can be made, 
for example encoding it in 'UTF-8' format, or removing from the csv file the objects which contain characters not recognized from Jena.

**JENA MODEL**: Starting from the RDF triples we build a Jena Model. The [Main.java](kecovid/src/main/java/Main.java) class also contains several queries that can be exuted over the model.

**SPARQL QUERIES**: A set of queries which are meant to translate some competency questions that you can find in the following section
 have been implemented in the Main.java class. Furthermore the notebook [CovidQueries.ipynb](./CovidQueries.ipynb) can be used to run the same queries.
To use the notebook and run the queries you can self-host a SPARQL endpoint using [Jena-Fuseki](https://jena.apache.org/documentation/fuseki2/)

# Competency Questions

* All the places in which there is at least 1 new ventilator
* The average temperature and humidity of the subregion2 or locality in which the new confirmed>2000
* The new deceased of the USA subregion2 having a population > 1000000 people
* All the countries having a number of total confirmed > 2 mln
* New deceased and new confirmed of all the countries having a min temp<-10° or a max temp>+35°