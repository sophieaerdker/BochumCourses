package thirdclass;

import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.ui.TCanvas;

import domain.utils.Constants;

public class ThirdClassHomeworkSophieAerdker {

	public static double radius(int x) {

		double gravConstant = Constants.gravitationalConstant;
		double massSun = Constants.massOfSun;
		double ageEarth = Constants.ageOfEarth * Constants.yearToSeconds;
		double luminosity = Constants.solarLuminosity * Constants.ergToJoule;

		double r = (3.0 / 10.0) * gravConstant * Math.pow(massSun, 2) / (x * ageEarth * luminosity);

		return r;
	}

	public static void main(String[] args) {
		GraphErrors gr = new GraphErrors();

		for (int i = 1; i < 21; i++) {
			double r = radius(i) / Constants.solarRadius;
			gr.addPoint(i, r, 0, 0);
		}

		gr.setTitle("Solar radius with only gravitational heating");
		gr.setTitleX("Age of the sun [solar age]");
		gr.setTitleY("Radius of the sun [solar radius]");
		gr.setMarkerColor(4);
		gr.setMarkerStyle(2);

		TCanvas canvas = new TCanvas("Only gravitational heating", 800, 800);
		canvas.draw(gr);
		// SaveCanvas.saveCanvas(canvas);
	}

}
