package org.redrock.servlet.handle;

import com.google.gson.Gson;

import javax.servlet.ServletInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class ServletDataHandle {

    public Map<String,String> stringToMap(String jsonStr) {
        Gson gson = new Gson();
        Map<String,String> jsonMap = gson.fromJson(jsonStr, Map.class);
        return jsonMap;
    }

    public String streamToJson(InputStream inputStream){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((ServletInputStream)inputStream));
            StringBuffer stringBuffer = new StringBuffer("");
            String temp;
            while ((temp = bufferedReader.readLine()) != null){
                stringBuffer.append(temp);
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
