package moon.compile.util.variable;

import moon.compile.util.analyst.constant.BaseDataConstant;
import moon.compile.function.Function;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Administrator on 2016/5/11.
 */

/**
 * Admin all the vars.
 */
public class Vars extends LinkedList<Data> implements BaseDataConstant {
	public Function getFunctionCalled(String name) {
		for (Data data : this) {
			if (data.getName().equals(name) && data instanceof Function) {
				return (Function) data;
			}
		}
		return null;
	}

	public static class Array extends Data {
		private double[] ar;
		private boolean isReference;

		public Array(String name, int arLength) throws Exception {
			super(name);
			ar = new double[arLength];
			isReference = true;
		}



		public void setArray(double[] ar) {
			this.ar = ar;
		}

		public double[] getArray() {
			return ar;
		}

		public int getArrayLength() {
			return ar.length;
		}

		@Override
		public void setValue(double value) throws Exception {
			if (isReference) {
				try {
//					ar[arIndex] = value;
					int index = getArIndex();
					setArIndex(index);
					ar[index] = value;
				} catch (Exception e) {
					throw new Exception("For the array: \'" + name + "\', size:" + getArrayLength() + ", index: \'" + arIndex + "\' is out of bounds.");
				}
				return;
			}
//			try {
				throw new Exception("ARRAY: \'" + name + "\' can not be lvalue.");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}

		@Override
		public double getValue() throws Exception {
			if (isReference) {
				try {
//					return ar[arIndex];
					return ar[getArIndex()];
				} catch (Exception e) {
					throw new Exception("For the array: \'" + name + "\', size: " + getArrayLength() + ", index: \'" + arIndex + "\' is out of bounds.");
				}
			}
//			throw new Exception("ARRAY: \'" + name + "\' can be reference.");
			return ar.hashCode();
		}

		public boolean isReference() {
			return isReference;
		}

		public void setNonReference() {
			isReference = false;
//			arrayIndex = new Stack<>();
		}

		public void reference() {
			isReference = true;
		}

		@Override
		public String toString() {
			return name + " = " + Arrays.toString(ar);
		}
	}

	public Vars() {
	}

	/**
	 * Return the The index of var, if the var is not exist, return -1.
	 * 
	 * @param name
	 *            The name of var.
	 * @return The index of the var.
	 */
	public int getIndex(String name) {
		for (int i = 0, thisSize = size(); i < thisSize; i++) {
			if (get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	public boolean isArray(int elemIndex) {
		return get(elemIndex) instanceof Array;
	}

	public void addAr(String name, int arLength) throws Exception {
		newVar(new Array(name, arLength));
	}

	public boolean isFunction(int elemIndex) {
		return get(elemIndex) instanceof Function;
	}

	/**
	 * No matter when the method is called , it will set the variable value of zero.
	 * @param arg0 new var.
	 * @throws Exception if the var has already existed, it will occur.
     */
	public void newVar(Data arg0) throws Exception {
		for (Data data : this) {
			if (data.getName().equals(arg0.getName())) {
//				data.setValue(0.0);
				if (data instanceof Function) {
					return;
				}
				throw new Exception("the variable called \'" + arg0.getName() + "\' has already existed.");
			}
		}
		add(arg0);
	}
}
