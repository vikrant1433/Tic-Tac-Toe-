import java.util.Random;
import java.util.Scanner;

/**
 * Created by Vikrant on 2/3/14.
 */
class TicTacToe {
	private final static char PLAYER_1_CHOICE = 'X';
	private static char CHOICE = PLAYER_1_CHOICE;
	private final static char PLAYER_2_CHOICE = '#';
	private final static char VACANT = '_';
	private final static Integer COMPUTER = 3;
	private final static Integer COMPUTER_1 = 4;
	private final static Integer COMPUTER_2 = 5;
	private static Integer player1 = 1;
	private static Integer currTurn = player1;
	private static Integer player2 = 2;
	private static String playerOneName = null;
	private static String playerTwoName = null;
	private final Scanner sc = new Scanner(System.in);
	private final Random r = new Random();
	private final int N = 3;
	private final char[][] x;
	private int numBoxFilled = 0;
	private int currentFilledBox = -1;

	public TicTacToe() {
		x = new char[N][N];
		for (byte i = 0; i < N; i++) {
			for (byte j = 0; j < N; j++) {
				x[i][j] = VACANT;
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
			turn();
			if (isPlayerWin()) {
				printWinningMessage();
				return;
			}
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
			playerOneName = sc.next();
			playerTwoName = "PC";
			player1 = 1;
			player2 = COMPUTER;
		} else if (choice == 2) {
			System.out.print("Enter player1 name:\t");
			player1 = 1;
			playerOneName = sc.next();
			System.out.print("Enter player2 name:\t");
			player2 = 2;
			playerTwoName = sc.next();
		} else {
			player1 = COMPUTER_1;
			playerOneName = "PC1";
			player2 = COMPUTER_2;
			playerTwoName = "PC2";
		}
	}

	private void turn() {

		if (currTurn.equals(player1))
			currTurn = player2;
		else
			currTurn = player1;

		if (currTurn.equals(COMPUTER) || currTurn.equals(COMPUTER_1) || currTurn.equals(COMPUTER_2)) {
			computerTurn();
		} else {
			System.out.print(getCurrTurnName() + "'s turn : ");
			Scanner sc = new Scanner(System.in);
			int t;
			while (true) {
				t = sc.nextInt();
				if (1 <= t && t <= N * N && isVacant(t))
					break;
				displayGrid();
				System.out.println("Invalid CHOICE try again");
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

	private void fillBox(Integer player, int t) {

		if (player.equals(player1)) {
			CHOICE = PLAYER_1_CHOICE;
		} else {
			CHOICE = PLAYER_2_CHOICE;
		}
		numBoxFilled++;
		currentFilledBox = t;
		markBox();
	}

	private void markBox() {
		x[getRow(currentFilledBox)][getColumn(currentFilledBox)] = CHOICE;
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
		return x[getRow(t)][getColumn(t)] == VACANT;
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

	private void display() {
		displayGrid();
		System.out.println(getCurrTurnName() + "'s has marked " + currentFilledBox);
	}

	private void matchDrawn() {
		displayGrid();
		System.out.println("Match Drawn");
	}

	private String getCurrTurnName() {
		return currTurn.equals(player1) ? playerOneName : playerTwoName;
	}

	private void printWinningMessage() {
		displayGrid();
		System.out.println(getCurrTurnName() + " has Won" + "\nGame Over");
	}

	private boolean isPlayerWin() {
		return isRowFull(currentFilledBox) || isColumnFull(currentFilledBox) || isDiagonalFull();
	}

	private boolean checkOffDiagonal() {
		char bottomCorner = x[N - 1][0];
		for (int i = 0; i < N; i++) {
			if (x[N - 1 - i][i] == VACANT || x[N - i - 1][i] != bottomCorner)
				return false;
		}
		return true;
	}

	private boolean checkOnDiagonal() {
		char topCorner = x[0][0];
		for (int i = 0; i < N; i++) {
			if (x[i][i] == VACANT || x[i][i] != topCorner)
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
			if (x[i][column] != x[i + 1][column] || x[i][column] == VACANT) {
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
