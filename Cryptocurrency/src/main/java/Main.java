
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.datatypes.*;
import java.io.*;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args)  throws FileNotFoundException  {


        Model m = ModelFactory.createDefaultModel();
       // InputStream is = FileManager.getInternal().open( "./src/main/911_output.rdf");
        InputStream is = FileManager.getInternal().open( "./src/main/crypto_output.rdf");


            m.read(is, null, "N-TRIPLE");


        InfModel mrdf = ModelFactory.createRDFSModel(m);

        Dataset ds = DatasetFactory.create();

        ds.addNamedModel("model2", mrdf);
        ds.setDefaultModel(ds.getUnionModel());




//        String q = "SELECT ?Incident (STR(?x) AS ?Date) WHERE {" + " ?Incident <http://schema.org/DateTime> " +
//                " ?x.FILTER(?x >=  \"2020-10-31T21:35:00.000\"^^<http://www.w3.org/2001/XMLSchema#dateTime> &&" +
//                " ?x <=  \"2020-10-31T22:35:00.000\"^^<http://www.w3.org/2001/XMLSchema#dateTime>  )" +
//                "?Incident <http://observedchange.com/moac/ns/#Emergency>  \"Aid Response\" }";


 //       String q = "SELECT ?Incident ?Address WHERE {"+ "?Incident <http://schema.org/longitude> \"-122.318034\"." + "?Incident <http://schema.org/address> ?Address.}" ;

    //  String q = "SELECT ?Incident WHERE { ?Incident <http://schema.org/address> \"725 9th Av\".}";

//        String q = "SELECT ?Incident  WHERE { " + "?Incident <http://schema.org/GeoShape>  \"-122.325563\"." + " " +
//                " ?Incident <http://schema.org/GeoShape>  \"47.606444\". }";




//        String q = "SELECT ?Currency ?Price WHERE { ?Currency " +
//                "<http://www.semanticweb.org/athira/ontologies/2020/10/cryptocurrency#hasPrice> " +
//                "?Price.Filter(?Price > \"5000.00\"^^<http://www.w3.org/2001/XMLSchema#decimal>)}";


//        String q = "SELECT ?Name ?RankDelta WHERE { " + "?x <http://www.semanticweb.org/athira/ontologies/2020/10/cryptocurrency#Status> \"active\"."  +
//                "?Currency <http://www.semanticweb.org/athira/ontologies/2020/10/cryptocurrency#hasStatus> ?x." +
//                "?Currency <http://www.semanticweb.org/athira/ontologies/2020/10/cryptocurrency#rankDelta> " +
//                "?RankDelta.FILTER(?RankDelta > \"0\"^^<http://www.w3.org/2001/XMLSchema#integer> )"+"  ?Currency <http://www.semanticweb.org/athira/ontologies/2020/10/cryptocurrency#hasID> ?Name.}";

       String q = "SELECT ?Name ?Rank WHERE {?Currency <http://www.semanticweb.org/athira/ontologies/2020/10/cryptocurrency#rank> ?Rank.FILTER(?Rank <= \"20\"^^<http://www.w3.org/2001/XMLSchema#integer>)"+"  ?Currency <http://www.semanticweb.org/athira/ontologies/2020/10/cryptocurrency#hasID> ?Name. }";


       // String q = "SELECT ?Currency ?Exchanges WHERE {?Currency <http://www.semanticweb.org/athira/ontologies/2020/10/cryptocurrency#num_exchanges> ?Exchanges.FILTER(?Exchanges >= \"100\"^^<http://www.w3.org/2001/XMLSchema#integer>) }";

        //        String q = "SELECT ?Currency ?RankDelta WHERE { " + "?x <http://www.semanticweb.org/athira/ontologies/2020/10/cryptocurrency#Status> \"active\"."  +
//                "?Currency <http://www.semanticweb.org/athira/ontologies/2020/10/cryptocurrency#hasStatus> ?x." +
//                "?Currency <http://www.semantic\web.org/athira/ontologies/2020/10/cryptocurrency#rankDelta> " +
//                "?RankDelta.FILTER(?RankDelta > \"0\"^^<http://www.w3.org/2001/XMLSchema#integer> ) }";

        Query query = QueryFactory.create(q);

        QueryExecution exec = QueryExecutionFactory.create(query, ds);
        ResultSet res = exec.execSelect();
        ResultSetFormatter.out(System.out, res, query);
    }}