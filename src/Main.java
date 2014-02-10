import java.util.Scanner;

/**
 * Created by Vikrant on 2/4/14.
 */
class Main {
	public static void main(String[] args) {
		while (true) {
			TicTacToe obj = new TicTacToe();
			obj.play();
			System.out.print("press y to play again and n to quit: ");
			Scanner sc = new Scanner(System.in);
			String c = sc.next();
			if (!c.equals("y")) {
				break;
			}
		}

	}
}
