package homework6Sophie;

import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.ui.TCanvas;

import domain.utils.Constants;

public class CalculateHubbleConstant {

	public static void main(String[] args) {

		StellarObjects[] objects = new StellarObjects[6];
		GraphErrors gr = new GraphErrors();
		gr.setMarkerSize(4);
		gr.setTitleX("Distance [MPc]");
		gr.setTitleY("Velocity [1000 km/s]");
		gr.setTitle("Velocity vs. Distance for some stellar objects");

		objects[0] = new Bootes();
		objects[1] = new CoronaBorealis();
		objects[2] = new Hydra();
		objects[3] = new QuasarPC1247_3406();
		objects[4] = new UrsaMajor();
		objects[5] = new Virgo();

		double[] gradient = new double[6];
		double gradientSum = 0.0;

		for (int i = 0; i < 6; i++) {
			double d = objects[i].getDistance();
			double v = objects[i].getVelocity();

			gradient[i] = v / d;
			// System.out.println(gradient[i]);
			gradientSum += gradient[i];

			gr.addPoint(d, v / 1000, 0, 0);
		}

		TCanvas canvas = new TCanvas("Estimation of the Hubble Constant", 800, 800);
		canvas.draw(gr);

		double meanHubbleConstant = gradientSum / 6; // km/(s*MPc)
		System.out.println("The calculated mean Hubble Constant is: " + meanHubbleConstant + " km*s^(-1)/MPc");

		double ageOfUniverse = 1 / (meanHubbleConstant); // s*MPc/km = s*10^6*3,086e+13 km/km
		ageOfUniverse = 3.086 * Math.pow(10, 19) * ageOfUniverse / Constants.yearToSeconds; // years

		double ageOfUniverse_literature = 1.3799E10; // best estimate of the age with combined data, 15.11.27,
														// https://en.wikipedia.org/wiki/Age_of_the_universe

		System.out.println("The calculated age of the universe is: " + ageOfUniverse + " years");
		System.out.println("The approx. age of the universe is: " + ageOfUniverse_literature
				+ " years, in this model the cosmological constant is taken into account.");
		System.out.println("The deviation is about 10%");
	}

}
