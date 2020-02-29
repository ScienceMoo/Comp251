import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;

public class islands{

	public static boolean[][] picture;
	public static boolean[][] checked;
	public static int m;
	public static int n;

	public islands(int m, int n, boolean[][] picture) {
		this.m = m;
		this.n = n;
		this.picture = picture;
		this.checked = new boolean[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				checked[i][j] = false;
			}
		}
	}

	public static void findRestOfIsland(int m_pos, int n_pos) {
		if (m_pos > 0) {
			if (checked[m_pos-1][n_pos] == false) {
				checked[m_pos - 1][n_pos] = true;
				if (picture[m_pos - 1][n_pos] == true) {
					findRestOfIsland((m_pos - 1), n_pos);
				}
			}
		}
		if (m_pos < (m - 1)) {
			if (checked[m_pos + 1][n_pos] == false) {
				checked[m_pos + 1][n_pos] = true;
				if (picture[m_pos + 1][n_pos] == true) {
					findRestOfIsland((m_pos + 1), n_pos);
				}
			}
		}
		if (n_pos > 0) {
			if (checked[m_pos][n_pos - 1] == false) {
				checked[m_pos][n_pos - 1] = true;
				if (picture[m_pos][n_pos - 1] == true) {
					findRestOfIsland(m_pos, (n_pos - 1));
				}
			}
		}
		if (n_pos < (n - 1)) {
			if (checked[m_pos][n_pos + 1] == false) {
				checked[m_pos][n_pos + 1] = true;
				if (picture[m_pos][n_pos + 1] == true) {
					findRestOfIsland(m_pos, (n_pos + 1));
				}
			}
		}
	}

	public static int findNumIslands() {

		int result = 0;

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (picture[i][j] == true) {
					if (checked[i][j] == false) {
						checked[i][j] = true;
						result++;
						findRestOfIsland(i, j);
					}
				}
			}
		}

		return result;
	}

	public static void main(String[] args) {
		BufferedReader br;
		Writer bw;
		try {
			br = new BufferedReader(new FileReader("testIslands.txt"));
			File file = new File("testIslands_solution.txt");
            bw = new BufferedWriter(new FileWriter(file));

			String line = br.readLine();
			int num_problems = Integer.parseInt(line);

			for (int i = 0; i < num_problems; i++) {
				line = br.readLine();
				String[] numbers = line.split(" ");
				int mx = Integer.parseInt(numbers[0]);
				int ny = Integer.parseInt(numbers[1]);
				boolean[][] problem = new boolean[mx][ny];

				for (int k = 0; k < mx; k++) {
					line = br.readLine();
					char[] pixels = line.toCharArray();
					for (int pix = 0; pix < ny; pix++) {
						if (pixels[pix] == 45) {
							problem[k][pix] = true;
						}
						else {
							problem[k][pix] = false;
						}
					}
				}
				
				islands new_ile = new islands(mx, ny, problem);

				int result = new_ile.findNumIslands();
				System.out.println(Integer.toString(result));
				bw.write(Integer.toString(result) + "\n");
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
}
























