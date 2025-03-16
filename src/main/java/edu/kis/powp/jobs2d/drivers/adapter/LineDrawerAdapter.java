package edu.kis.powp.jobs2d.drivers.adapter;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.legacy.drawer.shape.line.AbstractLine;
import edu.kis.legacy.drawer.shape.line.BasicLine;
import edu.kis.powp.jobs2d.Job2dDriver;

import java.awt.*;
import java.lang.reflect.Field;

public class LineDrawerAdapter extends DrawPanelController implements Job2dDriver {
    private int startX = 0, startY = 0;

    private Color color = Color.BLACK;
    private float thickness = 3.0f;
    private boolean dotted = false;

    private final DrawPanelController drawer;

    public LineDrawerAdapter(DrawPanelController drawPanelController) {
        super();
        this.drawer = drawPanelController;
    }

    @Override
    public void setPosition(int x, int y) {
        this.startX = x;
        this.startY = y;
    }

    public void useBasicLine() {
        this.color = LineFactory.getBasicLine().getColor();
        this.thickness = LineFactory.getBasicLine().getThickness();
        this.dotted = LineFactory.getBasicLine().isDotted();
    }

    public void useDottedLine() {
        this.color = LineFactory.getDottedLine().getColor();
        this.thickness = LineFactory.getDottedLine().getThickness();
        this.dotted = LineFactory.getDottedLine().isDotted();
    }

    public void useSpecialLine() {
        this.color = LineFactory.getSpecialLine().getColor();
        this.thickness = LineFactory.getSpecialLine().getThickness();
        this.dotted = LineFactory.getSpecialLine().isDotted();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    public void setDotted(boolean dotted) {
        this.dotted = dotted;
    }

    @Override
    public void operateTo(int x, int y) {
        AbstractLine abstractLine = new BasicLine();

        try {
            // Modify 'color'
            Field fieldColor = AbstractLine.class.getDeclaredField("color");
            fieldColor.setAccessible(true);
            fieldColor.set(abstractLine, this.color);

            // Modify 'thickness'
            Field fieldThickness = AbstractLine.class.getDeclaredField("thickness");
            fieldThickness.setAccessible(true);
            fieldThickness.set(abstractLine, this.thickness);

            // Modify 'dotted'
            Field fieldDotted = AbstractLine.class.getDeclaredField("dotted");
            fieldDotted.setAccessible(true);
            fieldDotted.set(abstractLine, this.dotted);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        abstractLine.setStartCoordinates(this.startX, this.startY);
        abstractLine.setEndCoordinates(x, y);
        drawer.drawLine(abstractLine);
        this.setPosition(x, y);
    }

    @Override
    public String toString() {
        return "@Q!$!@$!#@$(*#@&Q(%^*#@";
    }
}
