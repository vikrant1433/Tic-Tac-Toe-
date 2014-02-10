import java.util.Random;
import java.util.Scanner;

/**
 * Created by Vikrant on 2/3/14.
 */
class TicTacToe {
	private final Scanner sc = new Scanner(System.in);
	private final Random r = new Random();
	private int N = 3;
	private int numBoxFilled = 0;
	private final char[][] x;
	private final char player1Choice = 'X';
	private final char player2Choice = '#';
	private final char vacant = '_';
	private int currentFilledBox = -1;
	private String player1 = "player1";
	private String currTurn = player1;
	private String player2 = "player2";
	private final String computer = "pc";
	private final String computer1 = "pc1";
	private final String computer2 = "pc2";
	private char choice = 'X';

	public TicTacToe() {
		x = new char[N][N];
		for (byte i = 0; i < N; i++) {
			for (byte j = 0; j < N; j++) {
				x[i][j] = vacant;
			}
		}
	}

	private void menu() {
		makeChoice();
		makeDecision();
	}

	public void play() {
		menu();
		displayGrid();
		while (!isBoxFull()) {
			player1Turn();
			if (isSome1Won()) return;
			display();
			if (isBoxFull())
				break;
			player2Turn();
			if (isSome1Won()) return;
			display();
		}
		matchDrawn();
	}

	private void makeChoice() {
		System.out.println("1. single player");
		System.out.println("2. multi player");
		System.out.println("3. pc Vs pc");
	}

	private void makeDecision() {
		int choice = sc.nextInt();
		if (choice == 1) {
			System.out.print("Enter your name: ");
			player1 = sc.next();
			player2 = computer;
		} else if (choice == 2) {
			System.out.print("Enter player1 name:\t");
			player1 = sc.next();
			System.out.print("Enter player2 name:\t");
			player2 = sc.next();
		} else {
			player1 = computer1;
			player2 = computer2;
		}
	}

	private void turn() {
		if (currTurn.equals(computer) || currTurn.equals(computer1) || currTurn.equals(computer2)) {
			computerTurn();
		} else {
			System.out.print(currTurn + "'s turn : ");
			Scanner sc = new Scanner(System.in);
			int t = 0;
			while (true) {
				t = sc.nextInt();
				if (1 <= t && t <= N * N && isVacant(t))
					break;
				displayGrid();
				System.out.println("Invalid choice try again");
			}
			fillBox(currTurn, t);
		}
	}

	private void computerTurn() {
		int t = 1 + r.nextInt(N * N);
		while (!isVacant(t)) {
			t = 1 + r.nextInt(N * N);
		}
		fillBox(currTurn, t);
	}

	private void fillBox(String player, int t) {

		if (player.equals(player1)) {
			choice = player1Choice;
		} else {
			choice = player2Choice;
		}
		numBoxFilled++;
		currentFilledBox = t;
		markBox();
	}

	private void player2Turn() {
		currTurn = player2;
		turn();
	}

	private void player1Turn() {
		currTurn = player1;
		turn();
	}

	private void markBox() {
		x[getRow(currentFilledBox)][getColumn(currentFilledBox)] = choice;
	}

	private int getColumn(int t) {
		t--;
		return t % N;
	}

	private int getRow(int t) {
		t--;
		return t / N;
	}

	private boolean isVacant(int t) {
		return x[getRow(t)][getColumn(t)] == vacant;
	}

	private void displayGrid() {
		System.out.println("\n\n\n");
		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < x.length; j++) {
				int t = getBoxNumber(i, j);
				if (j != 0) {
					if (isVacant(t)) {
						System.out.print("\t" + t);
					} else {
						System.out.print("\t" + x[i][j]);
					}
				} else {
					if (isVacant(t)) {
						System.out.print("\t" + t);
					} else {
						System.out.print("\t" + x[i][j]);
					}
				}
			}
			System.out.println();
		}
	}

	private int getBoxNumber(int i, int j) {
		return i * N + j + 1;
	}

	private boolean isSome1Won() {
		if (isPlayerWin()) {
			won();
			return true;
		}
		return false;
	}

	private void display() {
		displayGrid();
		System.out.println(currTurn + "'s has marked " + currentFilledBox);
	}

	private void matchDrawn() {
		displayGrid();
		System.out.println("Match Drawn");
	}

	private void won() {
		displayGrid();
		System.out.println(currTurn + " has Won" + "\nGame Over");
	}

	private boolean isPlayerWin() {
		return isRowFull(currentFilledBox) || isColumnFull(currentFilledBox) || isDiagonalFull();
	}

	private boolean checkOffDiagonal() {
		char bottomCorner = x[N - 1][0];
		for (int i = 0; i < N; i++) {
			if (x[N - 1 - i][i] == vacant || x[N - i - 1][i] != bottomCorner)
				return false;
		}
		return true;
	}

	private boolean checkOnDiagonal() {
		char topCorner = x[0][0];
		for (int i = 0; i < N; i++) {
			if (x[i][i] == vacant || x[i][i] != topCorner)
				return false;
		}
		return true;
	}

	private boolean isDiagonalFull() {

		return checkOnDiagonal() || checkOffDiagonal();
	}

	private boolean isColumnFull(int currentFilledBox) {
		int column = getColumn(currentFilledBox);
		for (int i = 0; i < N - 1; i++) {
			if (x[i][column] != x[i + 1][column] || x[i][column] == vacant) {
				return false;
			}
		}
		return true;
	}

	private boolean isRowFull(int currentFilledBox) {
		int row = getRow(currentFilledBox);
		for (int j = 0; j < N - 1; j++) {
			if (x[row][j] != x[row][j + 1]) {
				return false;
			}
		}
		return true;
	}

	private boolean isBoxFull() {
		return numBoxFilled == N * N;
	}
}
