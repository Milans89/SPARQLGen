import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.sdb.*;
import com.hp.hpl.jena.sdb.sql.SDBConnectionFactory.*;
import com.hp.hpl.jena.sdb.sql.*;
import com.hp.hpl.jena.sdb.store.*;
import com.hp.hpl.jena.update.GraphStore;
import com.hp.hpl.jena.update.GraphStoreFactory;
import com.hp.hpl.jena.update.UpdateAction;


public class JENASDB {
	public static void main(String[] args) {	//Args: Query, qrytype, sdb.ttl/sdb.ttl2
		String queryString = null;
        Store store = SDBFactory.connectStore(args[2]) ;
        Dataset ds = DatasetStore.create(store) ;
		try {
			BufferedReader in = new BufferedReader(new FileReader(args[0]));
			String zeile = null;
			while ((zeile = in.readLine()) != null) {				
				if (queryString != null) {
					queryString += zeile; 
				} else {
					queryString = zeile;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Kein Query angegeben");
			queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> SELECT ?FullProfessor WHERE { ?FullProfessor rdf:type ub:FullProfessor . ?GraduateCourse rdf:type ub:GraduateCourse . ?FullProfessor ub:teacherOf <http://www.Department1.University0.edu/GraduateCourse62> . }" ;
		}
        if (args[1].equals("select") || args[1].equals("ask")) {
    		Query query = QueryFactory.create(queryString) ;
            QueryExecution qe = QueryExecutionFactory.create(query, ds) ;
            try {
                ResultSet rs = qe.execSelect() ;
                ResultSetFormatter.out(rs) ;
            } finally { qe.close() ; }
            store.getConnection().close() ;
            store.close() ;
        } else if (args[1].equals("insert") || args[1].equals("delete") || args[1].equals("modify")) {
        	GraphStore graphStore = GraphStoreFactory.create(ds) ;
        	UpdateAction.readExecute("/home/milan/"+args[0], graphStore) ;

        }
	}
}
