package restaurant.util;

// Class to get string of name that goes before '@'
public class SimpleNameFormatter {
    public static String getSimpleName(String originalName) {
        StringBuilder name = new StringBuilder(originalName);
        int end = 0;
        for (end = 0; end < name.length(); ++end) {
            if (name.charAt(end) == '@') {
                break;
            }
        }
        return name.substring(0, end);
    }
}
