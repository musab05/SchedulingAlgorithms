import java.util.*;

class Process {
  int Priority;
  int pid;
  int startTime;
  int turnaroundTime;
  int waitingTime;
  int processId;
  int arrivalTime;
  int burstTime;
  int completionTime;
  int burstTimeOrg;
  int priority; // Added priority field
  boolean completed;

  public Process(int processId, int arrivalTime, int burstTime, int burstTimeOrg) {
    this.processId = processId;
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
    this.completionTime = 0;
    this.completed = false;
    this.burstTimeOrg = burstTimeOrg;
  }

  public Process(int processId, int arrivalTime, int burstTime, int burstTimeOrg, int priority) {
    this.processId = processId;
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
    this.completionTime = 0;
    this.completed = false;
    this.burstTimeOrg = burstTimeOrg;
    this.priority = priority;
  }

  // Added default constructor
  public Process() {
  }
}

public class Main {
  static int Priority;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Choose Algorithm:");
    System.out.println("1. FCFS");
    System.out.println("2. SJF (Non-Preemptive)");
    System.out.println("3. SJF (Preemptive)");
    System.out.println("4. Priority (Non-Preemptive)");
    System.out.println("5. Round Robin");
    System.out.println("6. Exit");

    while (true) {
      int choice = scanner.nextInt();
      switch (choice) {
        case 1:
          executeFCFS(scanner);
          break;
        case 2:
          executeSJFNonPreemptive(scanner);
          break;
        case 3:
          executeSJFPreemptive(scanner);
          break;
        case 4:
          executePriorityNonPreemptive(scanner);
          break;
        case 5:
          executeRoundRobin(scanner);
          break;
        case 6:
          scanner.close();
          break;
        default:
          System.out.println("Invalid choice.");
      }

    }
  }

  public static void executeFCFS(Scanner scanner) {
    System.out.print("Enter the number of processes: ");
    int n = scanner.nextInt();

    int[] arrivalTime = new int[n];
    int[] burstTime = new int[n];
    int[] completionTime = new int[n];
    int[] turnaroundTime = new int[n];
    int[] waitList = new int[n];
    float avgTAT = 0;
    float avgWT = 0;
    int currentTime = 0;

    System.out.println("Enter arrival time and burst time for each process:");

    for (int i = 0; i < n; i++) {
      System.out.print("Arrival time of process" + (i + 1) + ": ");
      arrivalTime[i] = scanner.nextInt();
      System.out.print("Burst time of process" + (i + 1) + ": ");
      burstTime[i] = scanner.nextInt();
    }

    for (int i = 0; i < n; i++) {
      if (currentTime < arrivalTime[i]) {
        currentTime = arrivalTime[i];
      }

      completionTime[i] = currentTime + burstTime[i];
      turnaroundTime[i] = completionTime[i] - arrivalTime[i];
      waitList[i] = turnaroundTime[i] - burstTime[i];

      currentTime = completionTime[i];

      avgTAT += turnaroundTime[i];
      avgWT += waitList[i];
    }

    System.out.println("Processno\t" + "ArrivalTime\t" + "BurstTime\t" +
        "CompletionTime\t" + "TurnAroundTime\t" + "Waitlist");
    for (int i = 0; i < n; i++) {
      System.out.print((i + 1) + "\t\t\t" + arrivalTime[i] + "\t\t\t" + burstTime[i] + "\t\t\t" +
          completionTime[i] + "\t\t\t\t" + turnaroundTime[i] + "\t\t\t\t" + waitList[i] + "\n");
    }
    System.out.print("\tAvgTAT = " + (avgTAT / n) + "\tAvgWT = " + (avgWT / n));
  }

  public static void executeSJFNonPreemptive(Scanner scanner) {
    System.out.print("Enter the number of processes: ");
    int n = scanner.nextInt();

    int[] arrivalTime = new int[n];
    int[] burstTime = new int[n];
    int[] completionTime = new int[n];
    int[] turnaroundTime = new int[n];
    int[] waitingTime = new int[n];
    boolean[] isVisited = new boolean[n];
    int avgtat = 0;
    int avgwt = 0;

    System.out.println("Enter arrival time and burst time for each process:");

    for (int i = 0; i < n; i++) {
      System.out.print("Arrival time for Process " + (i + 1) + ": ");
      arrivalTime[i] = scanner.nextInt();
      System.out.print("Burst time for Process " + (i + 1) + ": ");
      burstTime[i] = scanner.nextInt();
    }

    Integer[] sortedIndex = new Integer[n];
    for (int i = 0; i < n; i++) {
      sortedIndex[i] = i;
    }
    Arrays.sort(sortedIndex, (a, b) -> Integer.compare(arrivalTime[a], arrivalTime[b]));

    int currentTime = 0;

    for (int i = 0; i < n; i++) {
      int index = -1;
      for (int j = 0; j < n; j++) {
        if (!isVisited[j] && arrivalTime[j] <= currentTime && (index == -1 || burstTime[j] < burstTime[index])) {
          index = j;
        }
      }

      if (index != -1) {
        completionTime[index] = currentTime + burstTime[index];
        turnaroundTime[index] = completionTime[index] - arrivalTime[index];
        waitingTime[index] = turnaroundTime[index] - burstTime[index];

        currentTime = completionTime[index];
        isVisited[index] = true;
      }
    }

    System.out.println("\nProcess\t Arrival Time\t Burst Time\t Completion Time\t Turnaround Time\t Waiting Time");

    for (int i = 0; i < n; i++) {
      avgtat += turnaroundTime[i];
      avgwt += waitingTime[i];
      int index = sortedIndex[i];
      System.out.println((index + 1) + "\t\t" + arrivalTime[index] + "\t\t" + burstTime[index] + "\t\t" +
          completionTime[index] + "\t\t" + turnaroundTime[index] + "\t\t" + waitingTime[index]);
    }
    System.out.print("\tAvgTAT = " + (avgtat / n) + "\tAvgWT = " + (avgwt / n));
  }

  public static void executeSJFPreemptive(Scanner scanner) {
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
    System.out.println("Average TAT = " + avgtat / n + "\t\t " + "Average WT = " +
        avgwt / n);
  }

  public static void executePriorityNonPreemptive(Scanner scanner) {
    System.out.print("Enter the number of processes: ");
    int n = scanner.nextInt();

    List<Process> processes = new ArrayList<>();

    System.out.println("Enter arrival time and burst time for each process:");

    for (int i = 0; i < n; i++) {
      System.out.print("Arrival time for Process " + (i + 1) + ": ");
      int arrivalTime = scanner.nextInt();
      System.out.print("Burst time for Process " + (i + 1) + ": ");
      int burstTime = scanner.nextInt();
      System.out.print("Priority for Process " + (i + 1) + ": ");
      int Priority = scanner.nextInt();
      int burstTimeOrg = burstTime;

      processes.add(new Process(i + 1, arrivalTime, burstTime, burstTimeOrg,
          Priority));
    }

    int currentTime = 0;
    int completedProcesses = 0;
    float avgtat = 0;
    float avgwt = 0;

    System.out.println("\nGantt Chart:");

    while (completedProcesses < n) {
      Process shortestJob = null;

      int shortestPriority = Integer.MAX_VALUE;

      for (Process process : processes) {
        if (!process.completed && process.arrivalTime <= currentTime
            && process.Priority < shortestPriority) {
          shortestJob = process;
          shortestPriority = process.Priority;
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

    // Display results
    System.out.println(
        "\nP\tAT\tBT\tPr\tCT\tTAT\tWT");

    for (Process process : processes) {
      avgtat += process.completionTime -
          process.arrivalTime;
      avgwt += process.completionTime - process.arrivalTime - process.burstTimeOrg;
      System.out.println(process.processId + "\t" + process.arrivalTime + "\t"
          + process.burstTimeOrg + "\t" + process.Priority + "\t" +
          process.completionTime + "\t" + (process.completionTime -
              process.arrivalTime)
          + "\t" +
          (process.completionTime - process.arrivalTime - process.burstTimeOrg));
    }
    System.out.println("Average TAT = " + avgtat / n + "\t\t " + "Average WT = "
        + avgwt / n);
  }

  public static void executeRoundRobin(Scanner scanner) {
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
        if (burstRemaining[i] > 0 && p[i].arrivalTime <= currentTime && mark[i] == 0) {
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
    System.out.println("P\t" + "AT\t" + "BT\t" + "CT\t" + "TAT\t" + "WT\t" + "\n");

    for (int i = 0; i < n; i++) {
      System.out.println(p[i].pid + "\t" + p[i].arrivalTime + "\t" + p[i].burstTime + "\t" + p[i].completionTime
          + "\t" + p[i].turnaroundTime + "\t" +
          p[i].waitingTime + "\t" + "\t" + "\n");
    }
    System.out.println("Average Turnaround Time = " + avgTurnaroundTime);
    System.out.println("Average Waiting Time = " + avgWaitingTime);
  }

}
