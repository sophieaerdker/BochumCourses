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

enum Month {
	January, February, March, April, May, June, July, August, September, October, November, December
}

public class Loops {
	public static void main(String[] args) {
		for (Month currentMonth : Month.values()) {
			System.out.println(currentMonth);
		}

		int i = 0;
		for (; i < 3;) {
			System.out.println("i = " + i);
			i++;
		}

		int k = 0;
		while (k < 5) {
			System.out.println("k = " + k);
			k++;
		}

		int l = 15;
		while (l < 5) {
			System.out.println("l = " + l);
			l++;
		}
		do {
			System.out.println("l = " + l);
			l++;
		} while (l < 5);
	}

}
