import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Process {
    int id;
    int burstTime;
    int arrivalTime;
    int startTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;

    public Process(int id, int burstTime, int arrivalTime) {
        this.id = id;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.startTime = 0;
        this.completionTime = 0;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
    }
}

public class FCFS {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 6, 0));
        processes.add(new Process(2, 8, 1));
        processes.add(new Process(3, 7, 2));
        processes.add(new Process(4, 3, 3));
        processes.add(new Process(5, 5, 4));

        // Sort processes by arrival time
        Collections.sort(processes, (p1, p2) -> p1.arrivalTime - p2.arrivalTime);

        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        for (Process process : processes) {
            // Set the start time of the process
            process.startTime = Math.max(process.arrivalTime, currentTime);
            // Calculate completion time
            process.completionTime = process.startTime + process.burstTime;
            // Calculate turnaround time
            process.turnaroundTime = process.completionTime - process.arrivalTime;
            // Calculate waiting time
            process.waitingTime = process.turnaroundTime - process.burstTime;

            // Update current time
            currentTime = process.completionTime;

            // Accumulate total turnaround time and total waiting time
            totalTurnaroundTime += process.turnaroundTime;
            totalWaitingTime += process.waitingTime;

            // Print process details
            System.out.println("Process ID: " + process.id);
            System.out.println("Start Time: " + process.startTime);
            System.out.println("Completion Time: " + process.completionTime);
            System.out.println("Turnaround Time: " + process.turnaroundTime);
            System.out.println("Waiting Time: " + process.waitingTime);
            System.out.println("---------------------------------");
        }

        double averageTurnaroundTime = (double) totalTurnaroundTime / processes.size();
        double averageWaitingTime = (double) totalWaitingTime / processes.size();

        System.out.println("All processes executed!");
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
        System.out.println("Average Waiting Time: " + averageWaitingTime);
    }
}
