package com.gatedev.bobble.ui.graphics;

import java.nio.FloatBuffer;

/**
 * User: Gianluca
 * Date: 31/05/13
 * Time: 17.34
 */
public class Mesh2d {

    private FloatBuffer vertexArray, colorArray, texCoordArray;

    public void setVertexArray(FloatBuffer vertexArray) {
        this.vertexArray = vertexArray;
    }

    public FloatBuffer getVertexArray() {
        return vertexArray;
    }

    public void setColorArray(FloatBuffer colorArray) {
        this.colorArray = colorArray;
    }

    public FloatBuffer getColorArray() {
        return colorArray;
    }

    public void setTexCoordArray(FloatBuffer texCoordArray) {
        this.texCoordArray = texCoordArray;
    }

    public FloatBuffer getTexCoordArray() {
        return texCoordArray;
    }

}