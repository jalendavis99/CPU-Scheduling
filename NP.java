import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class PriorityProcess {
    int id;
    int burstTime;
    int arrivalTime;
    int priority;
    int startTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;

    public PriorityProcess(int id, int burstTime, int arrivalTime, int priority) {
        this.id = id;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.startTime = 0;
        this.completionTime = 0;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
    }
}

public class NP {
    public static void main(String[] args) {
        List<PriorityProcess> processes = new ArrayList<>();
        processes.add(new PriorityProcess(1, 6, 0, 3));
        processes.add(new PriorityProcess(2, 8, 1, 1));
        processes.add(new PriorityProcess(3, 7, 2, 2));
        processes.add(new PriorityProcess(4, 3, 3, 4));
        processes.add(new PriorityProcess(5, 5, 4, 5));

        // Sort processes by arrival time
        Collections.sort(processes, (p1, p2) -> p1.arrivalTime - p2.arrivalTime);

        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        List<PriorityProcess> completedProcesses = new ArrayList<>();

        for (PriorityProcess process : processes) {
            // Calculate waiting time
            int waitingTime = currentTime - process.arrivalTime;
            if (waitingTime < 0) {
                waitingTime = 0;
                currentTime = process.arrivalTime;
            }

            // Update start time
            process.startTime = currentTime;

            // Update current time
            currentTime += process.burstTime;

            // Calculate turnaround time
            int turnaroundTime = waitingTime + process.burstTime;

            // Update total turnaround time and waiting time
            totalTurnaroundTime += turnaroundTime;
            totalWaitingTime += waitingTime;

            // Set completion time and turnaround time
            process.completionTime = currentTime;
            process.turnaroundTime = turnaroundTime;
            process.waitingTime = waitingTime;

            // Add the completed process to the list
            completedProcesses.add(process);
        }

        double averageTurnaroundTime = (double) totalTurnaroundTime / processes.size();
        double averageWaitingTime = (double) totalWaitingTime / processes.size();

        System.out.println("All processes executed!");
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
        System.out.println("Average Waiting Time: " + averageWaitingTime);

        // Print detailed information for each process
        System.out.println("\nProcess Details:");
        System.out.println("ID\tBurst Time\tArrival Time\tPriority\tStart Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (PriorityProcess process : completedProcesses) {
            System.out.printf("%d\t%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t\t%d\t\t\t%d%n",
                    process.id, process.burstTime, process.arrivalTime, process.priority,
                    process.startTime, process.completionTime, process.turnaroundTime, process.waitingTime);
        }
    }
}
