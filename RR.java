import java.util.*;

class RRProcess {
    int id;
    int burstTime;
    int arrivalTime;
    int priority;
    int remainingTime;
    int completedTime;
    int turnaroundTime;
    int waitingTime;

    public RRProcess(int id, int burstTime, int arrivalTime, int priority) {
        this.id = id;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.remainingTime = burstTime;
    }
}

public class RR {
    public static void main(String[] args) {
        List<RRProcess> processes = new ArrayList<>();
        processes.add(new RRProcess(1, 10, 0, 2));
        processes.add(new RRProcess(2, 5, 1, 1));
        processes.add(new RRProcess(3, 8, 2, 3));
        processes.add(new RRProcess(4, 6, 3, 2));
        processes.add(new RRProcess(5, 7, 4, 1));

        int quantum = 2; // Time quantum for each process

        // Sort processes by arrival time
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        Queue<RRProcess> queue = new LinkedList<>();
        List<RRProcess> completedProcesses = new ArrayList<>();

        int currentTime = 0;
        while (!processes.isEmpty() || !queue.isEmpty()) {
            // Add processes to the queue that have arrived by the current time
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                queue.add(processes.remove(0));
            }

            if (!queue.isEmpty()) {
                RRProcess currentProcess = queue.poll();

                // Check if the process can complete within the quantum or not
                if (currentProcess.remainingTime <= quantum) {
                    currentTime += currentProcess.remainingTime;
                    currentProcess.remainingTime = 0;
                    currentProcess.completedTime = currentTime;
                    completedProcesses.add(currentProcess);
                } else {
                    currentTime += quantum;
                    currentProcess.remainingTime -= quantum;
                    queue.add(currentProcess);
                }
            } else {
                currentTime++;
            }
        }

        // Calculate turnaround time and waiting time for each process
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        for (RRProcess process : completedProcesses) {
            int turnaroundTime = process.completedTime - process.arrivalTime;
            int waitingTime = turnaroundTime - process.burstTime;

            process.turnaroundTime = turnaroundTime;
            process.waitingTime = waitingTime;

            totalTurnaroundTime += turnaroundTime;
            totalWaitingTime += waitingTime;
        }

        // Calculate average turnaround time and average waiting time
        double averageTurnaroundTime = (double) totalTurnaroundTime / completedProcesses.size();
        double averageWaitingTime = (double) totalWaitingTime / completedProcesses.size();

        // Print process details
        System.out.println("Process\tBurst Time\tArrival Time\tPriority\tTurnaround Time\tWaiting Time");
        for (RRProcess process : completedProcesses) {
            System.out.println(process.id + "\t\t" + process.burstTime + "\t\t" + process.arrivalTime + "\t\t"
                    + process.priority + "\t\t" + process.turnaroundTime + "\t\t\t" + process.waitingTime);
        }

        // Print average turnaround time and average waiting time
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
        System.out.println("Average Waiting Time: " + averageWaitingTime);
    }
}
