package com.github.kiro.quadtree;

import junit.framework.TestCase;

import static com.github.kiro.quadtree.Point.point;

/**
 * PointTest
 */
public class PointTest extends TestCase {
    public void testDistance() {
        Point waterloo = point(-0.11331, 51.502599);
        Point regentsPark = point(-0.154423, 51.52781);

        System.out.println(waterloo.distance(regentsPark));
        System.out.println(waterloo.approximateDistance(regentsPark));
        System.out.println(waterloo.distance(point(-0.11331, 51.52781)));
        System.out.println(waterloo.distance(point(-0.154423, 51.502599)));
    }
}
