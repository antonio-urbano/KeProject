import org.apache.jena.query.*;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import java.io.InputStream;

public class Main {
    public static void main(String[] args){


        Model m = ModelFactory.createDefaultModel();
        InputStream is1 = FileManager.get().open( "./src/main/resources/guardian_output1_source.rdf");
        InputStream is2 = FileManager.get().open( "./src/main/resources/guardian_output2_tags.rdf");
        InputStream is3 = FileManager.get().open( "./src/main/resources/guardian_output3_editions.rdf");
        InputStream is4 = FileManager.get().open( "./src/main/resources/guardian_output4_sections.rdf");

        if (is1 == null || is2==null || is3 == null || is4==null) {
            throw new IllegalArgumentException("File RDF not found.");
        } else {
            m.read(is1, null, "N-TRIPLE");
            m.read(is2, null, "N-TRIPLE");
            m.read(is3, null, "N-TRIPLE");
            m.read(is4, null, "N-TRIPLE");
        }


        InfModel mrdf = ModelFactory.createRDFSModel(m);

        Dataset ds = DatasetFactory.create();

        ds.addNamedModel("model", mrdf);
        ds.setDefaultModel(ds.getUnionModel());

        System.out.println("The webUrl of the tags");
        String q1 = "SELECT ?webUrl " +
                    "WHERE { ?tag a <http://www.example.org/guardian/Tag>." +
                            "?tag <http://www.example.org/guardian/webUrl> ?webUrl. }" +
                            "LIMIT 5";

       Query query1 = QueryFactory.create(q1);

       QueryExecution exec1 = QueryExecutionFactory.create(query1, ds);
       ResultSet res1 = exec1.execSelect();
       ResultSetFormatter.out(System.out, res1, query1);

        //-------------------------------------------------------7
        System.out.println("The sponsors having sponsor name Google");
        String q2 = "SELECT ?sponsor " +
                    "WHERE { ?sponsor a <http://www.example.org/guardian/Sponsor>. " +
                            "?sponsor <http://www.example.org/guardian/sponsorName> \"Google\". }";

        Query query2 = QueryFactory.create(q2);

        QueryExecution exec2 = QueryExecutionFactory.create(query2, ds);
        ResultSet res2 = exec2.execSelect();
        ResultSetFormatter.out(System.out, res2, query2);

        //-------------------------------------------------------7
        System.out.println("The twitter name of the authors");
        String q3 = "SELECT ?author ?twitter " +
                    "WHERE { ?author a <http://www.example.org/guardian/Tag>." +
                            "?author <http://www.example.org/guardian/authorTwitter> ?twitter. " +
                            "?author <http://www.example.org/guardian/tagType> \"contributor\". }" +
                            "LIMIT 5";

        Query query3 = QueryFactory.create(q3);

        QueryExecution exec3 = QueryExecutionFactory.create(query3, ds);
        ResultSet res3 = exec3.execSelect();
        ResultSetFormatter.out(System.out, res3, query3);

        //-------------------------------------------------------7
        System.out.println("The contents and their apiUrl having a section with title Football");
        String q4 = "SELECT ?content ?apiUrl " +
                "WHERE { ?content a <http://www.example.org/guardian/Content>." +
                "?content <http://www.example.org/guardian/hasSection> ?s." +
                "?s <http://www.example.org/guardian/apiUrl> ?apiUrl. }";

        Query query4 = QueryFactory.create(q4);

        QueryExecution exec4 = QueryExecutionFactory.create(query4, ds);
        ResultSet res4 = exec4.execSelect();
        ResultSetFormatter.out(System.out, res4, query4);
    }}