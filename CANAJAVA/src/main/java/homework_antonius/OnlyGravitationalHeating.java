package homework_antonius;

import java.awt.Graphics;

import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.ui.TCanvas;

import domain.utils.Constants;

public class OnlyGravitationalHeating {

	public static void main(String[] args) {
		OnlyGravitationalHeating onlyGravitationalHeating = new OnlyGravitationalHeating();
		
		GraphErrors graphErrors = new GraphErrors();
		
		for (int i = 0; i <= 10; i++) {
			graphErrors.addPoint((double) i, onlyGravitationalHeating.radius(i), 0, 0);
		}
		
		TCanvas canvas = new TCanvas("plot", 640, 400);
		canvas.draw(graphErrors);
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
