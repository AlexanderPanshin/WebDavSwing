package logik;

import gui.BasePanel;
import gui.HeaderPanel;

import java.util.Stack;

public class StackPathLocal {
    public static final StackPathLocal STACK_PATH_DAW = new StackPathLocal();
    private static Stack<String> stackPath;
    public StackPathLocal() {
        stackPath = new Stack<>();
    }
    public static void pushPath(String path){
        stackPath.push(path);
    }
    public static void insertPath(String path){
        String [] mass = path.split("\\\\");
        for(int i = 0; i < mass.length; i++){
            stackPath.push(mass[i]);
        }
        System.out.println("Дерево локальное свтруктура = " + stackPath.toString());
    }
    public static boolean isRootFolder(){
        return stackPath.isEmpty();
    }
    public static String popPath(){
        return stackPath.pop();
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
}
