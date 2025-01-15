import java.util.*;

class Process {
  int processId;
  int arrivalTime;
  int burstTime;
  int completionTime;
  int burstTimeOrg;
  boolean completed;
  int Priority;

  public Process(int processId, int arrivalTime, int burstTime, int burstTimeOrg, int Priority) {
    this.processId = processId;
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
    this.completionTime = 0;
    this.completed = false;
    this.burstTimeOrg = burstTimeOrg;
    this.Priority = Priority;
  }
}

public class Priority {
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

    scanner.close();
  }
}
