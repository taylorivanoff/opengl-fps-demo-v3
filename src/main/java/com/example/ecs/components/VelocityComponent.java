package com.example.ecs.components;

import com.example.ecs.Component;

public class VelocityComponent extends Component {
    public float vx, vy, vz;

    public VelocityComponent(float vx, float vy, float vz) {
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }

    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public float getVz() {
        return vz;
    }

    public void setVz(float vz) {
        this.vz = vz;
    }

    public String toString() {
        return "VelocityComponent{" +
                "vx=" + vx +
                ", vy=" + vy +
                ", vz=" + vz +
                '}';
    }
}