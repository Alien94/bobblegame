package com.gatedev.bobble.ui.graphics;

/**
 * User: Gianluca
 * Date: 31/05/13
 * Time: 17.32
 */
public interface Triangulator {

    /**
     * Update the triangles
     */
    boolean triangulate();

    /**
     * Add a point to the polygon
     */
    void addPolyPoint(float x, float y);

    /**
     * Returns the triangles count.
     */
    int getTriangleCount();

    float[] getTrianglePoint(int tri, int i);

    float getTrianglePointX(int tri, int i);

    float getTrianglePointY(int tri, int i);

}