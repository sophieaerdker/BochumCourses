package npRatioSophie;

import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.ui.TCanvas;

public class NpRatioMain {

	// plot the np ratio depending on the temperature or the time - both versions
	// are not yet correct

	public static void main(String[] args) {

		NeutronProtonRatio npRatio = new NeutronProtonRatio();

		GraphErrors grA = new GraphErrors();
		GraphErrors grB = new GraphErrors();
		GraphErrors grC = new GraphErrors();
		GraphErrors grA1 = new GraphErrors();
		GraphErrors grB1 = new GraphErrors();
		GraphErrors grC1 = new GraphErrors();
		// TCanvas canvas = new TCanvas("neutron proton ratio depending on time", 800,
		// 800);
		TCanvas canvas1 = new TCanvas("neutron proton ratio depending on temperature", 800, 800);

		grA.setTitleX("log_10 of time t [s]");
		grA.setTitleY("log_10 of Neutron-Proton Ratio n/p");
		grA.setMarkerColor(2);
		grB.setMarkerColor(3);
		grC.setMarkerColor(1);
		grA.setMarkerSize(2);
		grB.setMarkerSize(2);
		grC.setMarkerSize(2);

		grA1.setTitleX("log_10 of temperature T [MeV]");
		grA1.setTitleY("log_10 of Neutron-Proton Ratio n/p");
		grA1.setMarkerColor(2);
		grB1.setMarkerColor(3);
		grC1.setMarkerColor(1);
		grA1.setMarkerSize(2);
		grB1.setMarkerSize(2);
		grC1.setMarkerSize(2);

		// T in MeV:
		double startT = 10;
		double limit_equilibrium = 0.68;
		double deltaT = 1e-3;
		double limitT = 1e-2;

		npRatio.setg(10.75);
		double start_time = npRatio.time(startT);
		double time_nd = npRatio.time(limit_equilibrium);
		double limit_time = npRatio.time(limitT);

		double np2 = npRatio.equilibrium_npRatio(limit_equilibrium);
		double np;
		double time;

		npRatio.setNp_ratio(np2);
		int i = 0;
		double np3 = 0;

		for (double T = startT; T > limitT; T -= deltaT) {

			np = npRatio.equilibrium_npRatio(T);
			npRatio.setnewg(T);
			time = npRatio.time(T);

			if (Math.log10(np) >= -1) {

				// System.out.println(Math.log10(np)); //
				// grA.addPoint(Math.log10(time), Math.log10(np), 0, 0);
				grA1.addPoint(Math.log10(T), Math.log10(np), 0, 0);
			}

			if (T <= limit_equilibrium) {

				np2 = npRatio.neutron_decay(0.01 * (time - time_nd));

				if (Math.log10(np2) >= -1) {

					// grB.addPoint(Math.log10(time), Math.log10(np2), 0, 0);
					grB1.addPoint(Math.log10(T), Math.log10(np2), 0, 0);
				}

				if (T <= 0.07 && i == 0) {

					np3 = np2;
					i = 1;

				} else if (i == 1) {

					// grC.addPoint(Math.log10(time), Math.log10(np3), 0, 0);
					grC1.addPoint(Math.log10(T), Math.log10(np3), 0, 0);
				}

			}

		}

		/*
		 * double T; npRatio.setg(10); int i = 0; double np3 = 0;
		 * 
		 * for (double t = start_time; t < limit_time; t += 10) {
		 * 
		 * T = npRatio.temperature(t); npRatio.setnewg(T); np =
		 * npRatio.equilibrium_npRatio(T); System.out.println(np);
		 * System.out.println(t);
		 * 
		 * if (Math.log10(np2) <= -2) {
		 * 
		 * // System.out.println(Math.log10(np)); grA.addPoint(Math.log10(t),
		 * Math.log10(np), 0, 0); grA1.addPoint(Math.log10(T), Math.log10(np), 0, 0);
		 * 
		 * if (T <= limit_equilibrium) {
		 * 
		 * np2 = npRatio.neutron_decay(0.01 * (t - time_nd));
		 * 
		 * grB.addPoint(Math.log10(t), Math.log10(np2), 0, 0);
		 * grB1.addPoint(Math.log10(T), Math.log10(np2), 0, 0);
		 * 
		 * if (T <= 0.07 && i == 0) {
		 * 
		 * np3 = np2; i = 1;
		 * 
		 * } else if (i == 1) {
		 * 
		 * grC.addPoint(Math.log10(t), Math.log10(np3), 0, 0);
		 * grC1.addPoint(Math.log10(T), Math.log10(np3), 0, 0); }
		 * 
		 * }
		 * 
		 * } else { break; } }
		 */

		// plot is not continuous because the factor g is changed:
		// canvas.draw(grA);
		// canvas.draw(grB, "same");
		// canvas.draw(grC, "same");

		// plot is reversed due to decrease in temperature with time:
		canvas1.draw(grA1);
		canvas1.draw(grB1, "same");
		canvas1.draw(grC1, "same");

	}

}
