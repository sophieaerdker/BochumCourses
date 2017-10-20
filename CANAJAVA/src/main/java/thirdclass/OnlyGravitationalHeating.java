package thirdclass;

import domain.utils.Constants;

public class OnlyGravitationalHeating {

	public static void main(String[] args) {
		
	}

	public double radius() {
		double ageOfSun = Constants.ageOfSun * Constants.yearToSeconds;
		double solarLuminosity = Constants.solarLuminosity * Constants.ergToJoule;
		
		double radius = 3./10. * Constants.gravitationalConstant * Math.pow(Constants.massOfSun, 2) / (ageOfSun * solarLuminosity);
		
		return radius;
	}
}
