package moon.compile.function;

import moon.compile.util.variable.Data;
import moon.compile.util.variable.Vars;

import java.util.Objects;

/**
 * Created by Administrator on 2016/6/1.
 */
public class Pointer extends moon.compile.util.variable.Data {

    public Pointer(String name) throws Exception {
        super(name);
    }

    @Override
    public String toString() {
        return "&" + getName() + "-> " + Objects.toString(pointer);
    }

    Data pointer;

    public void setPointer(moon.compile.util.variable.Data pointer) throws Exception {
        if (pointer instanceof Vars.Array && ((Vars.Array) pointer).isReference()) {
            arIndex = pointer.getArIndex();
//            JOptionPane.showMessageDialog(null, arIndex + " : " + Arrays.toString(((Vars.Array) pointer).getArray()));
        }
        this.pointer = pointer;
    }

    @Override
    public double getValue() throws Exception {
        if (pointer != null) {
            if (pointer instanceof Vars.Array) {
                Vars.Array array = ((Vars.Array) pointer);
                if (array.isReference()) {
                    return array.getArray()[arIndex];
                } else {
                    //this statement never visit.
                }
            } else {
                return pointer.getValue();
            }

        }
        return hashCode();
    }

    @Override
    public void setValue(double value) throws Exception {
        if (pointer != null) {
            if (pointer instanceof Vars.Array) {
                Vars.Array array = ((Vars.Array) pointer);
                if (array.isReference()) {
                    array.getArray()[arIndex] = value;
                    return;
                } else {
                    //this statement never visit.
                }
            }
            pointer.setValue(value);
            return;
        }
        throw new Exception("Pointer with constant is incompatible.");
    }

    public moon.compile.util.variable.Data getPointer() {
        if (pointer == null) {
            return new Data(hashCode());
        }
        return pointer;
    }
}
