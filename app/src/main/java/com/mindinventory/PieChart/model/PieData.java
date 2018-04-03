package com.mindinventory.PieChart.model;

import android.graphics.Paint;

/**
 * Created by MindInventory.
 * PieData : this indicates to getter & setter for data.
 */

public class PieData {

    private int color;
    private float percentage;
    private String name;
    private Paint paint;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
