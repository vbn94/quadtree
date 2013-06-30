package com.github.kiro.quadtree;

import junit.framework.Test;
import junit.framework.TestCase;

import java.util.HashMap;

import static com.github.kiro.quadtree.Point.point;
import static com.github.kiro.quadtree.WithinDistanceProcessor.withinDistance;

/**
 * WithinDistanceProcessorTest
 */
public class WithinDistanceProcessorTest extends TestCase {
    public void testWithinDistance() {
        Node<Point> regentsPark = Node.node(-0.160002, 51.53128, -0.145582, 51.525674);
        Point someTreeInRegentsPark = point(1, -0.153393, 51.528771);
        Point anotherTree = point(2, -0.155109, 51.530266);

        regentsPark.values = new HashMap<Integer, Point>();
        regentsPark.values.put(1, someTreeInRegentsPark);
        regentsPark.values.put(2, anotherTree);

        Point picaddilly = point(-0.133995, 51.510559);

        WithinDistanceProcessor oneKm = withinDistance(picaddilly, 1);
        assertFalse(oneKm.shouldProcess(regentsPark));

        WithinDistanceProcessor twoKm = withinDistance(picaddilly, 2);
        assertFalse(twoKm.shouldProcess(regentsPark));

        WithinDistanceProcessor threeKm = withinDistance(picaddilly, 3);
        assertTrue(threeKm.shouldProcess(regentsPark));

        threeKm.process(regentsPark);

        assertEquals(threeKm.points().size(), 2);
        assertTrue(threeKm.points().contains(someTreeInRegentsPark));
        assertTrue(threeKm.points().contains(anotherTree));
    }
}
