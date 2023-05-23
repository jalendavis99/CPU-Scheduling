import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SJFSProcess {
    int id;
    int burstTime;
    int arrivalTime;
    int startTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;
    int remainingTime;

    public SJFSProcess(int id, int burstTime, int arrivalTime) {
        this.id = id;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.startTime = 0;
        this.completionTime = 0;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
        this.remainingTime = burstTime;
    }
}

public class SJFS {
    public static void main(String[] args) {
        List<SJFSProcess> processes = new ArrayList<>();
        processes.add(new SJFSProcess(1, 6, 0));
        processes.add(new SJFSProcess(2, 8, 1));
        processes.add(new SJFSProcess(3, 7, 2));
        processes.add(new SJFSProcess(4, 3, 3));
        processes.add(new SJFSProcess(5, 5, 4));

        // Sort processes by arrival time
        Collections.sort(processes, (p1, p2) -> p1.arrivalTime - p2.arrivalTime);

        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        List<SJFSProcess> readyQueue = new ArrayList<>();
        List<SJFSProcess> completedProcesses = new ArrayList<>();

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            // Add arrived processes to the ready queue
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                SJFSProcess process = processes.remove(0);
                readyQueue.add(process);
            }

            // Sort the ready queue based on burst time (SJF)
            Collections.sort(readyQueue, (p1, p2) -> p1.remainingTime - p2.remainingTime);

            // Execute the process with the shortest burst time
            if (!readyQueue.isEmpty()) {
                SJFSProcess currentProcess = readyQueue.remove(0);
                currentProcess.startTime = currentTime;
                currentProcess.completionTime = currentTime + currentProcess.remainingTime;
                currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;

                currentTime = currentProcess.completionTime;

                totalTurnaroundTime += currentProcess.turnaroundTime;
                totalWaitingTime += currentProcess.waitingTime;

                completedProcesses.add(currentProcess);

                // Update remaining time for other processes in the ready queue
                for (SJFSProcess process : readyQueue) {
                    process.waitingTime += currentProcess.remainingTime;
                }
            } else {
                currentTime++;
            }
        }

        double averageTurnaroundTime = (double) totalTurnaroundTime / completedProcesses.size();
        double averageWaitingTime = (double) totalWaitingTime / completedProcesses.size();

        System.out.println("All processes executed!");
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
        System.out.println("Average Waiting Time: " + averageWaitingTime);
    }
}
