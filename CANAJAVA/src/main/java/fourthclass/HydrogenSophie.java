package fourthclass;

import domain.utils.Constants;

class HydrogenSophie {

	int Z = 1;
	int mostAbundant_N = 0;
	int numberOfIsotopes = 7;
	String[] naturalIsotopes = { "Hydrogen", "Deuterium", "Tritium" };
	String[] stableIsotopes = { "Hydrogen", "Deuterium" };

	int[] N;
	double[] masses;

	HydrogenSophie(int x) {

		if (x > numberOfIsotopes) {
			x = numberOfIsotopes;
		}
		// create number of neutrons for all x Isotopes:
		this.N = new int[x];
		for (int i = 0; i < x; i++) {
			N[i] = i;
		}

		// use of SemiEmpiricalMassFormula to obtain approximate mass of each selected
		// Isotope in u:
		this.masses = new double[x];
		for (int i = 0; i < x; i++) {
			this.masses[i] = 1 / (931.494) * (Z + N[i]) * 1000 * Constants.massOfHydogenInGev
					- BindingEnergyHistogramSophie.BindingEnergy(Z, N[i]) / (Math.pow(Constants.speedOfLight, 2));
		}

	}

	HydrogenSophie() {

		int x = numberOfIsotopes;
		// create number of neutrons for all x Isotopes:
		this.N = new int[x];
		for (int i = 0; i < x; i++) {
			N[i] = i;
		}

		// use of SemiEmpiricalMassFormula to obtain approximate mass of each selected
		// Isotope in u:
		this.masses = new double[x];
		for (int i = 0; i < x; i++) {
			this.masses[i] = 1 / (931.494) * (Z + N[i]) * 1000 * Constants.massOfHydogenInGev
					- BindingEnergyHistogramSophie.BindingEnergy(Z, N[i]) / (Math.pow(Constants.speedOfLight, 2));
		}

	}

	public int getZ() {
		return Z;
	}

	public int getMostAbundant_N() {
		return mostAbundant_N;
	}

	public int getNumberOfIsotopes() {
		return numberOfIsotopes;
	}

	public String[] getNaturalIsotopes() {
		return naturalIsotopes;
	}

	public String[] getStableIsotopes() {
		return stableIsotopes;
	}

	public int[] getN() {
		return N;
	}

	public double[] getMasses() {
		return masses;
	}

}
