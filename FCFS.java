import java.util.*;

public class FCFS {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter number of process: ");
    int n = sc.nextInt();

    int[] arrivalTime = new int[n];
    int[] burstTime = new int[n];

    System.out.println("Enter value of Arrival time and Burst time:");

    for (int i = 0; i < n; i++) {
      System.out.print("Arrival time of process" + (i + 1) + ": ");
      arrivalTime[i] = sc.nextInt();
      System.out.print("Burst time of process" + (i + 1) + ": ");
      burstTime[i] = sc.nextInt();
    }
    int[] completionTime = new int[n];
    int[] turnaroundTime = new int[n];
    int[] waitList = new int[n];
    float avgTAT = 0;
    float avgWT = 0;
    int currentTime = 0;
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

    System.out.println(
        "Processno\t" + "ArrivalTime\t" + "BurstTime\t" + "CompletionTime\t" + "TurnAroundTime\t" + "Waitlist");
    for (int i = 0; i < n; i++) {

      System.out.print((i + 1) + "\t\t\t" + arrivalTime[i] + "\t\t\t" + burstTime[i] + "\t\t\t" + completionTime[i]
          + "\t\t\t\t" + turnaroundTime[i] + "\t\t\t\t" + waitList[i] + "\n");
    }
    System.out.print("\tAvgTAT = " + (avgTAT / n) + "\tAvgWT = " + (avgWT / n));
  }
}
