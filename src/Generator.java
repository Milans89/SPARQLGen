import java.util.ArrayList;
import java.util.Random; 

public class Generator {
	
	public static void main(String[] args) { //Args: Single/Linked, qrytype, unicount, linkness
		int Anzahl = 1; // Anzahl Queries, die erstellt werden sollen
		Ontology ont = new Ontology(), triples = new Ontology("Ontology.txt");
		for (int i = 0; i < Anzahl; i++) {
			String type = "select";
			Random randomgenerator = new Random();
			int Linkness = 1, randomnumber = randomgenerator.nextInt(triples.size());
			if (args.length == 4) Linkness = Integer.parseInt(args[3]);
			if (Linkness >= 1) {
				ont.set(triples.get(randomnumber));
				ArrayList<Integer> exclude = new ArrayList<Integer>();
				exclude.add(randomnumber);
				for (int h = 0; h < triples.size(); h++) {
					ArrayList<Integer> include = new ArrayList<Integer>();
					for (int l = 0; l < ont.size(); l++){
						if (!exclude.contains(h) && Linkness >= exclude.size()) {
							boolean hit = false;
							if (triples.get(h,0).equals(ont.get(l,0))) {
								hit = true;
							} else if (triples.get(h,0).equals(ont.get(l,2))) {
								hit = true;
							} else if (triples.get(h,2).equals(ont.get(l,0))) {
								hit = true;
							} else if (triples.get(h,2).equals(ont.get(l,2))) {
								hit = true;
							} else hit = false;
							if (hit == true) {
								include.add(h);
							}
						}
					}
					//in include sind die stellen, die eine verbindung zu dem bisherigen triples haben und nicht schon in exclude enthalten sind
					//jetzt eine zuf�llige stelle von include w�hlen. diese stelle gibt die stelle in triples wieder, welche zu ont geadded werden muss.
					if (include.size() != 0) {
						int stelleininclude = randomgenerator.nextInt(include.size()); // Random letter from include
						int stelle = include.get(stelleininclude); // get random letter from include
						exclude.add(stelle); // add it to exclude
						ont.set(triples.get(stelle)); // add a part of triples to ont
					}
				}
			}
			SPARQL sparql = new SPARQL(args[1], args[0],args[2], ont);
			String qry = sparql.query();
			System.out.println(qry);
		}
	}
}