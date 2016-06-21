package moon.compile.manager;

/**
 * Created by Administrator on 2016/5/26.
 */
public class CompileStack extends moon.compile.CompileStack {
    @Override
    public void add(char e) throws Exception {
        if (size() == 0 && e != '{') {
            throw new Exception("Function define expected \'{\': code statement.");
        }
        super.add(e);
    }
}
