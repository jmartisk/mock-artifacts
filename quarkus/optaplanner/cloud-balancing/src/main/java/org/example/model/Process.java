package org.example.model;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Process {

    @PlanningId
    private Integer id;

    private Integer requiredCpuPower;

    private Integer requiredMemory;

    private Integer requiredNetworkBandwidth;

    @PlanningVariable(valueRangeProviderRefs = "computerList")
    private Computer computer;

    public Process() {
    }

    public Process(Integer id, Integer requiredCpuPower, Integer requiredMemory, Integer requiredNetworkBandwidth) {
        this.id = id;
        this.requiredCpuPower = requiredCpuPower;
        this.requiredMemory = requiredMemory;
        this.requiredNetworkBandwidth = requiredNetworkBandwidth;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public Integer getRequiredCpuPower() {
        return requiredCpuPower;
    }

    public void setRequiredCpuPower(Integer requiredCpuPower) {
        this.requiredCpuPower = requiredCpuPower;
    }

    public Integer getRequiredMemory() {
        return requiredMemory;
    }

    public void setRequiredMemory(Integer requiredMemory) {
        this.requiredMemory = requiredMemory;
    }

    public Integer getRequiredNetworkBandwidth() {
        return requiredNetworkBandwidth;
    }

    public void setRequiredNetworkBandwidth(Integer requiredNetworkBandwidth) {
        this.requiredNetworkBandwidth = requiredNetworkBandwidth;
    }

    @Override
    public String toString() {
        return "Process{" +
                "requiredCpuPower=" + requiredCpuPower +
                ", requiredMemory=" + requiredMemory +
                ", requiredNetworkBandwidth=" + requiredNetworkBandwidth +
                ", computer=" + (computer == null ? "null" : computer.getId()) +
                '}';
    }
}
