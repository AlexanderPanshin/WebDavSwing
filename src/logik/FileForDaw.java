package logik;

import java.io.File;
import java.net.URI;
import java.util.Arrays;

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
    public boolean isRootFolder(){
        String path = this.getAbsolutePath();
        System.out.println(this.getAbsolutePath());

        String [] mass = path.split("\\\\");
        Arrays.stream(mass).forEach(System.out::println);
        System.out.println("IsrootFolder lenght = " + mass.length);
        if(mass.length!=1){
            return false;
        }else {
            return true;
        }
    }

}
