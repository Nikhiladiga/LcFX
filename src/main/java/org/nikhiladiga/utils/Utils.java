package org.nikhiladiga.utils;

public class Utils {

    public void execCmd(String[] cmd){
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
