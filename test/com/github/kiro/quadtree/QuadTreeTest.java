package com.github.kiro.quadtree;

import junit.framework.TestCase;

import java.util.List;

import static com.github.kiro.quadtree.Point.point;

/**
 * QuadTreeTest
 */
public class QuadTreeTest extends TestCase {
    public void testKNearest() {
        QuadTree quadTree = new QuadTree.Builder()
                .xaxis(-180.0, 180.0)
                .yaxis(-90.0, 90.0)
                .maxDepth(20)
                .build();

        Point goodgeStreet = point(-0.134424, 51.520547);
        Point oxfordStreet = point(-0.141977, 51.515152);
        Point picadillyCircus = point(-0.133738, 51.510025);
        Point leicesterSquare = point(-0.128244, 51.511413);
        Point coventGarden = point(-0.124725, 51.513123);
        Point holborn = point(-0.121206, 51.517503);
        Point rusellSquare = point(-0.124296, 51.523164);

        quadTree.addAll(goodgeStreet, oxfordStreet, picadillyCircus, leicesterSquare, coventGarden, holborn, rusellSquare);
        List<Point> nearest = quadTree.kNearest(leicesterSquare, 2);
        assertTrue(nearest.contains(picadillyCircus));
        assertTrue(nearest.contains(coventGarden));
    }
}
