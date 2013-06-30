package com.github.kiro.quadtree;

import java.util.ArrayList;
import java.util.List;

import static com.github.kiro.quadtree.Point.point;

/**
 * WithinDistanceProcessor
 */
class WithinDistanceProcessor implements Processor<Point> {
    private final Point point;
    private final List<Point> points;
    private final double radius;

    private WithinDistanceProcessor(Point point, double radius) {
        this.point = point;
        this.points = new ArrayList<Point>();
        this.radius = radius;
    }

    @Override
    public void process(Node<Point> node) {
        for (Point value : node.values.values()) {
            if (point.approximateDistanceLessThan(value, radius)) {
                points.add(value);
            }
        }
    }

    @Override
    public boolean shouldProcess(Node<Point> node) {
        return node.contains(point.x, point.y) ||
               point.approximateDistanceLessThan(point(node.minx, node.miny), radius) ||
               point.approximateDistanceLessThan(point(node.minx, node.maxy), radius) ||
               point.approximateDistanceLessThan(point(node.maxx, node.miny), radius) ||
               point.approximateDistanceLessThan(point(node.maxx, node.maxy), radius);
    }

    public List<Point> points() {
        return points;
    }

    public static WithinDistanceProcessor withinDistance(Point point, double radius) {
        return new WithinDistanceProcessor(point, radius);
    }
}
