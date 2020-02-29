import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;

public class balloon{

	public static int[] balloons;
	public static int[][] balloonLists;
	public static int num_left;
	public static int n;

	public balloon(int[] balloons, int n) {
		this.balloons = balloons;
		balloonLists = new int[n][n + 2];
		this.n = n;
		this.num_left = n;
		for (int i = 0; i < n; i++) {
			balloonLists[i][0] = balloons[i];
			findList(i);
		}
	}

	public static void updateList(int node) {
		int idx = 2;
		int height = balloonLists[node][0];
		int hits = 1;

		int i = node + 1;
		while ((i < n) && (height > 0)) {
			int h = balloonLists[i][0];
			if (h == (height - 1)) {
				height--;
				hits++;
				balloonLists[node][idx++] = i;
			}
			i++;
		}
		balloonLists[node][1] = hits;
	}

	public static void findList(int start) {
		int idx = 2;
		int height = balloons[start];
		int hits = 1;

		int i = start + 1;
		while ((i < n) && (height > 0)) {
			int h = balloons[i];
			if (h == (height - 1)) {
				height--;
				hits++;
				balloonLists[start][idx++] = i;
			}
			i++;
		}
		balloonLists[start][1] = hits;
	}

	/*public static int howManyHits(int start) {
		int height = balloons[start];
		int hits = 1;

		int i = start + 1;
		while ((i < n) && (height > 0)) {
			int h = balloons[i];
			if ((h == (height - 1)) && (h != -1)) {
				height--;
				hits++;
			}
			i++;
		}

		return hits;
	}*/

	//. . . . .
	//0 1 2 3 4

	/*public static int findBestBalloon() {
		int highest = 0;
		int bestBalloon = 0;
		int i = 0;

		while ((i < n) && (highest < (n - i))) {
			if (balloons[i] != -1) {
				int result = howManyHits(i);
				//System.out.println("hits = " + result);
				if (result > highest) {
					highest = result;
					bestBalloon = i;
				}
			}
			i++;
		}

		return bestBalloon;
	}*/

	public static int findNextList() {
		int highest = -1;
		int best = -1;
		for (int i = 0; i < n; i++) {
			int hits = balloonLists[i][1];
			if ((hits > highest) && (balloonLists[i][0] != -1)) {
				boolean available = true;
				for (int j = 2; j <= hits; j++) {
					if (balloonLists[balloonLists[i][j]][0] == -1) {
						available = false;
					}
				}
				if (available) {
					best = i;
					highest = hits;
					//System.out.println("height is " + balloonLists[i][0] + " and number of hits is " + balloonLists[i][1]);
					//System.out.println("Next list found! Best = " + best);
				}
				else {
					updateList(i--);
					//System.out.println("A");
				}
			}
		}
		return best;
	}

	public static void executeOneRound2(int start) {
		//System.out.println("Executing round, start = " + start);
		//System.out.println("Setting balloon number " + start + " to -1.");
		int hits = balloonLists[start][1];
		for (int i = 2; i <= hits; i++) {
			balloonLists[balloonLists[start][i]][0] = -1;
			balloonLists[balloonLists[start][i]][1] = 0;
			//System.out.println("Setting balloon number " + balloonLists[start][i] + " to -1.");
		}
		balloonLists[start][0] = -1;
		balloonLists[start][1] = 0;
		num_left = num_left - hits;
	}

	/*public static void executeOneRound(int start) {
		//System.out.println("Executing round, start = " + start);
		int height = balloons[start];
		balloons[start] = -1;

		for (int i = start + 1; i < n; i++) {
			if ((balloons[i] == (height - 1)) && (balloons[i] != -1)) {
				height--;
				balloons[i] = -1;
			}
		}
	}*/

	public static int findNumArrows() {
		int arrows = 0;

		while (num_left > 0) {
			int bestBalloon = findNextList();
			//System.out.println("Best balloon is " + bestBalloon);
			if (bestBalloon == -1) {
				System.out.println("ERROR");
				break;
			}
			arrows++;
			executeOneRound2(bestBalloon);
			//System.out.println("Round executed. Balloon list is now:");
			/*for (int k = 0; k < n; k++) {
				System.out.print(" " + balloonLists[k][0]);
			}
			System.out.print("\n");*/
		}

		return arrows;
	}

	public static void main(String[] args) {
		BufferedReader br;
		Writer bw;
		try {
			br = new BufferedReader(new FileReader("testBalloons.txt"));
			File file = new File("testBalloons_solution.txt");
            bw = new BufferedWriter(new FileWriter(file));

			String line = br.readLine();
			int num_problems = Integer.parseInt(line);

			line = br.readLine();
			int[] problem_sizes = new int[num_problems];
			String[] sizes = line.split(" ");

			for (int i = 0; i < num_problems; i++) {
				problem_sizes[i] = Integer.parseInt(sizes[i]);
			}

			for (int i = 0; i < num_problems; i++) {
				line = br.readLine();

				//if (i == 3) {
				int[] problem = new int[problem_sizes[i]];
				String[] words = line.split(" ");
				for (int x = 0; x < problem_sizes[i]; x++) {
					problem[x] = Integer.parseInt(words[x]);
				}

				balloon set = new balloon(problem, problem_sizes[i]);
				//System.out.println("Balloon set successfully activated.");

				int arrows_needed = set.findNumArrows();
				//System.out.println(arrows_needed);
				bw.write(Integer.toString(arrows_needed) + "\n");	

				//}


			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
}
















