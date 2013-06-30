package com.github.kiro.quadtree;

import com.github.kiro.quadtree.Node;

import java.util.List;
import java.util.Map;

/**
 * Processor
 */
interface Processor<T> {
    public void process(Node<T> node);

    public boolean shouldProcess(Node<T> node);
}
