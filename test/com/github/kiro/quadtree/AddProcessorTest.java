package com.github.kiro.quadtree;

import com.github.kiro.quadtree.Point;
import com.github.kiro.quadtree.AddProcessor;
import junit.framework.TestCase;

import java.util.Map;

import static com.github.kiro.quadtree.AddProcessor.pleaseAdd;
import static com.github.kiro.quadtree.Node.node;
import static com.github.kiro.quadtree.Point.point;
import static com.google.common.collect.Maps.newHashMap;

/**
 * AddProcessorTest
 */
public class AddProcessorTest extends TestCase {
    private Point point = point(1, 5, 5);
    private AddProcessor add = pleaseAdd(point);

    public void testProcess() {
        Node<Point> node = node(0, 0, 0, 0);
        add.process(node);
        assertTrue(node.values.containsKey(point.id));
        assertEquals(node.values.get(point.id), point);
    }

    public void testShouldProcess() {
        assertTrue(add.shouldProcess(Node.<Point>node(1, 10, 1, 10)));
        assertTrue(add.shouldProcess(Node.<Point>node(3, 16, 4, 6)));
        assertFalse(add.shouldProcess(Node.<Point>node(0, 4, 3, 10)));
        assertFalse(add.shouldProcess(Node.<Point>node(6, 7, 1, 10)));
        assertFalse(add.shouldProcess(Node.<Point>node(4, 7, 6, 10)));
        assertFalse(add.shouldProcess(Node.<Point>node(4, 7, 2, 3)));
        assertFalse(add.shouldProcess(Node.<Point>node(4, 4, 4, 4)));
    }
}
