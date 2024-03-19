package logik;

import java.util.Collections;
import java.util.LinkedList;


public class StackPathLocal {
    public static final StackPathLocal STACK_PATH_DAW = new StackPathLocal();
    private static LinkedList<String> stackPath;
    public StackPathLocal() {
        stackPath = new LinkedList<>();
    }
    public static void pushPath(String path){
        stackPath.add(path);
    }
    public static void insertPath(String path){
        String [] mass = path.split("\\\\");
        Collections.addAll(stackPath, mass);
    }
    public static boolean isRootFolder(){
        return stackPath.isEmpty();
    }
    public static void popPath(){
        stackPath.removeLast();
    }

    public static String string() {
        return stackPath.toString();
    }
    public static String stringPath(){
        StringBuilder sb = new StringBuilder();
        if(!stackPath.isEmpty()){
            for (String segmentPath: stackPath){
                sb.append(segmentPath).append("\\");
            }
            if(stackPath.size()>1) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }
    public static void  optimizedStack() {
         if(!stackPath.isEmpty() && !stackPath.getFirst().contains(":")) {
            String optimizedFirst = stackPath.getFirst().charAt(0) + ":\\";
            stackPath.set(0,optimizedFirst);
        }
    }
}
