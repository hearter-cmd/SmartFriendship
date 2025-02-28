package com.yaonie.intelligent.assessment.server.chat_server.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 77160
 */
public class DateUtil {

    private static final Object LOCK_OBJ = new Object();
    private static final Map<String, ThreadLocal<SimpleDateFormat>> SDF_MAP = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = SDF_MAP.get(pattern);
        if (tl == null) {
            synchronized (LOCK_OBJ) {
                tl = SDF_MAP.get(pattern);
                if (tl == null) {
                    tl = ThreadLocal.withInitial(() -> new SimpleDateFormat(pattern));
                    SDF_MAP.put(pattern, tl);
                }
            }
        }

        return tl.get();
    }

    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    public static Date parse(String dateStr, String pattern) {
        try {
            return getSdf(pattern).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
