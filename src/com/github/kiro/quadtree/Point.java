package com.github.kiro.quadtree;

import java.util.Objects;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

/**
 * Point
 */
public class Point {
    private static final double EPS = 0.0000001;

    public final int id;
    public final double x, y;

    private static LonDegreeLength lonDegreeLength = new LonDegreeLength();

    private Point(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public static Point point(int id, double x, double y) {
        return new Point(id, x, y);
    }

    public static Point point(double x, double y) {
        return new Point(0, x, y);
    }

    public Point move(double xdiff, double ydiff) {
        return new Point(id, x + xdiff, y + ydiff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Point)) {
            return false;
        }

        Point that = (Point)obj;
        return id == that.id && abs(x - that.x) < EPS && abs(y - that.y) < EPS;
    }

    @Override
    public String toString() {
        return id + " " + x + " " + y;
    }

    public double distance(Point p) {
        double theta = x - p.x;
        double dist = Math.sin(deg2rad(y)) * Math.sin(deg2rad(p.y)) +
                Math.cos(deg2rad(y)) * Math.cos(deg2rad(p.y)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return dist;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private static final double DEGREE_IN_KM = 111.111;

    public double approximateDistance(Point p) {
        double lon = (x - p.x) * lonDegreeLength.get((y + p.y) / 2.0);
        double lat = (y - p.y) * DEGREE_IN_KM;

        return sqrt(lat * lat  + lon * lon);
    }

    public boolean approximateDistanceLessThan(Point p, double distance) {
        double lon = (x - p.x) * lonDegreeLength.get((y + p.y) / 2.0);
        double lat = (y - p.y) * DEGREE_IN_KM;

        return lat*lat + lon*lon < distance * distance;
    }
}
