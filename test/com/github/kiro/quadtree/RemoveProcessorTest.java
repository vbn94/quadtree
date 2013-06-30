package com.github.kiro.quadtree;

import com.github.kiro.quadtree.Point;
import com.github.kiro.quadtree.RemoveProcessor;
import junit.framework.TestCase;

import java.util.Map;

import static com.github.kiro.quadtree.Node.node;
import static com.github.kiro.quadtree.Point.point;
import static com.github.kiro.quadtree.RemoveProcessor.pleaseRemove;
import static com.google.common.collect.Maps.newHashMap;

/**
 * RemoveProcessorTest
 */
public class RemoveProcessorTest extends TestCase {
    private static final int ID = 1;
    private static final int OTHER_ID = 2;

    private Point point = point(ID, 5, 5);
    private RemoveProcessor remove = pleaseRemove(point);

    public void testProcess() {
        Node<Point> node = node(0, 0, 0, 0);
        remove.process(node);
        assertTrue(node.values.size() == 0);

        node.values.put(point.id, point);
        node.values.put(OTHER_ID, point);
        remove.process(node);

        assertTrue(node.values.size() == 1);
        assertTrue(node.values.containsKey(OTHER_ID));
        assertFalse(node.values.containsKey(ID));
    }

    public void testShouldProcess() {
        assertTrue(remove.shouldProcess(Node.<Point>node(1, 10, 1, 10)));
        assertTrue(remove.shouldProcess(Node.<Point>node(3, 16, 4, 6)));
        assertFalse(remove.shouldProcess(Node.<Point>node(0, 4, 3, 10)));
        assertFalse(remove.shouldProcess(Node.<Point>node(6, 7, 1, 10)));
        assertFalse(remove.shouldProcess(Node.<Point>node(4, 7, 6, 10)));
        assertFalse(remove.shouldProcess(Node.<Point>node(4, 7, 2, 3)));
        assertFalse(remove.shouldProcess(Node.<Point>node(4, 4, 4, 4)));
    }
}
