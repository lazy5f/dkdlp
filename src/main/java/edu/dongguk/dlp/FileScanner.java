package edu.dongguk.dlp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;

import edu.dongguk.dlp.Identifier.Validator;

public class FileScanner {
	
	void scan(String path, String encoding) throws IOException {
		Discover d = new Discover(Arrays.asList(SSN, MOB));
		
		String txt = new String(Files.readAllBytes(Paths.get(path)), encoding);
		
		d.scan(txt, (idf, start, end) -> {
			System.out.println(idf + ": " + txt.substring(start, end));
		});
	}
	
	/*
	 * Concrete identifiers
	 */
	
	static final String SSN_PTTN = "(\\d{6})[ -]?([012349]\\d{6})";
	
	static final Validator SSN_VALID = g -> {
		// 앞 6자리
		int[] a = IntStream.range(0, 6).map(i -> g[1].charAt(i) - '0').toArray();
		
		if (!isValidDate(a[0] * 10 + a[1], a[2] * 10 + a[3], a[4] * 10 + a[5])) {
			return false;
		}
		
		// 뒤 7자리
		int[] b = IntStream.range(0, 7).map(i -> g[2].charAt(i) - '0').toArray();
		
		int wsum = (2 * a[0] + 3 * a[1] + 4 * a[2] + 5 * a[3] + 6 * a[4] + 7 * a[5] + 8 * b[0] + 9 * b[1] + 2 * b[2]
				+ 3 * b[3] + 4 * b[4] + 5 * b[5]);
		
		//System.out.println((11 - wsum % 11) % 10);
		
		return (11 - wsum % 11) % 10 == b[6];
	};
	
	static final Identifier SSN = new Identifier("주민등록번호", SSN_PTTN, SSN_VALID);
	
	// FIXME
	static final String MOB_PTTN = "01\\d[ -]?\\d{4}[ -]?\\d{4}";
	
	// FIXME
	static final Validator MOB_VALID = g -> {
		return true;
	};
	
	static final Identifier MOB = new Identifier("휴대전화번호", MOB_PTTN, MOB_VALID);
	
	/*
	 * Helpers
	 */
	
	public static final int NUM_DAYS[] = {-1, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	public static boolean isValidDate(int y, int m, int d) {
		return 1 <= m && m <= 12 && 1 <= d && d <= NUM_DAYS[m] &&
				(m != 2 || d != 29 || (y % 4 == 0 && y % 100 != 0 || y % 400 == 0));
	}
	
	/*
	 * main
	 */
	public static void main(String[] args) throws IOException {
		new FileScanner().scan("src/test/java/edu/dongguk/dlp/DiscoverTest.java", "UTF-8");
	}
}
