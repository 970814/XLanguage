package moon.compile;

import java.util.Stack;

/**
 * Created by Administrator on 2016/5/21.
 */
public class CompileStack extends Stack<Character> {
	public CompileStack() {
		clear();
	}

	@Override
	public void clear() {
		super.clear();
		es = 0;
	}

	private int es;

	public int getEs() {
		return es;
	}

	public void check() throws Exception {
		if (es != 0) {
			throw new Exception("expect: \'}\'");
		}
	}

	public void add(char e) throws Exception {
		add(e, true);
	}

	public void add(char e, boolean check) throws Exception {
		if (size() > 0) {
			char previous = peek();
			if (e == '}') {
				if (previous == '{') {
					es--;
					if (check) {
						pop();
					} else {
						super.add(e);
					}
					return;
				}
			}
		}
		if (e == '{') {
			es++;
		}
		if (e == '}') {
			es--;
		}
		super.add(e);
		if (es < 0) {
			throw new Exception("No match: \'{\'");
		}
	}

	public void toExp() {
		super.add(0, '{');
		super.add('}');
	}
}
