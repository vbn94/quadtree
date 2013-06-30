package com.github.kiro.quadtree;

import static com.github.kiro.quadtree.Point.point;

/**
 * LonDegreeLength
 */
public class LonDegreeLength {
    private double [] cache = new double[3600];

    public LonDegreeLength() {
        for (int i = 0; i < cache.length; i++) {
            cache[i] = -1;
        }
    }

    public double get(double lat) {
        int cacheIndex = (int)((180.0 + lat) * 10.0);

        if (cache[cacheIndex] < 0) {
            double latEstimate = Math.round(lat * 10.0) / 10.0;

            cache[cacheIndex] = point(0, latEstimate).distance(point(1, latEstimate));
        }

        return cache[cacheIndex];
    }
}
