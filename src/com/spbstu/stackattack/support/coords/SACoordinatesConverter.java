package com.spbstu.stackattack.support.coords;

public class SACoordinatesConverter {
    public static class Coordinate<T> {
        public final T x, y;

        public Coordinate(final T x, final T y) {
            this.x = x;
            this.y = y;
        }
    }

    private static SACoordinatesConverter instance = new SACoordinatesConverter();

    private int w, h;
    protected int vw, vh;

    public static SACoordinatesConverter instance() {
        return instance;
    }

    public void resize(final int w, final int h) {
        this.w = w;
        this.h = h;

        vw = Math.max(w, h);
        vh = Math.min(w, h);
    }

    public SAScreenCoordinate virtualToScreen(final SAVirtualCoordinate vc) {
        if (w > h) {
            return new SAScreenCoordinate(vc.x, vc.y);
        } else {
            return new SAScreenCoordinate(w - vc.y, h - vc.x);
        }
    }

    public SAVirtualCoordinate screenToVirtual(final SAScreenCoordinate sc) {
        if (w > h) {
            return new SAVirtualCoordinate(sc.x, sc.y);
        } else {
            return new SAVirtualCoordinate(h - sc.y, w - sc.x);
        }
    }

    public int vw() {
        return vw;
    }

    public int vh() {
        return vh;
    }
}
