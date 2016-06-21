package gui;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by Administrator on 2016/6/9.
 */
public class HashSet extends LinkedList<File> {
    @Override
    public boolean add(File file) {
        for (File f : this) {
            if (f.getAbsolutePath().equals(file.getAbsolutePath())) {
                return false;
            }
        }
        return super.add(file);
    }



    @Override
    public File remove() {
        for (int i = 0; i < this.size(); i++) {
            if (!get(i).exists()) {
                remove(get(i));
            }
        }
        return null;
    }
}
