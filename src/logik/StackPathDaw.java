package logik;

import java.util.Stack;

public class StackPathDaw {
    public static final StackPathDaw STACK_PATH_DAW = new StackPathDaw();
    private static Stack<String> stackPath;
    public StackPathDaw() {
        stackPath = new Stack<>();
    }
    public static void pushPath(String path){
        stackPath.push(path);
    }
    public static boolean isRootFolder(){
        StackPathDaw.string();
        if(stackPath.size()>1){
            return false;
        }else {
            return true;
        }
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
        System.out.println("StackDawToSring method string" + stackPath.toString());
        return stackPath.toString();
    }
}
