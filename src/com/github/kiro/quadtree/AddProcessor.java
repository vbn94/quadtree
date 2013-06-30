package com.github.kiro.quadtree;

import com.google.common.collect.Lists;

/**
 * AddProcessor
 */
class AddProcessor implements Processor<Point> {
    private final Point point;

    private AddProcessor(Point point) {
        this.point = point;
    }

    @Override
    public void process(Node<Point> node) {
        node.values.put(point.id, point);
    }

    @Override
    public boolean shouldProcess(Node<Point> node) {
        boolean shouldProcess = node.contains(point.x, point.y);

        if (shouldProcess) {
            node.count++;

            if (node.children.isEmpty()) {
                double midx = (node.minx + node.maxx) / 2;
                double midy = (node.miny + node.maxy) / 2;

                node.children = Lists.newArrayList(
                        new Node<Point>(node.minx, midx, node.miny, midy),
                        new Node<Point>(node.minx, midx, midy, node.maxy),
                        new Node<Point>(midx, node.maxx, node.miny, midy),
                        new Node<Point>(midx, node.maxx, midy, node.maxy)
                );
            }

        }

        return shouldProcess;
    }

    public static AddProcessor pleaseAdd(Point point) {
        return new AddProcessor(point);
    }
}
