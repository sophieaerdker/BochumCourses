package statStarSophie;

public class StatStarMain {

	public static void main(String[] args) {

		StatStar Star = new StatStar(1.0, 1.0, 5770, 0.75, 0.02);
		int zone = Star.Surface();

		if (zone != 0) {
			Star.mainCalculation(zone);
		}

	}

}
