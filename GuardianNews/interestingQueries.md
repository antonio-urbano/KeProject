## Query 1: The contents having author scott murray

**PREFIX** guardian:<http://www.example.org/guardian/> <br>
<p>

**SELECT** ?content <br>
**WHERE** { ?content a guardian:Content. <br>
		?content guardian:hasTag ?t. <br>
		?t guardian:tagType "contributor". <br>
		?t guardian:firstName "scott". <br>
		?t guardian:lastName "murray". <br>
	}
	
---------------------------

## Query 2: The bio of authors which have written a content published in December 2020

**PREFIX** guardian:<http://www.example.org/guardian/> <br>
**PREFIX** xsd: <http://www.w3.org/2001/XMLSchema#> <br>
 <p>
 
**SELECT** ?author ?bio ?content <br>
**WHERE** { ?author a guardian:Tag. <br>
		?author guardian:tagType "contributor". <br>
		?author guardian:authorBio ?bio. <br>
		?content guardian:hasTag ?author. <br>
		?content guardian:webPublicationDate ?d. <br>
		**FILTER** (?d > "2020-12-01T00:00:01Z"^^xsd:dateTime && ?d < "2020-12-31T23:59:59Z"^^xsd:dateTime).
	}	
	
---------------------------------------------------------

## Query 3: The contents having section with an edition name = "international"

**PREFIX** guardian:<http://www.example.org/guardian/> <br>
<p>

**SELECT** ?content ?section <br>
**WHERE** { ?content a guardian:Content. <br>
		?author guardian:hasSection ?section. <br>
		?section guardian:hasEdition ?e. <br>
		?e guardian:editionName "international". <br>
	}
	
----------------------------------

## Query 4: The sponsor logo of the tags of type  "series"

**PREFIX** guardian:<http://www.example.org/guardian/> <br>

**SELECT** ?sponsorLogo <br>
**WHERE** { ?s a guardian:Sponsor. <br>
		?s guardian:sponsorLogo ?sponsorLogo. <br>
		?t a guardian:Tag. <br>
		?t guardian:hasSponsor ?s. <br>
		?t guardian:tagType "series" <br>
	}

-----------------------
