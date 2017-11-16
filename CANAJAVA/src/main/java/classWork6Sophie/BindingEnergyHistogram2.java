package classWork6Sophie;

import org.jlab.groot.data.H2F;
import org.jlab.groot.ui.TCanvas;

public class BindingEnergyHistogram2 {

	// calculating binding energy of known isotopes in the "Element" class
	// from Hydrogen until Nitrogen:

	public static void main(String[] args) {

		H2F ah2F = new H2F("Binding Energy Histogram", 10, 1, 10, 10, 1, 10);
		int limitA = 10;

		Element[] element = new Element[7];
		element[0] = new Hydrogen();
		element[1] = new Helium();
		element[2] = new Lithium();
		element[3] = new Beryllium();
		element[4] = new Boron();
		element[5] = new Carbon();
		element[6] = new Nitrogen();

		for (int i = 0; i < 7; i++) {

			int Z = i + 1;
			int maxA = element[i].getMaxA();
			int minA = element[i].getMinA();
			// System.out.println(maxA);

			if (maxA > limitA) {
				maxA = limitA;
			}

			for (int A = minA; A <= maxA; A++) {

				double BE = element[i].BindingEnergy(A);
				ah2F.setBinContent(Z, A - Z, BE);
			}
		}

		TCanvas canvas = new TCanvas("Binding Energy of known Isotopes with A < 10", 800, 800);
		canvas.draw(ah2F);
	}

}
