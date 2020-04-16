package org.example.generator;

import com.sun.tools.javac.util.List;
import org.example.model.CloudBalance;
import org.example.model.Computer;
import org.example.model.Process;

import javax.enterprise.context.Dependent;

@Dependent
public class CloudBalanceProblemGenerator {

    public CloudBalance generate() {
        CloudBalance unsolvedCloudBalance = new CloudBalance();

        List<Computer> computerList = List.of(
                new Computer(1, 10, 20, 15, 7),
                new Computer(2, 15, 23, 12, 4),
                new Computer(3, 20, 33, 17, 3)
        );
        unsolvedCloudBalance.setComputerList(computerList);

        List<Process> processList = List.of(
                new Process(1, 5, 10, 8),
                new Process(2, 8, 10, 8),
                new Process(3, 10, 6, 4)
        );
        unsolvedCloudBalance.setProcessList(processList);

        return unsolvedCloudBalance;
    }

}
