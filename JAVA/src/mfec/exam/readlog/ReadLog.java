/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfec.exam.readlog;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author gnence
 */
public class ReadLog {
    
    private String path_locate_json;
    private String path_log;
    
    public ReadLog(String path_log, String path_locate_json){
        this.path_log = path_log;
        this.path_locate_json = path_locate_json;
    }
    
    public void genCost_jsonFile(){
        try {
            createJSON(addData_json(read_log_file(path_log)),path_locate_json);
        } catch (JSONException ex) {
            Logger.getLogger(ReadLog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadLog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ReadLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private ArrayList<String> read_log_file(String path_log){
        ArrayList<String> info = new ArrayList<>();
        try{
            FileInputStream file_input = new FileInputStream(path_log);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(file_input));
            String log_user;
            while ((log_user = buffer.readLine()) != null)   {
                info.add(log_user);
            }
            file_input.close();
         } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
         }
        return info;
    }
    
    private int calculate_sec_use(String start_time, String end_time){
        String[] data_time_start = start_time.split(":"); 
        String[] data_time_stop = end_time.split(":"); 
        int count_sec_start = Integer.parseInt(data_time_start[0])*3600 + Integer.parseInt(data_time_start[1])*60 + Integer.parseInt(data_time_start[2]);
        int count_sec_end = Integer.parseInt(data_time_stop[0])*3600 + Integer.parseInt(data_time_stop[1])*60 + Integer.parseInt(data_time_stop[2]);
        return count_sec_end - count_sec_start;
    }
    
    private double calculate_cost(int sec_use){
        double total_cost = 0; 
        int start_cost = 3;
        if(sec_use > 0){
            int sec_count = sec_use - 60;
            if(sec_count > 0 && sec_count <= 60){
                    total_cost += start_cost;
            }else{
                int minute = sec_count/60;
                total_cost = start_cost + minute + (sec_count/60.0 - minute); 
            }
        }
        return this.round(total_cost,2);
    }
    
    private double round(double value, int places){
        if(places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    private JSONObject addData_json(ArrayList<String> info) throws JSONException{
        DecimalFormat df = new DecimalFormat("####0.00");
        JSONArray data_cost = new JSONArray();
        for(int i = 0 ; i < info.size()-1 ; i++){
            String[] data_log = info.get(i).split("\\|");
            JSONObject cost_data = new JSONObject();
            cost_data.put("promotion_name", data_log[4]);
            cost_data.put("mobile_no", data_log[3]);
            cost_data.put("cost", df.format(calculate_cost(calculate_sec_use(data_log[1], data_log[2]))));
            data_cost.put(cost_data);
        }
        return new JSONObject().put("total_cost", data_cost);
    }
    
    private void createJSON(JSONObject input,String pathLocal) throws FileNotFoundException, UnsupportedEncodingException, JSONException{
        try (PrintWriter writer = new PrintWriter(pathLocal+"\\data-cost.json", "UTF-8")) {
            writer.println(input.toString());
            writer.close();
        }
    }
    
    public JSONObject get_jsonData() throws JSONException{
        return addData_json(read_log_file(path_log));
    }
}
