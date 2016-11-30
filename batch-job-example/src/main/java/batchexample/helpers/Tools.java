package batchexample.helpers;

import java.util.Map;
import java.util.Properties;

/**
 * @author Jan Martiska
 */
public class Tools {

    public static <T> Properties convertMapToProperties(Map<T, T[]> map) {
        final Properties result = new Properties();
        map.forEach((key, values) -> {
            result.put(key, values[0]);
        });
        return result;
    }
}
