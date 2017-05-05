/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author lsh
 */
public class Utility {

    static long BASE=961211;
    static long MOD=2147483647;
    
    static public String Hash(String str) {
        byte[] s=str.getBytes();
        int n=s.length;
        long sum=0;
        for(int i=0;i<n;i++) {
            sum=(sum*BASE+s[i])%MOD;
        }
        return Long.toString(sum);
    }
    
    static public java.sql.Date StringToDate(String str) throws ParseException {
        if (str==null || str.equals("")) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf.parse(str);
        return new java.sql.Date(date.getTime());
    }

    static public int StringToInt(String str) {
        int res=0;
        try {
            res=Integer.parseInt(str);
        }catch (Exception e)
        {
            res=0;
        }
        return res;
    }
    
    static public double StringToDouble(String str) {
        double res=0;
        try {
            res=Double.parseDouble(str);
        }catch (Exception e)
        {
            res=0;
        }
        return res;
    }
    
    static public <T> T MapToClass(Map<String, Object> col, Class<T> cls) throws Exception {
        T obj = cls.newInstance();
        Field field;
        Set set = col.entrySet();
        for (Iterator iter = set.iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            try {
                field = cls.getDeclaredField(key);
                field.set(obj, value);
            } catch (Exception e) {
            }
        }
        return obj;
    }

}
