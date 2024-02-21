// Antoine CRETUAL, Lukian LEIZOUR, 21/02/2024

public class Situation {
	// Atributes 

	private int pos_i, pos_j, nb_choix;

	// Constructors

	public Situation(int i, int j, int n) {
		this.pos_i = i;
		this.pos_j = j;
		this.nb_choix = n;
	}

	// Methods

	public Situation copy(int n) {
		return new Situation(this.pos_i, this.pos_j, n);
	}
}
