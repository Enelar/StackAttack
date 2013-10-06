package com.spbstu.stackattack.utils;

/**
 * Stack attack utilities class.
 *
 * @author bsi
 */
public class TimeUtils {
    /**
     * Convert seconds to mills function.
     *
     * @param sec time in seconds to convert.
     *
     * @return time in mills.
     */
    public static long secToMills(double sec) {
        return (long)(1000 * sec);
    }

    /**
     * Convert mills to seconds function.
     *
     * @param mills time in mills to convert.
     *
     * @return time in seconds.
     */
    public static double millsToSec(long mills) {
        return mills / 1000.0;
    }
}
