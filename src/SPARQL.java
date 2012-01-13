import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class SPARQL {
	Random randomgenerator = new Random();
	int Uni_size, Uni_size2;
	String owl = "http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#",modify = null,delete = null, insert = null, ask = null, select = null, where = null, Source = "localhost/RDF.xml", qrytype = null,rdftype=null, rdf3x = null;	
	public SPARQL(String q, String sl, String numberuni, Ontology onto) {
		Uni_size = Integer.parseInt(numberuni);
		qrytype = q;
		if (sl == "single" || sl.equals("single")) {
			int randomnumber = randomgenerator.nextInt(onto.size());
			Triples Triple = onto.get(randomnumber);
			if (qrytype == "select" || qrytype.equals("select") || qrytype == "describe" || qrytype.equals("describe")) {
				int specified = randomgenerator.nextInt(8);
				if (qrytype == "select" || qrytype.equals("select")) rdf3x = "SELECT ";
				else rdf3x = "DESCRIBE ";
				if (specified == 0) {
					select = "?" + Triple.get(2);
					where = "<" + specify(Triple.get(0)) + "> ub:" + Triple.get(1) + " ?" + Triple.get(2) + " . \n";
					rdf3x += select +" where { <" + specify(Triple.get(0)) + "> <"+owl + Triple.get(1) + "> ?" + Triple.get(2) + " .";
				} else if (specified == 1) {
					select = "?" + Triple.get(0);
					where = "?" + Triple.get(0) + " ub:" + Triple.get(1) + " <" + specify(Triple.get(2)) + "> . \n";
					rdf3x += select +" where { ?" + Triple.get(0) + " <"+owl + Triple.get(1) + "> <" + specify(Triple.get(2)) + "> .";
				} else if (specified == 2) {
					select = "*";
					where = "<" + specify(Triple.get(0)) + "> ub:" + Triple.get(1) + " <" + specify(Triple.get(2)) + "> . \n";
					rdf3x += select +" where { <" + specify(Triple.get(0)) + "> <"+owl + Triple.get(1) + "> <" + specify(Triple.get(2)) + "> .";
				} else if (specified == 3) { 
					select = "*";
					where = "<" + specify(Triple.get(0)) + "> ?Predicate <" + specify(Triple.get(2)) + "> . \n";
					rdf3x += select +" where { <" + specify(Triple.get(0)) + "> ?Predicate <" + specify(Triple.get(2)) + "> .";
				} else if (specified == 4) {
					select = "?" + Triple.get(2);
					where = "<" + specify(Triple.get(0)) + "> ?Predicate ?" + Triple.get(2) + " . \n";
					rdf3x += select +" where { <" + specify(Triple.get(0)) + "> ?Predicate ?" + Triple.get(2) + " .";
				} else if (specified == 5) {
					select = "?" + Triple.get(0);
					where = "?" + Triple.get(0) + " ?Predicate <" + specify(Triple.get(2)) + "> . \n";
					rdf3x += select +" where { ?" + Triple.get(0) + " ?Predicate <" + specify(Triple.get(2)) + "> .";
				}else {
					select = "*";
					where = "?" + Triple.get(0) + " ub:" + Triple.get(1) + " ?" + Triple.get(2) + " . \n";
					rdf3x += select +" where { ?" + Triple.get(0) + " <"+owl + Triple.get(1) + "> ?" + Triple.get(2) + " .";
				}
				rdftype = "?" + Triple.get(0) + " rdf:type ub:" + Triple.get(0) + " .\n?" + Triple.get(2) + " rdf:type ub:" + Triple.get(2) + " .\n";
				rdf3x += " }";
			} else if (qrytype == "ask" || qrytype.equals("ask")) {
				int specified = randomgenerator.nextInt(4);
				if (specified == 0) {
					ask = " ASK { <" + specify(Triple.get(0)) + "> ub:" + Triple.get(1) + " ?" + Triple.get(2) + " . }";
					rdf3x = "ASK { <" + specify(Triple.get(0)) + "> <"+owl + Triple.get(1) + "> ?" + Triple.get(2) + " . }";
				} else if (specified == 1) {
					ask = "ASK { ?" + Triple.get(0) + " ub:" + Triple.get(1) + " <" + specify(Triple.get(2)) + "> . }";
					rdf3x = "ASK { ?" + Triple.get(0) + " <"+owl + Triple.get(1) + "> <" + specify(Triple.get(2)) + "> . }";
				} else if (specified == 2) {
					ask = "ASK { <" + specify(Triple.get(0)) + "> ub:" + Triple.get(1) + " <" + specify(Triple.get(2)) + "> . }";
					rdf3x = "ASK { <" + specify(Triple.get(0)) + "> <"+owl + Triple.get(1) + "> <" + specify(Triple.get(2)) + "> . }";
				} else {
					ask = "ASK { ?" + Triple.get(0) + " ub:" + Triple.get(1) + " ?" + Triple.get(2) + " . }";
					rdf3x = "ASK { ?" + Triple.get(0) + " <"+owl + Triple.get(1) + "> ?" + Triple.get(2) + " . }";
				}
			} else if (qrytype == "insert" || qrytype.equals("insert") || qrytype == "delete" || qrytype.equals("delete")) {
				int random = randomgenerator.nextInt(onto.size());
				if (qrytype.equals("insert")) {
					insert = "INSERT DATA \n{ \n ";
					rdf3x = "INSERT DATA { ";
				}
				else if (qrytype.equals("delete")) {
					insert = "DELETE DATA \n{ \n ";
					rdf3x = "DELETE DATA { ";
				} else System.out.println("Wrong Querytype: " + qrytype); 
				insert += "<" + specify(onto.get(random).get(0)) + "> ub:" + onto.get(random).get(1) + " <" + specify(onto.get(random).get(2)) + "> . \n}";
				rdf3x += "<" + specify(onto.get(random).get(0)) + "> <"+owl + onto.get(random).get(1) + "> <" + specify(onto.get(random).get(2)) + "> . }";
			} else if (qrytype == "modify" || qrytype.equals("modify")) {
				modify = "MODIFY \nDELETE \n{";
				rdf3x = "MODIFY DELETE {";
				int random = randomgenerator.nextInt(3), stelle = randomgenerator.nextInt(onto.size());
				if (random == 0) {
					delete = "<" + specify(onto.get(stelle).get(0)) + "> ub:" + onto.get(stelle).get(1) + " <" + specify(onto.get(stelle).get(2)) + ">";
					insert = "<" + specify(onto.get(stelle).get(0)) + "> ub:" + onto.get(stelle).get(1) + " <" + specify(onto.get(stelle).get(2)) + ">";
				} else if (random == 1) {
					delete = "?" + onto.get(stelle).get(0) + " ub:" + onto.get(stelle).get(1) + " <" + specify(onto.get(stelle).get(2)) + ">";
					insert = "?" + onto.get(stelle).get(0) + " ub:" + onto.get(stelle).get(1) + " <" + specify(onto.get(stelle).get(2)) + ">";
				} else {
					delete = "<" + specify(onto.get(stelle).get(0)) + "> ub:" + onto.get(stelle).get(1) + " ?" + onto.get(stelle).get(2);
					insert = "<" + specify(onto.get(stelle).get(0)) + "> ub:" + onto.get(stelle).get(1) + " ?" + onto.get(stelle).get(2);
				}
				modify += " " + delete + " . }\nINSERT \n{ " + insert + " . }\nWHERE \n{ " + delete +" . }";
				rdf3x += " " + delete + " . } INSERT { " + insert + " . } WHERE { " + delete +" . }";
			} else System.out.println("Wrong Querytype: " + qrytype);
		} else if (sl == "linked" || sl.equals("linked")) {
			ArrayList excrdftype = new ArrayList(), speci = new ArrayList();
			if (qrytype == "select" || qrytype.equals("select")) {
				rdf3x = " where { ";
				//Ontology exists, now same connection as before?! do other connections exist? certainly yes ...
				// Describe one tupel after the other, afterwards, 25/75 chance to replace the class by specified() -> should work
				for (int i = 0; i < onto.size(); i++) {
					if (randomgenerator.nextInt(4) < 1) {
						if (randomgenerator.nextInt(2) < 1) {
							if (where != null) {
								where += "?" + onto.get(i).get(0) + " ?Predicate ?" + onto.get(i).get(2) + " . \n";
								rdf3x += "?" + onto.get(i).get(0) + " ?Predicate ?" + onto.get(i).get(2) + " . ";
							}
							else {
								where = "?" + onto.get(i).get(0) + " ?Predicate ?" + onto.get(i).get(2) + " . \n";
								rdf3x += "?" + onto.get(i).get(0) + " ?Predicate ?" + onto.get(i).get(2) + " . ";
							}
						} else {
							if (where != null) {
								where += "?" + onto.get(i).get(0) + " ub:" + onto.get(i).get(1) + " ?" + onto.get(i).get(2) + " . \n";
								rdf3x += "?" + onto.get(i).get(0) + " <"+owl + onto.get(i).get(1) + "> ?" + onto.get(i).get(2) + " . ";
							}
							else {
								where = "?" + onto.get(i).get(0) + " ub:" + onto.get(i).get(1) + " ?" + onto.get(i).get(2) + " . \n";
								rdf3x += "?" + onto.get(i).get(0) + " <"+owl + onto.get(i).get(1) + "> ?" + onto.get(i).get(2) + " . ";
							}
						}
						if (!speci.contains(onto.get(i).get(0))) speci.add(onto.get(i).get(0));
						if (!speci.contains(onto.get(i).get(2))) speci.add(onto.get(i).get(2));
					} else {
						int rndnum = randomgenerator.nextInt(3), rndnum2 = randomgenerator.nextInt(4);
						if (rndnum == 0) {
							if (where != null && (rndnum2 < 1)) {
								where += "<" + specify(onto.get(i).get(0)) + "> ?Predicate ?" + onto.get(i).get(2) + " . \n";
								rdf3x += "<" + specify(onto.get(i).get(0)) + "> ?Predicate ?" + onto.get(i).get(2) + " . ";
							} else if (where == null && (rndnum2 < 1)){
								where = "<" + specify(onto.get(i).get(0)) + "> ?Predicate ?" + onto.get(i).get(2) + " . \n"; 
								rdf3x += "<" + specify(onto.get(i).get(0)) + "> ?Predicate ?" + onto.get(i).get(2) + " . "; 
							} else if (where != null) {
								where += "<" + specify(onto.get(i).get(0)) + "> ub:" + onto.get(i).get(1) + " ?" + onto.get(i).get(2) + " . \n";
								rdf3x += "<" + specify(onto.get(i).get(0)) + "> <"+owl + onto.get(i).get(1) + "> ?" + onto.get(i).get(2) + " . ";
							} else {
								where = "<" + specify(onto.get(i).get(0)) + "> ub:" + onto.get(i).get(1) + " ?" + onto.get(i).get(2) + " . \n"; 
								rdf3x += "<" + specify(onto.get(i).get(0)) + "> <"+owl + onto.get(i).get(1) + "> ?" + onto.get(i).get(2) + " . "; 
							}
							if (!speci.contains(onto.get(i).get(2))) speci.add(onto.get(i).get(2));
						} else if (rndnum == 1) {
							if (where != null && (rndnum2 < 1)) {
								where += "?" + onto.get(i).get(0) + " ?Predicate <" + specify(onto.get(i).get(2)) + "> . \n";
								rdf3x += "?" + onto.get(i).get(0) + " ?Predicate <" + specify(onto.get(i).get(2)) + "> . ";
							} else if (where == null && (rndnum2 < 1)){
								where = "?" + onto.get(i).get(0) + " ub:" + onto.get(i).get(1) + " <" + specify(onto.get(i).get(2)) + "> . \n";
								rdf3x += "?" + onto.get(i).get(0) + " <"+owl + onto.get(i).get(1) + "> <" + specify(onto.get(i).get(2)) + "> . ";
							} else 	if (where != null) {
								where += "?" + onto.get(i).get(0) + " ub:" + onto.get(i).get(1) + " <" + specify(onto.get(i).get(2)) + "> . \n";
								rdf3x += "?" + onto.get(i).get(0) + " <"+owl + onto.get(i).get(1) + "> <" + specify(onto.get(i).get(2)) + "> . ";
							} else {
								where = "?" + onto.get(i).get(0) + " ub:" + onto.get(i).get(1) + " <" + specify(onto.get(i).get(2)) + "> . \n";
								rdf3x += "?" + onto.get(i).get(0) + " <"+owl + onto.get(i).get(1) + "> <" + specify(onto.get(i).get(2)) + "> . ";
							}
							if (!speci.contains(onto.get(i).get(0))) speci.add(onto.get(i).get(0));
						} else {
							if (where != null && (rndnum2 < 1)) {
								where += "<" + specify(onto.get(i).get(0)) + "> ?Predicate <" + specify(onto.get(i).get(2)) + "> . \n";
								rdf3x += "<" + specify(onto.get(i).get(0)) + "> ?Predicate <" + specify(onto.get(i).get(2)) + "> . ";
							} else if (where == null && (rndnum2 < 1)){
								where = "<" + specify(onto.get(i).get(0)) + "> ?Predicate <" + specify(onto.get(i).get(2)) + "> . \n";
								rdf3x += "<" + specify(onto.get(i).get(0)) + "> ?Predicate <" + specify(onto.get(i).get(2)) + "> . ";
							} if (where != null) {
								where += "<" + specify(onto.get(i).get(0)) + "> ub:" + onto.get(i).get(1) + " <" + specify(onto.get(i).get(2)) + "> . \n";
								rdf3x += "<" + specify(onto.get(i).get(0)) + "> <"+owl + onto.get(i).get(1) + "> <" + specify(onto.get(i).get(2)) + "> . ";
							} else {
								where = "<" + specify(onto.get(i).get(0)) + "> ub:" + onto.get(i).get(1) + " <" + specify(onto.get(i).get(2)) + "> . \n";
								rdf3x += "<" + specify(onto.get(i).get(0)) + "> <"+owl + onto.get(i).get(1) + "> <" + specify(onto.get(i).get(2)) + "> . ";
							}
						}
					}
					//Add new rdftypes; Already defined rdftypes have to be excluded
					if (!excrdftype.contains(onto.get(i).get(0))) {
						if (rdftype == null) {
							rdftype = "?" + onto.get(i).get(0) + " rdf:type ub:" + onto.get(i).get(0) + " .\n";
						} else {
							rdftype += "?" + onto.get(i).get(0) + " rdf:type ub:" + onto.get(i).get(0) + " .\n";

						}
						excrdftype.add(onto.get(i).get(0));
					} else if (!excrdftype.contains(onto.get(i).get(2))) {
						rdftype += "?" + onto.get(i).get(2) + " rdf:type ub:" + onto.get(i).get(2) + " .\n";
						excrdftype.add(onto.get(i).get(2));
					}
					
				}
				//add select parameters
				if (randomgenerator.nextInt(2) < 1) {
					select = "*";
				} else {
					for (int h = 0; h < onto.size(); h++) {
						if (randomgenerator.nextInt(onto.size()) < 1) {
							if (randomgenerator.nextInt(2) < 1 && speci.contains(onto.get(h).get(0))) {
								if (select != null) select += " ?" + onto.get(h).get(0) + " ";
								else select = " ?" + onto.get(h).get(0) + " ";
							} else if (randomgenerator.nextInt(2) < 1 && speci.contains(onto.get(h).get(2))) {
								if (select != null) select += " ?" + onto.get(h).get(2) + " ";
								else select = " ?" + onto.get(h).get(2) + " ";
							}
						}
					}
					if (select == null) select = "*";
				}
				 rdf3x = "select " + select + rdf3x;
				rdf3x += " }";
			} else if (qrytype == "ask" || qrytype.equals("ask")) {
				
			} else if (qrytype == "describe" || qrytype.equals("describe")) {
				
			} else if (qrytype == "modify" || qrytype.equals("modify")) {
				modify = "MODIFY \nDELETE \n{";
				rdf3x = "MODIFY DELETE {";
				for (int i = 0; i < onto.size(); i++) {
					int random = randomgenerator.nextInt(3);
					if (random == 0) {
						if (delete != null) {
							delete += "<" + specify(onto.get(i).get(0)) + "> ub:" + onto.get(i).get(1) + " <" + specify(onto.get(i).get(2)) + "> . \n";
							insert += "<" + specify(onto.get(i).get(0)) + "> ub:" + onto.get(i).get(1) + " <" + specify(onto.get(i).get(2)) + "> . \n";
						} else {
							delete = "<" + specify(onto.get(i).get(0)) + "> ub:" + onto.get(i).get(1) + " <" + specify(onto.get(i).get(2)) + "> . \n";
							insert = "<" + specify(onto.get(i).get(0)) + "> ub:" + onto.get(i).get(1) + " <" + specify(onto.get(i).get(2)) + "> . \n";
						}
					} else if (random == 1) {
						if (delete != null) {
							delete += "?" + onto.get(i).get(0) + " ub:" + onto.get(i).get(1) + " <" + specify(onto.get(i).get(2)) + "> . \n";
							insert += "?" + onto.get(i).get(0) + " ub:" + onto.get(i).get(1) + " <" + specify(onto.get(i).get(2)) + "> . \n";

						} else {
							delete = "?" + onto.get(i).get(0) + " ub:" + onto.get(i).get(1) + " <" + specify(onto.get(i).get(2)) + "> . \n";
							insert = "?" + onto.get(i).get(0) + " ub:" + onto.get(i).get(1) + " <" + specify(onto.get(i).get(2)) + "> . \n";
						}
					} else {
						if (delete != null) {
							delete += "<" + specify(onto.get(i).get(0)) + "> ub:" + onto.get(i).get(1) + " ?" + onto.get(i).get(2) + " . \n";
							insert += "<" + specify(onto.get(i).get(0)) + "> ub:" + onto.get(i).get(1) + " ?" + onto.get(i).get(2) + " . \n";

						} else {
							delete = "<" + specify(onto.get(i).get(0)) + "> ub:" + onto.get(i).get(1) + " ?" + onto.get(i).get(2) + " . \n";
							insert = "<" + specify(onto.get(i).get(0)) + "> ub:" + onto.get(i).get(1) + " ?" + onto.get(i).get(2) + " . \n";
						}
					}
				}
				modify += " " + delete + "}\nINSERT \n{ " + insert + "}\nWHERE \n{ " + delete +"}";
				rdf3x += " " + delete + " . } INSERT { " + insert + " . } WHERE { " + delete +" . }";	
			} else if (qrytype == "insert" || qrytype.equals("insert") || qrytype == "delete" || qrytype.equals("delete")) {
				if (qrytype.equals("insert")) {
					insert = "INSERT DATA \n{ \n ";
					rdf3x = "INSERT DATA { ";
				}
				else if (qrytype.equals("delete")) {
					insert = "DELETE DATA \n{ \n ";
					rdf3x = "DELETE DATA { ";
				} else System.out.println("Wrong Querytype: " + qrytype); 
				for (int i = 0; i < onto.size(); i++) {
					insert += "<" + specify(onto.get(i).get(0)) + "> ub:" + onto.get(i).get(1) + " <" + specify(onto.get(i).get(2)) + "> . \n";
					rdf3x += "<" + specify(onto.get(i).get(0)) + "> <"+owl + onto.get(i).get(1) + "> <" + specify(onto.get(i).get(2)) + "> . ";
				}
				insert += "}";
				rdf3x += "}";
			} else System.out.println("Wrong Querytype: " + qrytype);
			
		} else System.out.print("Wrong Linkness: " + sl);
		schreibe(rdf3x,sl);
	}
	
	private void schreibe(String text, String sl) {
		String dateiName = null;
	    FileOutputStream schreibeStrom;
	    int name = 0;
	    boolean exist;
	    dateiName = "3x" + sl + Uni_size + ".rq";
		try {
			schreibeStrom = new FileOutputStream(dateiName,false);
			for (int i=0; i < text.length(); i++){
		    	schreibeStrom.write((byte)text.charAt(i));
		    }
		    schreibeStrom.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String specify (String i) {
		Uni_size2 = (int) Math.round(Uni_size * 1.25);
		if (i.equals("University")) {
			return "http://www." + i + randomgenerator.nextInt(Uni_size2) + ".edu";
		} else if (i.equals("Department")) {
			return "http://www." + i + "." + randomgenerator.nextInt(26) + ".University" + randomgenerator.nextInt(Uni_size2) + ".edu";
		} else if (i.equals("FullProfessor")) {
			return "http://www.Department" + randomgenerator.nextInt(26) + ".University" + randomgenerator.nextInt(Uni_size2) + ".edu/" + i + randomgenerator.nextInt(10); 
		} else if (i.equals("AssociateProfessor")) {
			return "http://www.Department" + randomgenerator.nextInt(26) + ".University" + randomgenerator.nextInt(Uni_size2) + ".edu/" + i + randomgenerator.nextInt(14); 
		} else if (i.equals("AssistantProfessor")) {
			return "http://www.Department" + randomgenerator.nextInt(26) + ".University" + randomgenerator.nextInt(Uni_size2) + ".edu/" + i + randomgenerator.nextInt(11); 
		} else if (i.equals("Lecturer")) {
			return "http://www.Department" + randomgenerator.nextInt(26) + ".University" + randomgenerator.nextInt(Uni_size2) + ".edu/" + i + randomgenerator.nextInt(7); 
		} else if (i.equals("UndergraduateStudent")) {
			return "http://www.Department" + randomgenerator.nextInt(26) + ".University" + randomgenerator.nextInt(Uni_size2) + ".edu/" + i + randomgenerator.nextInt(600); 
		} else if (i.equals("GraduateStudent")) {
			return "http://www.Department" + randomgenerator.nextInt(26) + ".University" + randomgenerator.nextInt(Uni_size2) + ".edu/" + i + randomgenerator.nextInt(150); 
		} else if (i.equals("Course")) {
			return "http://www.Department" + randomgenerator.nextInt(26) + ".University" + randomgenerator.nextInt(Uni_size2) + ".edu/" + i + randomgenerator.nextInt(70); 
		} else if (i.equals("GraduateCourse")) {
			return "http://www.Department" + randomgenerator.nextInt(26) + ".University" + randomgenerator.nextInt(Uni_size2) + ".edu/" + i + randomgenerator.nextInt(70); 
		} else if (i.equals("ResearchGroup")) {
			return "http://www.Department" + randomgenerator.nextInt(26) + ".University" + randomgenerator.nextInt(Uni_size2) + ".edu/" + i + randomgenerator.nextInt(20); 
		} else if (i.equals("Publication")) {
			int h = randomgenerator.nextInt(4);
			if (h == 0) return "http://www.Department" + randomgenerator.nextInt(26) + ".University" + randomgenerator.nextInt(Uni_size2) + ".edu/FullProfessor" + randomgenerator.nextInt(10) + "/" + i + randomgenerator.nextInt(20);			
			else if (h == 1) return "http://www.Department" + randomgenerator.nextInt(26) + ".University" + randomgenerator.nextInt(Uni_size2) + ".edu/AssociateProfessor" + randomgenerator.nextInt(14) + "/" + i + randomgenerator.nextInt(18);			
			else if (h == 2) return "http://www.Department" + randomgenerator.nextInt(26) + ".University" + randomgenerator.nextInt(Uni_size2) + ".edu/AssistantProfessor" + randomgenerator.nextInt(11) + "/" + i + randomgenerator.nextInt(10);			
			else if (h == 3) return "http://www.Department" + randomgenerator.nextInt(26) + ".University" + randomgenerator.nextInt(Uni_size2) + ".edu/Lecturer" + randomgenerator.nextInt(7) + "/" + i + randomgenerator.nextInt(5);
		}
		return null;
	}
	public String query() {
		String qry = null;
		qry = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \nPREFIX ub: <" + owl + ">\n";
		if (qrytype.equals("insert") || qrytype.equals("delete")) qry += insert;
		if (qrytype.equals("select")) qry += "SELECT " + select +"\n";
		if (qrytype.equals("describe")) qry += "DESCRIBE " + select +"\n";
		if (qrytype.equals("ask")) qry += ask;
		if (qrytype.equals("modify")) qry +=modify;
		if (qrytype.equals("select") || qrytype.equals("describe")) qry += "WHERE { \n" + rdftype + where + "}";
		return qry;
	}
}
