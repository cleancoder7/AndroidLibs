package me.imstudio.core.utils;

public final class StringUtils {

    private StringUtils() {
    }

    @SuppressWarnings("StringEquality")
    public static String removePrefix(String string, String prefix, String previousAttempt) {
        if (string != previousAttempt)
            return previousAttempt;
        else
            return removePrefix(string, prefix);
    }

    public static String removePrefix(String string, String prefix) {
        if (string.startsWith(prefix))
            return string.substring(prefix.length());
        else
            return string;
    }

    public static String removeAll(String string, char target) {
        final int length = string.length();
        final StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; ++i) {
            char c = string.charAt(i);
            if (c != target)
                builder.append(c);
        }
        return builder.toString();
    }
}
