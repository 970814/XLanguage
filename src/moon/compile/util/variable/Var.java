package moon.compile.util.variable;

import moon.compile.function.Pointer;

/**
 * Created by Administrator on 2016/6/6.
 */
public class Var extends Data {

    public Var(String name) throws Exception {
        super(name);
    }

    public static int id = 0;

    Vars args = new Vars();

    public void add(Data data) throws Exception {
        args.add(new Pointer(name + id++){{
            setPointer(data);}});
    }

    public Vars getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return args.toString();
    }

    public Data remove() throws Exception {
        if (args.size() == 0) {
            throw new Exception("No such element in queue called: \'" + name+"\'");
        }
        return ((Pointer) args.remove()).getPointer();
    }

    @Override
    public double getValue() throws Exception {
        return args.size();
    }
}
