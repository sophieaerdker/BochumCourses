package domain;

import domain.utils.Constants;

public class EnergyEqualsMCSquared {

	public static double getEnergy(double mass) {
		return mass * Math.pow(Constants.speedOfLight, 2) / (Constants.eVToJoule * Constants.evToGeV);
	}

}
