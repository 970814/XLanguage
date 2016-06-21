package moon.compile.util.analyst.constant;

import java.util.Vector;

/**
 * Created by Administrator on 2016/5/27.
 */
public class ReservedWords {
	/**
	 * you can not instance the class.
	 */
	private ReservedWords() {
	}

	static Vector<String> reservedWords = new Vector<>();

	public static Vector<String> getReservedWords() {
		return reservedWords;
	}

	public static void checkName(String s) throws Exception {
		s = s.trim();
		for (String reservedWord : reservedWords) {
			if (s.equals(reservedWord)) {
				throw new Exception("you can't use this identify: \'"
						+ reservedWord + "\'");
			}
		}
	}

	static {
		add("if");
		add("else");
		add("def");
		add("while");
		add("return");
	}

	private static void add(String args) {
		reservedWords.add(args);
	}
}
