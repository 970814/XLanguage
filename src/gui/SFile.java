package gui;

import java.io.File;

/**
 * Created by Administrator on 2016/6/7.
 */
public class SFile extends File {


    public SFile(String pathname) {
        super(pathname);
    }

    @Override
    public String toString() {
        return getName();
    }
}
