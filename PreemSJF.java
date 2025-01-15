import java.util.*;

class Process {
int processId;
int arrivalTime;
int burstTime;
int completionTime;
int burstTimeOrg;
boolean completed;

public Process(int processId, int arrivalTime, int burstTime, int
burstTimeOrg) {
this.processId = processId;
this.arrivalTime = arrivalTime;
this.burstTime = burstTime;
this.completionTime = 0;
this.completed = false;
this.burstTimeOrg = burstTimeOrg;
}
}

public class PreemSJF {
public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);

System.out.print("Enter the number of processes: ");
int n = scanner.nextInt();

List<Process> processes = new ArrayList<>();

System.out.println("Enter arrival time and burst time for each process:");

for (int i = 0; i < n; i++) {
System.out.print("Arrival time for Process " + (i + 1) + ": ");
int arrivalTime = scanner.nextInt();
System.out.print("Burst time for Process " + (i + 1) + ": ");
int burstTime = scanner.nextInt();
int burstTimeOrg = burstTime;

processes.add(new Process(i + 1, arrivalTime, burstTime, burstTimeOrg));
}

int currentTime = 0;
int completedProcesses = 0;
int avgtat = 0;
int avgwt = 0;

System.out.println("\nGantt Chart:");

while (completedProcesses < n) {
Process shortestJob = null;
int shortestBurstTime = Integer.MAX_VALUE;

for (Process process : processes) {
if (!process.completed && process.arrivalTime <= currentTime &&
process.burstTime < shortestBurstTime) {
shortestJob = process;
shortestBurstTime = process.burstTime;
}
}

if (shortestJob != null) {
System.out.print("P" + shortestJob.processId + " ");
currentTime++;
shortestJob.burstTime--;

if (shortestJob.burstTime == 0) {
shortestJob.completionTime = currentTime;
shortestJob.completed = true;
completedProcesses++;
}
} else {
System.out.print("Idle ");
currentTime++;
}
}

System.out.println("\nProcessno\t" + "ArrivalTime\t" + "BurstTime\t" +
"CompletionTime\t" + "TurnAroundTime\t" + "Waitlist");

for (Process process : processes) {
avgtat += process.completionTime -
process.arrivalTime;
avgwt += process.completionTime - process.arrivalTime - process.burstTimeOrg;
System.out.println(process.processId + "\t\t" + process.arrivalTime + "\t\t"
+ process.burstTimeOrg + "\t\t" +
process.completionTime + "\t\t" + (process.completionTime -
process.arrivalTime)
+ "\t\t" +
(process.completionTime - process.arrivalTime - process.burstTimeOrg));
}
System.out.println("Average TAT = " + avgtat + "\t\t " + "Average WT = " +
avgwt);
scanner.close();
}
}
