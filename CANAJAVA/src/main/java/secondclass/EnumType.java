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

enum Day {
	Sunday, Monday, Ttuesday, HumpDay, Thursday, Friday, Saturday
}

public class EnumType {

	public static void main(String[] args) {
		System.out.println("The day today is " + Day.Friday);

		boolean doesRun = false;
		boolean isCorrect = true;
		boolean isOriginal = true;
		double grade;

		if (isOriginal) {
			if (doesRun && isCorrect) {
				grade = 1.0;

			} else {
				grade = 0.5;
			}
		} else
			grade = 0.0;

		System.out.println(grade);
	}

}
