
public class Triples {
	public String s;
	public String p;
	public String o;
	public Triples(String s, String p, String o) {
		this.s = s;
		this.p = p;
		this.o = o;
		
	}
	public String get() {
		return ("Subjekt: " + this.s + " Prädikat: " + this.p + " Objekt: " + this.o);
	}
	public String get (int part) {
		if (part == 0) {
			return this.s;
		} else if (part == 1) {
			return this.p;
		} else if (part == 2) {
			return this.o;
		} else {
			return null;		
		}
	}
}
