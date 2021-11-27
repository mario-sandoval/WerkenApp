package cl.kimelti.werken.util;

import android.util.Base64;

public class StringUtil {

    public static String encript(String s)  {
        return Base64.encodeToString(s.getBytes(), Base64.NO_WRAP);
    }

    public static String concatenate(String... texts){
        StringBuilder sb = new StringBuilder();
        for (String text:texts) {
            sb.append(text);
        }
        return sb.toString();
    }
}
