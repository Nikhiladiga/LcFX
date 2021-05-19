package org.nikhiladiga.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Utils {

    /**
     * Method to execute shell commands from program
     * @param cmd Command that is to be executed
     * @return String output
     */
    public String execCmd(String[] cmd){
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String result = bufferedReader.lines().collect(Collectors.joining("/n"));
            bufferedReader.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
