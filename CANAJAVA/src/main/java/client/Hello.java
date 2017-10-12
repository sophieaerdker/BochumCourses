/*  +__^_________,_________,_____,________^-.-------------------,
 *  | |||||||||   `--------'     |          |                   O
 *  `+-------------USMC----------^----------|___________________|
 *    `\_,---------,---------,--------------'
 *      / X MK X /'|       /'
 *     / X MK X /  `\    /'
 *    / X MK X /`-------'
 *   / X MK X /
 *  / X MK X /
 * (________(                @author m.c.kunkel
 *  `------'
*/
package client;

import domain.EnergyEqualsMCSquared;
import domain.utils.Constants;

public class Hello {
	public static void main(String[] args) {
		String myName = "Michael";
		int yearsOfJava = 5;
		int yearsOfProgramming = 11;

		double ageOfUniverse = Constants.ageOfUniverse;
		double ageOfEarth = 4.54e9; // years
		double hydrogenMass = EnergyEqualsMCSquared.getEnergy(Constants.massOfHydogen);
		System.out.println(
				"Hello CANUJAVA students. I am " + myName + ". I have " + yearsOfJava + " years programming in Java.");
		System.out.println("");
		System.out.println("I also have " + yearsOfProgramming + " years of computer programming.");
		System.out.println("");
		System.out.println("Did you know that the age of the universe is " + ageOfUniverse
				+ " years, while the earth is only " + ageOfEarth + " years.");
		System.out.println("");
		System.out.println(
				"With simple programming techiques, we will be able to calculate and plot various content that our ");
		System.out.println("evil overlords want us to learn.");
		System.out.println("");

		System.out.println("Such as converting the hydogen mass of " + Constants.massOfHydogen + "grams");
		System.out.println(" to GigaElectronVolts (GeV) of " + hydrogenMass + " GeV");
		System.out.println(
				"using a simple method call function of EnergyEqualsMCSquared.getEnergy(Constants.massOfHydogen);.");

	}

}
