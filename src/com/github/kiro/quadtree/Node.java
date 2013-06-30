package com.github.kiro.quadtree;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A QuadTree node.
 */
class Node<T> {
    // package private so they can be accessed by the processors
    final double minx, maxx, miny, maxy;

    Map<Integer, T> values;
    List<Node<T>> children;

    int count;

    public Node(double minx, double maxx, double miny, double maxy) {
        this.minx = minx;
        this.maxx = maxx;
        this.miny = miny;
        this.maxy = maxy;
        this.children = new ArrayList<Node<T>>();
    }

    public void traverse(int depth, Processor<T> processor) {
        if (depth == 0) {
            if (this.values == null) {
                this.values = new HashMap<Integer, T>();
            }
            processor.process(this);
        } else {
            if (processor.shouldProcess(this)) {
                for (Node<T> child : children) {
                    child.traverse(depth - 1, processor);
                }
            }
        }
    }

    public boolean contains(double x, double y) {
        return minx < x && x < maxx && miny < y && y < maxy;
    }

    public static <T> Node<T> node(double minx, double maxx, double miny, double maxy) {
        return new Node<T>(minx, maxx, miny, maxy);
    }

    @Override
    public String toString() {
        return minx + " " + maxx + " " + miny + " " + maxy + " " + values.size();
    }
}
