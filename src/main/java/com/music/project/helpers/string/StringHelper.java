package com.music.project.helpers.string;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class StringHelper {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w\\p{L}-]"); // Thêm \\p{L} để bao gồm ký tự Unicode chữ cái
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");

        slug = slug.toLowerCase(Locale.ENGLISH);
        slug = slug.replace("đ", "d");


        // Tạo chuỗi 3 ký tự ngẫu nhiên
        String randomChars = generateRandomChars(3);
        String result = slug + "-" + randomChars;
//                slug = slug.replace("--", "-");
        return result.replace("--", "-");
    }

    private static String generateRandomChars(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}
