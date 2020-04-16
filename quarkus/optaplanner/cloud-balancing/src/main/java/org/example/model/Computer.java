package org.example.model;

import org.optaplanner.core.api.domain.lookup.PlanningId;

public class Computer {

    @PlanningId
    private Integer id;

    private Integer cpuPower;

    private Integer totalMemory;

    private Integer networkBandwidth;

    private Integer cost;

    public Computer(Integer id, Integer cpuPower, Integer totalMemory, Integer networkBandwidth, Integer cost) {
        this.id = id;
        this.cpuPower = cpuPower;
        this.totalMemory = totalMemory;
        this.networkBandwidth = networkBandwidth;
        this.cost = cost;
    }

    public Integer getCpuPower() {
        return cpuPower;
    }

    public void setCpuPower(Integer cpuPower) {
        this.cpuPower = cpuPower;
    }

    public Integer getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(Integer totalMemory) {
        this.totalMemory = totalMemory;
    }

    public Integer getNetworkBandwidth() {
        return networkBandwidth;
    }

    public void setNetworkBandwidth(Integer networkBandwidth) {
        this.networkBandwidth = networkBandwidth;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "id=" + id +
                ", cpuPower=" + cpuPower +
                ", totalMemory=" + totalMemory +
                ", networkBandwidth=" + networkBandwidth +
                ", cost=" + cost +
                '}';
    }
}
