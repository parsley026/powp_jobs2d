package edu.kis.powp.jobs2d.drivers.adapter;

import edu.kis.powp.jobs2d.AbstractDriver;
import edu.kis.powp.jobs2d.Job2dDriver;

public class DriverAdapter extends AbstractDriver implements Job2dDriver {
    private final Job2dDriver abstractDriver;

    public DriverAdapter(Job2dDriver abstractDriver) {
        super(0, 0);
        this.abstractDriver = abstractDriver;
    }

    @Override
    public void operateTo(int x, int y) {
        abstractDriver.operateTo(x, y);
    }

    @Override
    public String toString() {
        return abstractDriver.toString();
    }
}
