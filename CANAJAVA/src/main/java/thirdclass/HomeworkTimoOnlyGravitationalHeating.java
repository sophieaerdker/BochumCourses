package thirdclass;

import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.ui.TCanvas;

import domain.utils.Constants;
import domain.utils.SaveCanvas;

public class HomeworkTimoOnlyGravitationalHeating {

	static public double returnRadius(double tsun) {
		// input: multiple of current solar age
		// output: solar radius in percent of actual radius assuming gravitational
		// heating

		double G = Constants.gravitationalConstant;
		double M = Constants.massOfSun;
		double L = Constants.solarLuminosity * Constants.ergToJoule;
		double t = tsun * Constants.ageOfSun * Constants.yearToSeconds;
		double R = Constants.solarRadius;

		double retValue = 3. / 10. * G * Math.pow(M, 2) / (t * L) / R * 100;

		return retValue;
	}

	static public void plotRadius() {
		// plot the solar radius assuming only gravitational heating for 1<t/t_sun<20
		// save plot as .pdf

		GraphErrors gr = new GraphErrors();

		for (double i = 1.; i <= 20.; i++) {
			gr.addPoint(i, returnRadius(i), 0, 0);
		}

		gr.setTitle("Solar Radius with only gravitational heating");
		gr.setTitleX("Solar age in units of current age");
		gr.setTitleY("Solar radius in % of actual solar radius");
		gr.setMarkerColor(2);
		gr.setMarkerStyle(2);

		TCanvas canv = new TCanvas("SolarRadiuswithonlygravitationalheatingTimo", 800, 600);

		canv.draw(gr);
		SaveCanvas.saveCanvas(canv);

		canv.dispose(); // shows empty window otherwise
	}

	public static void main(String[] args) {
		plotRadius();
	}
}
