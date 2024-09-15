package in.rajlabs.EmailService.util;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommonUtils {
    public static UUID generateUUID() {
        return UUID.randomUUID();
    }

    public static String getHumanTimeFromEpoachTime(long epoach) {
        long _IstAdjustedEpoach = epoach;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
        Date netDate = new Date(_IstAdjustedEpoach * 1000);
        return sdf.format(netDate);
    }

    public static String getHumanTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy HH:mm:ss");

        Date d = new Date();
        return sdf.format(d);
    }

    public static <T> T addTimeinResponse(T map) {
        Map<Object, Object> newMap = new HashMap<>((Map<?, ?>) map);

        newMap.put("current_time", getHumanTime());
        return (T) newMap;
    }

    public static Map<String, Object> getTimeAddedResponseReadyMap() {
        Map<String, Object> res = new HashMap<>();
        res.put("current_time", getHumanTime());
        return res;
    }

    public static Map<String, Object> getTimeAddedResponseReadyMap(boolean success, String message) {
        Map<String, Object> res = new HashMap<>();
        res.put("current_time", getHumanTime());
        res.put(CONSTANTS.KEY_SUCCESS, success);
        res.put(CONSTANTS.KEY_MESSAGE, message);
        return res;
    }

    public static Map<String, Object> getResponseReadyMap(boolean success, String message) {
        Map<String, Object> res = new HashMap<>();
        res.put(CONSTANTS.KEY_SUCCESS, success);
        res.put(CONSTANTS.KEY_MESSAGE, message);
        return res;
    }

    public static Map<String, Object> getTimeAddedResponseReadyMap(boolean success) {
        Map<String, Object> res = new HashMap<>();
        res.put("current_time", getHumanTime());
        res.put(CONSTANTS.KEY_SUCCESS, success);


        return res;
    }

    public static Map<String, Object> getSuccessResponseReadyMap(boolean success) {
        Map<String, Object> res = new HashMap<>();
        res.put(CONSTANTS.KEY_SUCCESS, success);
        return res;
    }




}