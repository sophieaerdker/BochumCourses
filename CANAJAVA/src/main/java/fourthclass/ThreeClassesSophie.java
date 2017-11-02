package fourthclass;

public class ThreeClassesSophie {

	public static void main(String[] args) {

		// We only need getters because we do not want to change something in the three
		// classes -> all used variables (Z,N,m,...) are constants

		// the "first" three Isotopes of Hydrogen are calculated:
		HydrogenSophie hydrogen = new HydrogenSophie(3);
		// all Isotopes of helium are calculated:
		HeliumSophie helium = new HeliumSophie();
		// if x is greater than the number if possible isotopes, all possible isotopes
		// are calculated:
		LithiumSophie lithium = new LithiumSophie(12);

		System.out.println("Hydrogen has " + hydrogen.getNumberOfIsotopes() + " Isotopes. The three well-known are:");

		for (int i = 0; i < 3; i++) {
			System.out.println(hydrogen.getNaturalIsotopes()[i] + " with a mass of " + hydrogen.getMasses()[i] + " u ");
		}

		System.out.println("The stable Isotopes of Hydrogen are: " + hydrogen.getStableIsotopes()[0] + " and "
				+ hydrogen.getStableIsotopes()[1] + " and the most abundant Isotope is Hydrogen with N = "
				+ hydrogen.getMostAbundant_N());

		System.out.println("\nHelium with Z = " + helium.getZ() + " has two stable Isotopes: "
				+ helium.getStableIsotopes()[0] + " and " + helium.getStableIsotopes()[1] + ". The most abundant is: "
				+ helium.getStableIsotopes()[1] + " with N = " + helium.getMostAbundant_N()
				+ "\nThe smallest mass of helium has He-2 with approx. " + helium.getMasses()[0] + " u");

		System.out.println("\n Lithium has " + lithium.getZ() + " protons and two stable Isotopes named: "
				+ lithium.getStableIsotopes()[0] + " and " + lithium.getStableIsotopes()[1]
				+ ".\nThe most abundant Isotope is " + lithium.getStableIsotopes()[1] + " with N = "
				+ lithium.getMostAbundant_N() + ". The first four Isotopes have masses of approx.:");
		for (int i = 0; i < 4; i++) {
			System.out.println("Li-" + (i + 4) + " : " + lithium.getMasses()[i] + " u ");
		}

	}

}
