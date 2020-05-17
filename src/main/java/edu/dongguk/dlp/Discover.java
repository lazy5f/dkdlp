package edu.dongguk.dlp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Discover {
	
	/** 탐지할 식별자 목록 */
	List<Identifier> idfs;
	
	/** 식별자 별 compile 해둔 regex pattern 목록 */
	List<Pattern> pts = new ArrayList<>();

	Discover(List<Identifier> idfs) {
		this.idfs = idfs;
		
		// Regex를 미리 compile 해둔다.
		for (Identifier idf : idfs) {
			pts.add(Pattern.compile(idf.pttn));
		}
	}
	
	void scan(String txt, IncidentConsumer cnsm) {
		for (int i = 0; i < pts.size(); i++) {
			// 각각의 i번째 식별자를 검색한다.
			Identifier idf = idfs.get(i);
			Matcher mt = pts.get(i).matcher(txt);
			while (mt.find()) {
				// Captured groups를 array로 만듦
				String[] grps = IntStream.range(0, mt.groupCount() + 1).mapToObj(mt::group).toArray(String[]::new);
				
				// 유효성 확인
				if (idf.valid.check(grps)) {
					// 찾았다.
					cnsm.accept(idf, mt.start(), mt.end());
				}
			}
		}
	}
	
	@FunctionalInterface
	public interface IncidentConsumer {
		void accept(Identifier idf, int start, int end);
	}
}
