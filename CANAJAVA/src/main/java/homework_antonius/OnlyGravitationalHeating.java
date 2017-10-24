package homework_antonius;

import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.ui.TCanvas;
import org.jline.terminal.Size;

import domain.utils.Constants;

public class OnlyGravitationalHeating {

	public static void main(String[] args) {
		OnlyGravitationalHeating onlyGravitationalHeating = new OnlyGravitationalHeating();
		
		GraphErrors gr = new GraphErrors();

		
		for (int i = 1; i <= 20; i++) {
			System.out.println(onlyGravitationalHeating.radius(i));
			gr.addPoint((double) i, onlyGravitationalHeating.radius(i) / Constants.solarRadius, 0., 0.);
		}
		
		gr.setTitle("Theoretical solar radius if the sun relied only on gravitational heating.");
		gr.setTitleX("age / age of sun");
		gr.setTitleY("r / r_sun");
		
		TCanvas canvas = new TCanvas("plot", 640, 400);
		canvas.draw(gr);
	}
	
	public double radius() {
		return radius(1);
	}

	public double radius(int x) {
		double ageOfSun = Constants.ageOfSun * Constants.yearToSeconds;
		double solarLuminosity = Constants.solarLuminosity * Constants.ergToJoule;
		
		double radius = 3./10. * Constants.gravitationalConstant * Math.pow(Constants.massOfSun, 2) / (x * ageOfSun * solarLuminosity);
		
		return radius;
	}
}
