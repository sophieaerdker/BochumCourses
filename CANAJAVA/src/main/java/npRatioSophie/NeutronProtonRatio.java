package npRatioSophie;

public class NeutronProtonRatio {

	public NeutronProtonRatio() {
		// TODO Auto-generated constructor stub
	}

	private double g; // 3// 10 // 100
	// effective number of degreees of freedom for relat. particles
	private double np_ratio;

	public void setNp_ratio(double np_ratio) {

		// set the initial ratio for the free neutron decay:
		this.np_ratio = np_ratio;
	}

	public void setnewg(double temperature) {

		if (temperature >= 3) {
			this.g = 10;

		} else {
			this.g = 3;
		}
	}

	public void setg(double g) {
		this.g = g;
	}

	public double temperature(double t) {

		return 1.82e10 * Math.pow(g, -0.25) * 1 / Math.sqrt(t) * 8.617e-5 * 10e-6; //
		// with boltzmann constant in eV/K
		// -->
		// Temperature in MeV

		// without factor g --> to get a continuously relation between time and
		// temperature:

		// return 1.52e10 * 1.0 / Math.sqrt(t) * 8.617e-5 * 10e-6;
	}

	public double time(double Temperature) {

		return Math.pow(1.82e10 * 8.617e-5 * 10e-6 / Temperature, 2) * Math.pow(g, -0.5);

		// withput g:
		// return Math.pow(1.52e10 * 8.617e-5 * 10e-6 / Temperature, 2);
	}

	public double equilibrium_npRatio(double T) {

		// n / p:

		return Math.exp(-1.293 / T); // with (m_p - m_n)c^2 in MeV und temperature in MeV
	}

	public double neutron_decay(double t) {

		// n / p
		// with:
		// n_n = n_0n * exp(-t/tau)
		// n_p = n_0p + n_0n * (1- exp(-t/tau) )

		double tau = 614; // s

		return 1 / (Math.exp(t / tau) * (1 / np_ratio + 1) - 1);
	}

}
