package net.dexterr.cheques.utils;

import java.text.DecimalFormat;

public class NumberUtils {

    private static final DecimalFormat FORMATTER = new DecimalFormat("#,###.##");

    public static String formatDouble(double value) {
        return FORMATTER.format(value);
    }
}
