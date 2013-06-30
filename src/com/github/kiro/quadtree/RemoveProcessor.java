package com.github.kiro.quadtree;

import java.util.List;
import java.util.Map;

/**
 * RemoveProcessor
 */
class RemoveProcessor implements Processor<Point> {
    private final Point point;

    private RemoveProcessor(Point point) {
        this.point = point;
    }

    @Override
    public void process(Node<Point> node) {
        node.values.remove(point.id);
    }

    @Override
    public boolean shouldProcess(Node<Point> node) {
        boolean shouldProcess = node.contains(point.x, point.y);

        if (shouldProcess) {
            node.count--;
        }

        return shouldProcess;
    }

    public static RemoveProcessor pleaseRemove(Point point) {
        return new RemoveProcessor(point);
    }
}
