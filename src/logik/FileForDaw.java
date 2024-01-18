package logik;

import java.io.File;
import java.net.URI;

public class FileForDaw extends File {
    public FileForDaw(String pathname) {
        super(pathname);
    }

    public FileForDaw(String parent, String child) {
        super(parent, child);
    }

    public FileForDaw(File parent, String child) {
        super(parent, child);
    }

    public FileForDaw(URI uri) {
        super(uri);
    }

    @Override
    public String toString() {
        return getName();
    }

    public FileForDaw[] listFilesDaw() {
        File[] files = super.listFiles();
        FileForDaw[] fileForDaws = new FileForDaw[files.length];
        for (int i = 0; i < files.length;i++){
            fileForDaws[i]=new FileForDaw(files[i].toURI());
        }
        return fileForDaws;
    }

}
