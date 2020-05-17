package edu.dongguk.dlp;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileScannerTest {
	
	@Test
	public void testIsValidDate() {
		assertTrue(FileScanner.isValidDate(2020, 5, 16));
		
		assertTrue(FileScanner.isValidDate(2020, 2, 29));
		assertFalse(FileScanner.isValidDate(2021, 2, 29));
	}
	
	@Test
	public void testSSN() {
		assertTrue(FileScanner.SSN_VALID.check("", "711111", "1111117"));
		assertFalse(FileScanner.SSN_VALID.check("", "711111", "1111118"));
		
		assertFalse(FileScanner.SSN_VALID.check("", "322222", "2222223"));
	}
}
