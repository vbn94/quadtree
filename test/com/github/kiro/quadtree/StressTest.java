package com.github.kiro.quadtree;

import junit.framework.TestCase;
import sun.jvm.hotspot.types.CIntegerType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.github.kiro.quadtree.Point.point;
import static java.lang.Math.abs;
import static java.lang.Math.random;

/**
 * StressTest
 */
public class StressTest extends TestCase {
    private final double WEST = -0.534425;
    private final double EAST = 0.183806;
    private final double NORTH = 51.648703;
    private final double SOUTH = 51.34777;

    private QuadTree quadTree;

    private static double randomMove() {
        return Math.signum(Math.random() - 0.5) * 0.002 * Math.random();
    }

    public void setUp() {
        quadTree = new QuadTree.Builder()
                .xaxis(-180.0, 180.0)
                .yaxis(-90.0, 90.0)
                .maxDepth(17)
                .build();
    }

    private Point generatePoint(int id, double x, double y) {
        Random random = new Random();
        x = x + 0.01 * random.nextGaussian();
        y = y + 0.01 * random.nextGaussian();
        return point(id, x, y);
    }

    public void testLondon() {
        List<Point> points = new ArrayList<Point>();

        for (int i = 0; i < 1000; i++) {
            Point point = generatePoint(i, (EAST + WEST) / 2, (NORTH + SOUTH) / 2);
            points.add(point);
            quadTree.add(point);
        }

        for (int i = 0; i < 10; i++) {
            for (Point point : points) {
                Point nextPoint = point.move(randomMove(), randomMove());
                quadTree.update(nextPoint);
            }
        }
    }

    public void testTheWorld() throws IOException, Exception {
        List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath("worldcapitals.csv"), Charset.defaultCharset());
        List<Point> points = new ArrayList<Point>();
        int id = 0;

        List<Point> cities = new ArrayList<Point>();

        for (String line : lines) {
            String [] parts = line.split(",");
            double y = Double.parseDouble(parts[3]);
            double x = Double.parseDouble(parts[4]);

            cities.add(point(0, x, y));

            for (int i = 0; i < 10000; i++) {
                Point point = generatePoint(++id, x, y);
                points.add(point);
                quadTree.add(point);
            }
        }

        int count = 0;
        for (Point city : cities) {
            for (int i = 0; i < 100; i++) {
                count++;
                Point query = generatePoint(0, city.x, city.y);
                List<Point> result = quadTree.kNearest(query, 7);
            }
        }

        System.out.println(count);
    }
}
