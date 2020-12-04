# Cryptocurrency Ontology

This ontology includes a set of classes and properties able to model daily time-series data related to COVID-19 for over 20,000 distinct locations around the world. modelled for crypto currency market data.

The data include the Price, volume, market cap, and rank for all currencies. It also change in cryptocurrency indicators calculated on an interval of 7 days. It also provides basic information about the cryptocurrencies. Details included in the data:

Cryprocurrency data: currency name, id, status, price, price date, symbol, circulating_supply, max_supply, logo url, market cap, number of exchanges, number of pairs, number of pairs unmapped, first candle date, first trade date, first order book date, first price date, rank, rank delta, high, high timestamp.

Cryprocurrency indicator change in 7 days interval:
    - Price change and Price change percentage ,Volume, Volume change and Volume change percentage, Market Cap and Market cap change percentage


## Data Linking

API REQUEST: The data is retrieved from nomics.com ticker api by a get request using the token provided after registration. Link for the API : https://api.nomics.com/v1/currencies/ticker

YARRRML (https://rml.io/yarrrml/matey/#): A set of rules has been written to fit the crypto ontology model. TheseR2RML rules have been written and parsed using the Yarrrml matey tool. An example of rules can be seen in the crypto.rml.ttl file.

RMLMAPPER (https://github.com/RMLio/rmlmapper-java ): The RDF triples have been generated from the rml rules. For this we used the rmlmapper.jar.
Command: java -jar rmlmapper-4.9.0.jar.jar -m crypto.rml.ttl -o crypto_output.rdf

crypto_output.rdf contains the genearated RDF triples in N-triple format. They can be used in the for building the Jena Model.

## Querying

JENA MODEL: Starting from the RDF triples we build a Jena Model. The Main.java class also contains several queries that can be exuted over the model.

SPARQL QUERIES: A set of queries which are meant to translate some competency questions that you can find in the following section have been implemented in the Main.java class. Furthermore the notebook CovidQueries.ipynb can be used to run the same queries. 
The Jupyter notebook docker stack was used to ceate the python notebook.
The sparqk kernel was installed using : https://github.com/paulovn/sparql-kernel
SPARQL endpoint was created using - Jena-Fuseki. 

## Competency Questions
List all currencies with price above 5000 Euros
List all currencies with status active and rank delta value above 0.
List all currencies with rank below 20
List all currencies with number of exchanges above 100
List all market cap, price and volume change in the last 7 days
