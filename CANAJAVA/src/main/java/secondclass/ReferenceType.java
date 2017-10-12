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
package secondclass;

class AType {
	int myInt;
	double myDouble;
	char myChar;

}

public class ReferenceType {

	public static void main(String[] args) {
		AType myType = new AType();
		myType.myInt = 100;
		myType.myDouble = 123.456;
		myType.myChar = 'M';

		System.out.println("Integer Value of my data type is : " + myType.myInt);
		System.out.println("Double Value of my data type is : " + myType.myDouble);
		System.out.println("Char Value of my data type is : " + myType.myChar);

	}

}
