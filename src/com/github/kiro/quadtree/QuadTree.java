package com.github.kiro.quadtree;

import java.util.*;

import static com.github.kiro.quadtree.AddProcessor.pleaseAdd;
import static com.github.kiro.quadtree.Node.node;
import static com.github.kiro.quadtree.RemoveProcessor.pleaseRemove;
import static com.github.kiro.quadtree.WithinDistanceProcessor.withinDistance;

/**
 * QuadTree
 *
 * Case 1. Add point
 * Case 2. Update point
 * Case 3. Remove point
 * Case 4: k nearest
 *
 * Add up to a certain depth
 *
 */
public class QuadTree {
    private final int maxDepth;
    private final Node<Point> root;
    private final Map<Integer, Point> pointsCache;

    private QuadTree(double minx, double maxx, double miny, double maxy, int maxDepth) {
        this.root = node(minx, maxx, miny, maxy);
        this.maxDepth = maxDepth;
        this.pointsCache = new HashMap<Integer, Point>();
    }

    public void add(Point point) {
        root.traverse(maxDepth, pleaseAdd(point));
        pointsCache.put(point.id, point);
    }

    public void addAll(Point ... points) {
        for (Point point : points) {
            add(point);
        }
    }

    public void remove(Point point) {
        root.traverse(maxDepth, pleaseRemove(point));
        pointsCache.remove(point.id);
    }

    public void update(Point point) {
        remove(pointsCache.get(point.id));
        add(point);
    }

    public List<Point> kNearest(Point point, int k) {
        double radius = 0.5;

        List<Point> result = new ArrayList<Point>();

        while (radius < 33.0) {
            WithinDistanceProcessor withinDistance = withinDistance(point, radius);
            root.traverse(maxDepth, withinDistance);
            result = withinDistance.points();

            if (result.size() >= k) {
                break;
            }

            radius *= 2;
        }

        Collections.sort(result, distanceComparator(point));

        return result.subList(0, Math.min(result.size(), k));
    }

    public static class Builder {
        private double minx, maxx, miny, maxy;
        private int maxDepth;

        public Builder xaxis(double minx, double maxx) {
            this.minx = minx;
            this.maxx = maxx;
            return this;
        }

        public Builder yaxis(double miny, double maxy) {
            this.miny = miny;
            this.maxy = maxy;
            return this;
        }

        public Builder maxDepth(int maxDepth) {
            this.maxDepth = maxDepth;
            return this;
        }

        public QuadTree build() {
            return new QuadTree(minx, maxx, miny, maxy, maxDepth);
        }
    }

    private Comparator<Point> distanceComparator(final Point point) {
        return new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
               return Double.compare(p1.approximateDistance(point), p2.approximateDistance(point));
            }
        };
    }
}
