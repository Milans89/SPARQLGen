import java.io.*;
import com.hp.hpl.jena.query.* ;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.update.*;

public class JENAARQ {
	public static void main(String[] args) { //Args: Query, qrytype, data, lang
		String lang = args[3];
		InputStream data;
		Model model = null;
		try {
			data = new FileInputStream(new File(args[2]));
			model = ModelFactory.createMemModelMaker().createDefaultModel();
			model.read(data,null,lang);
			data.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String queryString = null ;
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
		if (args[1].equals("select")) {
			Query query = QueryFactory.create(queryString) ;
			QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
			ResultSet results = qexec.execSelect() ;
			ResultSetFormatter.out(System.out, results, query);
			qexec.close() ;
		} else if (args[1].equals("insert") || args[1].equals("delete") || args[1].equals("modify")) {
        	GraphStore graphStore = GraphStoreFactory.create(model) ;
        	UpdateAction.readExecute("/home/milan/"+args[0], graphStore) ;
        	OutputStream out;
			try {
				out = new FileOutputStream(new File(args[2]), false);
	        	model.write(out,lang);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
	}
}
