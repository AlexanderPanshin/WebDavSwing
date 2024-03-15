package logik;

import gui.BasePanel;
import gui.HeaderPanel;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

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
        for(int i = 0; i < mass.length; i++){
            stackPath.add(mass[i]);
        }
        System.out.println("Дерево локальное свтруктура = " + stackPath.toString());
    }
    public static boolean isRootFolder(){
        return stackPath.isEmpty();
    }
    public static String popPath(){
        return stackPath.removeLast();
    }
    public static String peekPath(){
        return stackPath.peek();
    }
    public static int sizeStack(){
        return stackPath.size();
    }

    public static String string() {
        System.out.println("Stack mass " + stackPath.toString());
        return stackPath.toString();
    }
    public static String stringPath(){
        StringBuffer sb = new StringBuffer();
        System.out.println("StringPath + = " + stackPath);
        if(!stackPath.isEmpty()){
            for (String segmentPath: stackPath){
                sb.append(segmentPath+"\\");
            }
            if(stackPath.size()>1) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        System.out.println("Stringh to path Stack " + sb.toString());
        return sb.toString();
    }
    public static void  optimizedStack() {
         if(!stackPath.isEmpty() && !stackPath.getFirst().contains(":")) {
            System.out.println(stackPath);
            System.out.println("Optimized STACK");
            String optimizedFirst = stackPath.getFirst().substring(0, 1) + ":\\";
            stackPath.set(0,optimizedFirst);
        }
    }
}
