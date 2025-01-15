import java.util.*;

public class SJF {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

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
    System.out.println("Average TAT" + avgtat + "\t\t " + "Average WT" + avgwt);
    scanner.close();
  }
}
