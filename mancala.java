import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;

public class mancala{

	public static int[][] find_moves(boolean[] board) {
		int n = board.length;

		int[][] moves = new int[2 * n][2];

		for (int num = 0; num < (2 * n); num++) {
			moves[num][0] = -1;
		}

		int moves_index = 0;

		for (int i = 0; i < (n - 2); i++) {
			//check if a move is possible
			if ((board[i] == true) && (board[i + 1] == true) && (board[i + 2] == false)) {
				//System.out.println("Found a possible move! Start = " + i + ". End = " + (i + 2) + ".");
				//System.out.println("For the move, board[start] is " + board[i] + ". board[end] = " + board[(i + 2)] + ".");
				moves[moves_index][0] = i;
				moves[moves_index++][1] = i + 2;
			}
		}

		for (int i = (n - 1); i >= 2; i--) {
			//check if a move is possible
			if ((board[i] == true) && (board[i - 1] == true) && (board[i - 2] == false)) {
				//System.out.println("Found a possible move! Start = " + i + ". End = " + (i + 2) + ".");
				//System.out.println("For the move, board[start] is " + board[i] + ". board[end] = " + board[(i - 2)] + ".");
				moves[moves_index][0] = i;
				moves[moves_index++][1] = i - 2;
			}
		}

		return moves;
	}

	public static boolean[] changeBoard(int start, int end, boolean[] board) {
		int n = board.length;
		boolean[] new_board = new boolean[n];
		for (int i = 0; i < n; i++) {
			new_board[i] = board[i];
		}

			if (start < end) {
				//System.out.println("Changing board if, start = " + start + ". end = " + end);
				new_board[start] = false;
				new_board[start + 1] = false;
				new_board[end] = true;
			}
			else {
				//System.out.println("Changing board else, start = " + start + ". end = " + end);
				new_board[start] = false;
				new_board[start - 1] = false;
				new_board[end] = true;
			}

		return new_board;
	}

	public static int execute(boolean[] board) {
		int n = board.length;
		int[][] moves = new int[2 * n][3];
		moves = find_moves(board);
		//System.out.println(moves[0][0]);
		//System.out.println(moves[0][1]);
		//System.out.println(moves[0][2]);
		boolean[] new_board = new boolean[n];
		for (int i = 0; i < n; i++) {
			new_board[i] = board[i];
		}

		while (moves[0][0] != -1) {
			int lowest_result = 12;
			int best_move = 0;
			int m = 0;
			while (moves[m][0] != -1) {
				boolean[] new_board2 = changeBoard(moves[m][0], moves[m][1], new_board);
				int result = execute(new_board2);
				if (result < lowest_result) {
					lowest_result = result;
					best_move = m;
				}
				m++;
			}

			boolean[] new_board3 = changeBoard(moves[best_move][0], moves[best_move][1], new_board);
			for (int k = 0; k < n; k++) {
				new_board[k] = new_board3[k];
			}

			moves = find_moves(new_board);
		}

		int pebbles_left = 0;

		for (int x = 0; x < n; x++) {
			if (new_board[x] == true) {
				pebbles_left++;
			}
		}

		return pebbles_left;
	}



	public static void main(String[] args) {
		BufferedReader br;
		Writer bw;
		try {
			br = new BufferedReader(new FileReader("testMancala.txt"));
			File file = new File("testMancala_solution.txt");
            bw = new BufferedWriter(new FileWriter(file));

			String line = br.readLine();
			int num_problems = Integer.parseInt(line);

			for (int i = 0; i < num_problems; i++) {
				line = br.readLine();
				int[] problem = new int[12];
				String[] words = line.split(" ");
				for (int x = 0; x < 12; x++) {
					problem[x] = Integer.parseInt(words[x]);
				}

				boolean[] board = new boolean[12];

				for (int x = 0; x < 12; x++) {
					if (problem[x] == 0) {
						board[x] = false;
					}
					else {
						board[x] = true;
					}
				}

				int pebbles_left = execute(board);
				System.out.println(pebbles_left);
				bw.write(Integer.toString(pebbles_left) + "\n");
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
}



















