package fr.novaria.utils.base;

public final class StringUtils {

    public static double asDouble(String str, double defaultValue) {

        if (str == null) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(str);
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static int asInt(String str, int defaultValue) {

        if (str == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private StringUtils() { /* Cannot be instantiated */ }
}
