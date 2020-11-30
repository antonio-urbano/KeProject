import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import java.io.*;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args)  throws FileNotFoundException  {


        Model m = ModelFactory.createDefaultModel();
        InputStream is = FileManager.get().open( "./src/main/resources/covid_prova.rdf");

        m.read(is, null, "N-TRIPLE");


        InfModel mrdf = ModelFactory.createRDFSModel(m);

        Dataset ds = DatasetFactory.create();

        ds.addNamedModel("model", mrdf);
        ds.setDefaultModel(ds.getUnionModel());

        System.out.println("All the places in which there is at least 1 new ventilator");
        String q1 =  "SELECT ?place WHERE{ ?place a <https://schema.org/Place>. " +
                            "?statistics <http://www.prova.org/covid/statisticsOf> ?place." +
                            "?statistics a <http://www.prova.org/covid/CovidStatistics>." +
                            "?statistics <http://www.prova.org/covid/new_ventilator> ?nv." +
                            "FILTER(?nv > \"0\"^^<http://www.w3.org/2001/XMLSchema#integer>)." +
                            "}";
       Query query1 = QueryFactory.create(q1);

       QueryExecution exec1 = QueryExecutionFactory.create(query1, ds);
       ResultSet res1 = exec1.execSelect();
       ResultSetFormatter.out(System.out, res1, query1);

        //-------------------------------------------------------

        System.out.println("The average temperature and humidity of the subregion2 or locality in which the new confirmed>1000");

        String q2 =  "SELECT ?avg_temp ?humidity (STR(?date) AS ?Date) ?place ?new_confirmed WHERE{ " +
                "?weather a <http://www.prova.org/covid/WeatherStatistics>." +
                "?weather <http://www.prova.org/covid/avg_temp> ?avg_temp." +
                "?weather <http://www.prova.org/covid/humidity> ?humidity." +
                "?weather <http://www.prova.org/covid/weatherOf> ?place." +
                "?weather <http://www.prova.org/covid/date> ?date." +
                "?place a <https://schema.org/Place>. " +
                "?statistics <http://www.prova.org/covid/statisticsOf> ?place." +
                "?statistics <http://www.prova.org/covid/date> ?date." +
                "?statistics <http://www.prova.org/covid/aggregationLevel> ?al." +
                "?statistics <http://www.prova.org/covid/new_confirmed> ?new_confirmed." +
                "FILTER (?new_confirmed > \"1000\"^^<http://www.w3.org/2001/XMLSchema#integer>)." +
                "FILTER (?al >= \"2\"^^<http://www.w3.org/2001/XMLSchema#integer>).}";

        Query query2 = QueryFactory.create(q2);

        QueryExecution exec2 = QueryExecutionFactory.create(query2, ds);
        ResultSet res2 = exec2.execSelect();
       ResultSetFormatter.out(System.out, res2, query2);

        //-------------------------------------------------------

        System.out.println("The new deceased of the USA subregion2 having a population > 500000 people");

        String q3 =   "SELECT ?subregion2 ?new_deceased (STR(?date) AS ?Date) ?population WHERE{ " +
                "?subregion2 a <https://schema.org/Place>. " +
                "?subregion2 <http://www.prova.org/covid/name> ?region_name." +
                "?statistics <http://www.prova.org/covid/statisticsOf> ?subregion2." +
                "?statistics <http://www.prova.org/covid/aggregationLevel> \"2\"^^<http://www.w3.org/2001/XMLSchema#integer>." +
                "?statistics <http://www.prova.org/covid/new_deceased> ?new_deceased." +
                "?statistics <http://www.prova.org/covid/date> ?date." +
                "?subregion2 <http://www.prova.org/covid/population> ?population." +
                "FILTER (?population > \"500000\"^^<http://www.w3.org/2001/XMLSchema#integer>)." +
                "FILTER regex(?region_name, \"^United States of America \").}";
        Query query3 = QueryFactory.create(q3);

        QueryExecution exec3 = QueryExecutionFactory.create(query3, ds);
        ResultSet res3 = exec3.execSelect();
        ResultSetFormatter.out(System.out, res3, query3);


        //-------------------------------------------------------

        System.out.println("All the countries having a number of total confirmed > 5 mln");

        String q4 =  "SELECT ?country ?total_confirmed WHERE{ " +
                "?country a <https://schema.org/Place>. " +
                "?statistics <http://www.prova.org/covid/statisticsOf> ?country." +
                "?statistics <http://www.prova.org/covid/aggregationLevel> \"0\"^^<http://www.w3.org/2001/XMLSchema#integer>." +
                "?statistics <http://www.prova.org/covid/total_confirmed> ?total_confirmed." +
                "FILTER (?total_confirmed > \"5000000\"^^<http://www.w3.org/2001/XMLSchema#integer>).}";
        Query query4 = QueryFactory.create(q4);

        QueryExecution exec4 = QueryExecutionFactory.create(query4, ds);
        ResultSet res4 = exec4.execSelect();
        ResultSetFormatter.out(System.out, res4, query4);


        //-------------------------------------------------------

        System.out.println("New deceased, new confirmed and new recovered of all the countries having a min temp<0° or a max temp>30°");

        String q5 =   "SELECT ?country ?new_deceased ?new_confirmed ?new_recovered (STR(?date) AS ?Date) ?max_temp ?min_temp WHERE{ " +
                "?country a <https://schema.org/Place>. " +
                "?statistics <http://www.prova.org/covid/statisticsOf> ?country." +
                "?statistics <http://www.prova.org/covid/aggregationLevel> \"0\"^^<http://www.w3.org/2001/XMLSchema#integer>." +
                "?statistics <http://www.prova.org/covid/new_deceased> ?new_deceased." +
                "?statistics <http://www.prova.org/covid/new_confirmed> ?new_confirmed." +
                "?statistics <http://www.prova.org/covid/new_recovered> ?new_recovered." +
                "?statistics <http://www.prova.org/covid/date> ?date." +
                "?weather <http://www.prova.org/covid/weatherOf> ?country." +
                "?weather <http://www.prova.org/covid/date> ?date." +
                "?weather <http://www.prova.org/covid/min_temp> ?min_temp." +
                "?weather <http://www.prova.org/covid/max_temp> ?max_temp." +
                "FILTER (?max_temp > \"30\"^^<http://www.w3.org/2001/XMLSchema#decimal> || ?min_temp < \"0\"^^<http://www.w3.org/2001/XMLSchema#decimal>).}";

        Query query5 = QueryFactory.create(q5);

        QueryExecution exec5 = QueryExecutionFactory.create(query5, ds);
        ResultSet res5 = exec5.execSelect();
        ResultSetFormatter.out(System.out, res5, query5);

    }}