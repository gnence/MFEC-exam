/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfec.exam.main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import mfec.exam.readlog.ReadLog;


public class MFECExam {

    public static ArrayList<String> info = new ArrayList<>();
    
    static final String path = "-------ENTER-YOUR-DESTINATION-LOCATE-JSONFILE------"; // exam "C:\\Users\\MFEC-Exam\\Web by React\\src";
    static final String path_log = "src/Java.log";
    
    public static void main(String[] args) throws FileNotFoundException{
        ReadLog readlog = new ReadLog(path_log, path);
        readlog.genCost_jsonFile();
    }
}
