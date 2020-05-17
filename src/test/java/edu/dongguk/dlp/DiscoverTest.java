package edu.dongguk.dlp;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

public class DiscoverTest {
	
	static final Identifier SSN_TEST = new Identifier("주민", "\\d{6}-\\d{7}", g -> true);
	static final Identifier MOB_TEST = new Identifier("핸폰", "\\d{3}-\\d{4}-\\d{4}", g -> true);

	@Test
	public void testScan() {
		Discover d = new Discover(Arrays.asList(SSN_TEST, MOB_TEST));
		
		String txt = "711111-1111117 711111-1111118 7111111111117 010-1234-5678 01012345678 999-1234-5678";
		String expt = "711111-1111117 711111-1111118 010-1234-5678 999-1234-5678";
		
		ArrayList<String> founds = new ArrayList<>();
		
		d.scan(txt, (idf, start, end) -> founds.add(txt.substring(start, end)));
		
		assertEquals(expt, founds.stream().collect(Collectors.joining(" ")));
	}
}
