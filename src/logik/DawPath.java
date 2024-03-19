package logik;

import java.util.LinkedList;
import java.util.regex.Pattern;

public class DawPath {
    private String home;
    private String path;
    private String parent;


    public DawPath(String s) {
        path = s;
        home = getHome(s);
        parent = getParentString(s);
    }
    private String getHome(String s){
        return s.replaceAll("(https?://[^/?#]+).*", "$1");
    }
    private String getParentString(String s){
        StringBuffer sb = new StringBuffer(home+ "/");
        String [] tempMass = s.split("/");
        if(tempMass.length>3){
            for (int i = 3;i < tempMass.length-1; i++){
              sb.append(tempMass[i]).append("/");
            }
            return sb.toString();
        }else return "";
    }

}
