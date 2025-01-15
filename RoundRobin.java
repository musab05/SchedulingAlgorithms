import java.util.*;

class Process {
int pid;
int arrivalTime;
int burstTime;
int startTime;
int completionTime;
int turnaroundTime;
int waitingTime;
}

public class RoundRobin {

public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);

int n, tq;
Process[] p = new Process[100];
int[] burstRemaining = new int[100];
int totalTurnaroundTime = 0, totalWaitingTime = 0;
float avgTurnaroundTime, avgWaitingTime;
int idx;

System.out.print("Enter the number of processes: ");
n = scanner.nextInt();
System.out.print("Enter time quantum: ");
tq = scanner.nextInt();

for (int i = 0; i < n; i++) {
p[i] = new Process();
System.out.print("Enter arrival time of process " + (i + 1) + ": ");
p[i].arrivalTime = scanner.nextInt();
System.out.print("Enter burst time of process " + (i + 1) + ": ");
p[i].burstTime = scanner.nextInt();
burstRemaining[i] = p[i].burstTime;
p[i].pid = i + 1;
System.out.println();
}

Queue<Integer> q = new LinkedList<>();
int currentTime = 0;
q.add(0);
int completed = 0;
int[] mark = new int[100];
Arrays.fill(mark, 0);
mark[0] = 1;

while (completed != n) {
idx = q.poll();

if (burstRemaining[idx] == p[idx].burstTime) {
p[idx].startTime = Math.max(currentTime, p[idx].arrivalTime);
currentTime = p[idx].startTime;
}

if (burstRemaining[idx] - tq > 0) {
burstRemaining[idx] -= tq;
currentTime += tq;
} else {
currentTime += burstRemaining[idx];
burstRemaining[idx] = 0;
completed++;

p[idx].completionTime = currentTime;
p[idx].turnaroundTime = p[idx].completionTime - p[idx].arrivalTime;
p[idx].waitingTime = p[idx].turnaroundTime - p[idx].burstTime;

totalTurnaroundTime += p[idx].turnaroundTime;
totalWaitingTime += p[idx].waitingTime;
}

for (int i = 1; i < n; i++) {
if (burstRemaining[i] > 0 && p[i].arrivalTime <= currentTime && mark[i] == 0)
{
q.add(i);
mark[i] = 1;
}
}
if (burstRemaining[idx] > 0) {
q.add(idx);
}

if (q.isEmpty()) {
for (int i = 1; i < n; i++) {
if (burstRemaining[i] > 0) {
q.add(i);
mark[i] = 1;
break;
}
}
}
}

avgTurnaroundTime = (float) totalTurnaroundTime / n;
avgWaitingTime = (float) totalWaitingTime / n;

System.out.println();
System.out.println("P\t" + "AT\t" + "BT\t" + "CT\t" + "TAT\t" + "WT\t" +
"\n");

for (int i = 0; i < n; i++) {
System.out.println(p[i].pid + "\t" + p[i].arrivalTime + "\t" + p[i].burstTime
+ "\t" + p[i].completionTime
+ "\t" + p[i].turnaroundTime + "\t" +
p[i].waitingTime + "\t" + "\t" + "\n");
}
System.out.println("Average Turnaround Time = " + avgTurnaroundTime);
System.out.println("Average Waiting Time = " + avgWaitingTime);

scanner.close();
}
}
