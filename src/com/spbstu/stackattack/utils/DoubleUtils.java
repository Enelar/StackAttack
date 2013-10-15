package com.spbstu.stackattack.utils;

/**
 * Double utils representation class.
 *
 * @author bsi
 */
public class DoubleUtils {
    private static final double EPS = 0.01;

    public static boolean positive(final double n) {
        return n >= EPS;
    }

    public static boolean zero(final double n) {
        return (-EPS < n) && (n < EPS);
    }

    public static boolean negative(final double n) {
        return n <= -EPS;
    }

    public static boolean greater(final double n1, final double n2) {
        return positive(n1 - n2);
    }

    public static boolean equals(final double n1, final double n2) {
        return zero(n1 - n2);
    }

    public static boolean less(final double n1, final double n2) {
        return negative(n1 - n2);
    }

    public static boolean greaterOrEquals(final double n1, final double n2) {
        return greater(n1, n2) || equals(n1, n2);
    }

    public static boolean lessOrEquals(final double n1, final double n2) {
        return less(n1, n2) || equals(n1, n2);
    }

    public static boolean hasFraction(final double n) {
        return !equals(n, (int)n);
    }

    public static double round(final double n) {
        double f = Math.floor(n);

        if (Math.abs(f - n) < EPS) {
            return f;
        } else if (Math.abs(f + 1 - n) < EPS) {
            return f + 1;
        }

        return n;
    }
}
