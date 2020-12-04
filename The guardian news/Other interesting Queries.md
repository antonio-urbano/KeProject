## Query 1: The contents having author scott murray

PREFIX guardian:<http://www.example.org/guardian/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>

SELECT ?content
WHERE { ?content a guardian:Content.
		?content guardian:hasTag ?t.
		?t guardian:tagType "contributor".
		?t guardian:firstName "scott".
		?t guardian:lastName "murray".		
	}

---------------------------

## Query 2: The bio of authors which have written a content published in December 2020

PREFIX guardian:<http://www.example.org/guardian/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>

SELECT ?author ?bio ?content
WHERE { ?author a guardian:Tag.
		?author guardian:tagType "contributor".
		?author guardian:authorBio ?bio.
		?content guardian:hasTag ?author.
		?content guardian:webPublicationDate ?d.
		FILTER (?d > "2020-12-01T00:00:01Z"^^xsd:dateTime && ?d < "2020-12-31T23:59:59Z"^^xsd:dateTime).
	}	
	
---------------------------------------------------------

## Query 3: The contents having section with an edition name = "international"

PREFIX guardian:<http://www.example.org/guardian/>


SELECT ?content ?section
WHERE { ?content a guardian:Content.
		?author guardian:hasSection ?section.
		?section guardian:hasEdition ?e.
		?e guardian:editionName "international".
		
----------------------------------

## Query 4: The sponsor logo of the tags of type  "series"

PREFIX guardian:<http://www.example.org/guardian/>

SELECT ?sponsorLogo
WHERE { ?s a guardian:Sponsor.
		?s guardian:sponsorLogo ?sponsorLogo.
		?t a guardian:Tag.
		?t guardian:hasSponsor ?s.
		?t guardian:tagType "series"
	}

-----------------------