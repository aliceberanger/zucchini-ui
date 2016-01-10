package example.reporting.scenario.domain;

import example.reporting.shared.domain.BasicInfo;
import example.reporting.shared.domain.Location;

import java.util.ArrayList;
import java.util.List;

public class Background {

    private BasicInfo info;

    private Location location;

    private List<Step> steps = new ArrayList<>();

    public BasicInfo getInfo() {
        return info;
    }

    public void setInfo(final BasicInfo info) {
        this.info = info;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(final List<Step> steps) {
        this.steps = steps;
    }

}
