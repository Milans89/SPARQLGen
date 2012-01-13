import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


class Ontology { 
	ArrayList<Triples> Myontology = new ArrayList();
	public Ontology() {		
	}
	
	public Ontology(String string) {
		input(string);
	}

	void input(String filename) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String zeile = null;
			int zeilenr = 0;
			while ((zeile = in.readLine()) != null) {				
				String[] strings = zeile.split(",");
				Triples triple = new Triples(strings[0], strings[1], strings[2]);
				Myontology.add(triple);
				zeilenr++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean set (Triples trip) {
		if (Myontology.add(trip)) return true;
		else return false;
	}

	public String get (int triple, int position) {
		if (triple <= this.Myontology.size()) {
			return this.Myontology.get(triple).get(position);
		} else {
			return null;
		}
	}
	public void print() {
		int n = this.Myontology.size();
	    for(int i = 0; i < n ; i++)
	      System.out.println( this.Myontology.get(i).get() );

	}
	public String print (int triple) {
		String s = null;
		for (int i = 0; i < 3; i++) {
			if (s != null) {
				s += this.Myontology.get(triple).get(i);
			} else {
				s = this.Myontology.get(triple).get(i);
			}
			
		}
		return s;
	}
	
	public Triples get (int triple) {
		Triples trip = this.Myontology.get(triple);
		return trip;
		
	}
	public int size() {
		return this.Myontology.size();
	}
}