import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import java.io.*;

public class Main {
    public static void main(String[] args){


        Model m = ModelFactory.createDefaultModel();
        InputStream is = FileManager.get().open( "./src/main/resources/covid_output.rdf");

        if (is == null) {
            throw new IllegalArgumentException("File RDF not found.");
        } else {
            m.read(is, null, "N-TRIPLE");
        }


        InfModel mrdf = ModelFactory.createRDFSModel(m);

        Dataset ds = DatasetFactory.create();

        ds.addNamedModel("model", mrdf);
        ds.setDefaultModel(ds.getUnionModel());

        System.out.println("All the places in which there is at least 1 new ventilator");

        String q1 = "SELECT ?place (?nv AS ?New_ventilator) (STR(?date) AS ?Date)" +
                    "WHERE{ ?place a <https://schema.org/Place>. " +
                            "?statistics <http://www.example.org/covid/statisticsOf> ?place." +
                            "?statistics <http://www.example.org/covid/date> ?date." +
                            "?statistics a <http://www.example.org/covid/CovidStatistics>." +
                            "?statistics <http://www.example.org/covid/new_ventilator> ?nv." +
                            "FILTER(?nv > \"0\"^^<http://www.w3.org/2001/XMLSchema#integer>)." +
                            "}";
       Query query1 = QueryFactory.create(q1);

       QueryExecution exec1 = QueryExecutionFactory.create(query1, ds);
       ResultSet res1 = exec1.execSelect();
       ResultSetFormatter.out(System.out, res1, query1);

        //-------------------------------------------------------

        System.out.println("The average temperature and humidity of the subregion2 or locality in which the new confirmed>2000");

        String q2 = "SELECT ?avg_temp ?humidity (STR(?date) AS ?Date) ?place ?new_confirmed " +
                    "WHERE{ ?weather a <http://www.example.org/covid/WeatherStatistics>." +
                            "?weather <http://www.example.org/covid/avg_temp> ?avg_temp." +
                            "?weather <http://www.example.org/covid/humidity> ?humidity." +
                            "?weather <http://www.example.org/covid/weatherOf> ?place." +
                            "?weather <http://www.example.org/covid/date> ?date." +
                            "?place a <https://schema.org/Place>. " +
                            "?statistics <http://www.example.org/covid/statisticsOf> ?place." +
                            "?statistics <http://www.example.org/covid/date> ?date." +
                            "?statistics <http://www.example.org/covid/aggregationLevel> ?al." +
                            "?statistics <http://www.example.org/covid/new_confirmed> ?new_confirmed." +
                            "FILTER (?new_confirmed > \"2000\"^^<http://www.w3.org/2001/XMLSchema#integer>)." +
                            "FILTER (?al >= \"2\"^^<http://www.w3.org/2001/XMLSchema#integer>).}";

        Query query2 = QueryFactory.create(q2);

        QueryExecution exec2 = QueryExecutionFactory.create(query2, ds);
        ResultSet res2 = exec2.execSelect();
       ResultSetFormatter.out(System.out, res2, query2);

        //-------------------------------------------------------

        System.out.println("The new deceased of the USA subregion2 having a population > 1000000 people");

        String q3 = "SELECT ?subregion2 ?new_deceased (STR(?date) AS ?Date) ?population " +
                    "WHERE{ ?subregion2 a <https://schema.org/Place>. " +
                            "?subregion2 <https://schema.org/name> ?region_name." +
                            "?statistics <http://www.example.org/covid/statisticsOf> ?subregion2." +
                            "?statistics <http://www.example.org/covid/aggregationLevel> \"2\"^^<http://www.w3.org/2001/XMLSchema#integer>." +
                            "?statistics <http://www.example.org/covid/new_deceased> ?new_deceased." +
                            "?statistics <http://www.example.org/covid/date> ?date." +
                            "?subregion2 <http://www.example.org/covid/population> ?population." +
                            "FILTER (?population > \"1000000\"^^<http://www.w3.org/2001/XMLSchema#integer>)." +
                            "FILTER regex(?region_name, \"^United States of America \").}";
        Query query3 = QueryFactory.create(q3);

        QueryExecution exec3 = QueryExecutionFactory.create(query3, ds);
        ResultSet res3 = exec3.execSelect();
        ResultSetFormatter.out(System.out, res3, query3);


        //-------------------------------------------------------

        System.out.println("All the countries having a number of total confirmed > 2 mln");

        String q4 = "SELECT ?country ?total_confirmed (STR(?date) AS ?Date)" +
                    "WHERE{ ?country a <https://schema.org/Place>. " +
                            "?statistics <http://www.example.org/covid/statisticsOf> ?country." +
                            "?statistics <http://www.example.org/covid/date> ?date." +
                            "?statistics <http://www.example.org/covid/aggregationLevel> \"0\"^^<http://www.w3.org/2001/XMLSchema#integer>." +
                            "?statistics <http://www.example.org/covid/total_confirmed> ?total_confirmed." +
                            "FILTER (?total_confirmed > \"2000000\"^^<http://www.w3.org/2001/XMLSchema#integer>).}";
        Query query4 = QueryFactory.create(q4);

        QueryExecution exec4 = QueryExecutionFactory.create(query4, ds);
        ResultSet res4 = exec4.execSelect();
        ResultSetFormatter.out(System.out, res4, query4);


        //-------------------------------------------------------

        System.out.println("New deceased and new confirmed of all the countries having a min temp<-10° or a max temp>+35°");

        String q5 = "SELECT ?country ?new_deceased ?new_confirmed (STR(?date) AS ?Date) ?max_temp ?min_temp " +
                    "WHERE{ ?country a <https://schema.org/Place>. " +
                            "?statistics <http://www.example.org/covid/statisticsOf> ?country." +
                            "?statistics <http://www.example.org/covid/aggregationLevel> \"0\"^^<http://www.w3.org/2001/XMLSchema#integer>." +
                            "?statistics <http://www.example.org/covid/new_deceased> ?new_deceased." +
                            "?statistics <http://www.example.org/covid/new_confirmed> ?new_confirmed." +
                            "?statistics <http://www.example.org/covid/date> ?date." +
                            "?weather <http://www.example.org/covid/weatherOf> ?country." +
                            "?weather <http://www.example.org/covid/date> ?date." +
                            "?weather <http://www.example.org/covid/min_temp> ?min_temp." +
                            "?weather <http://www.example.org/covid/max_temp> ?max_temp." +
                            "FILTER (?max_temp > \"35\"^^<http://www.w3.org/2001/XMLSchema#decimal> || ?min_temp < \"-10\"^^<http://www.w3.org/2001/XMLSchema#decimal>).}";

        Query query5 = QueryFactory.create(q5);

        QueryExecution exec5 = QueryExecutionFactory.create(query5, ds);
        ResultSet res5 = exec5.execSelect();
        ResultSetFormatter.out(System.out, res5, query5);

    }}