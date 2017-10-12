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
package thirdclass;

public class MyMethods {

	public void printHello(String str, int x) {
		for (int i = 1; i <= x; i++) {
			System.out
					.println("I am in printHello. Input string was: " + str + " printing " + i + " of " + x + " times");
		}
	}

	public String printString() {
		String retString;

		retString = "Do you like green eggs and Ham?";

		return retString;
	}

	public void myOverLoad(int x) {
		System.out.println("first myOverload method");
	}

	public void myOverLoad(int x, double y) {
		System.out.println("second myOverload method");
	}

	public void myOverLoad(int x, double y, int z) {
		System.out.println("third myOverload method");
	}

	public static void main(String[] args) {
		MyMethods methods = new MyMethods();
		methods.printHello("RibEye Steak", 3);
		System.out.println(methods.printString());

		methods.myOverLoad(1);
		methods.myOverLoad(1, 0.0);
		methods.myOverLoad(1, 2.0, 2);
	}

}
