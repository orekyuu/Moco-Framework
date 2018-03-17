package net.orekyuu.moco.chou;

import java.util.stream.Stream;

public class NamingUtils {

    public static String multipleName(String word) {
        if (Stream.of("s", "sh", "ch", "o", "x").anyMatch(word::endsWith)) {
            return word + "es";
        }
        if (Stream.of("f", "fe").anyMatch(word::endsWith)) {
            return word.replaceAll("fe?$", "ves");
        }
        if (word.matches(".*[^aiueo]y")) {
            return word.replace("y$", "ies");
        }
        return word + "s";
    }

    public static String toUpperName(String word) {
        StringBuilder builder = new StringBuilder();
        word.chars().mapToObj(c -> {
            if ('A' <= c && c <= 'Z') {
                return "_" + (char)c;
            }
            return String.valueOf((char)c);
        }).forEach(builder::append);
        return builder.toString();
    }

    public static String toUpperFirst(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
