package moon.compile.util.variable;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/5/10.
 */
public class Values extends LinkedList<Data> {
	Vars vars;

	public Values(Vars vars) {
		this.vars = vars;
	}

	public Vars getVars() {
		return vars;
	}

	public Values() {
		vars = new Vars();
	}

	/**
	 * Created by Administrator on 2016/5/11.
	 */

	public boolean add(double v) {
		return add(new Data(v));
	}

	public boolean add(int varIndex) {
		return add(vars.get(varIndex));
	}

	@Deprecated
	public boolean add(int varIndex, boolean isArray) {
		return add(vars.get(varIndex));
	}

}
