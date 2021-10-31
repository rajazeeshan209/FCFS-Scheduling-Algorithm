import java.util.Scanner;
import javax.swing.ProgressMonitor;
import java.lang.Character;

class FCFS_Scheduler{
    static String [] processes;
    static int [] burstTime;
    static int [] arrivalTime;
    static int [] waitingTime;
    static int [] turnAroundTime;
    static boolean isArrivalTimeDiff;

    static Scanner input = new Scanner(System.in);

    static void createProcesses(int n){
        processes = new String[n];
        burstTime = new int[n];
        arrivalTime = new int[n];
        waitingTime = new int[n];
        turnAroundTime = new int[n];

        for(int i=0; i<n; i++){
            System.out.print("Enter Process "+(i+1)+" Name: ");
            processes[i] = input.next();
        }

        for(int i=0; i<n; i++){
            System.out.print("Enter Burst Time of Process "+(i+1)+": ");
            burstTime[i] = input.nextInt();
        }

        System.out.print("Do the Processes have different Arrival Time (Y/N): ");
        char check = input.next().charAt(0);

        if(check == 'Y' || check == 'y'){
            isArrivalTimeDiff = true;
        }
        else{
            isArrivalTimeDiff = false;
        }

        if(isArrivalTimeDiff == true){
            for(int i=0; i<n; i++){
                System.out.print("Enter Arrival Time of Process "+(i+1)+": ");
                arrivalTime[i] = input.nextInt();
            }
        }


    }

    static void withoutArrivalTime(int n){
        waitingTime[0] = 0;

        for(int i = 1; i<n; i++){
            waitingTime[i] = burstTime[i-1] + waitingTime[i-1];
        }

        for(int i = 0; i<n; i++){
            turnAroundTime[i] = waitingTime[i] + burstTime[i];
        }

    }

    static void withArrivalTime(int n){
        String [] temp_processes = new String [n];
        int [] temp_burstTime = new int [n];
        int [] temp_arrivalTime = new int [n];

        waitingTime[0] = 0;

        for(int i = 0; i<n; i++){
            int a_min = 999999999;
            int e_processNumber = -1;

            for(int l = 0; l<n; l++){           
                if(arrivalTime[l]<a_min && arrivalTime[l] > -1){
                    e_processNumber = l;
                    a_min = arrivalTime[l];
                }
            }

            temp_processes[i] = processes[e_processNumber];
            temp_burstTime[i] = burstTime[e_processNumber];
            temp_arrivalTime[i] = a_min;

            arrivalTime[e_processNumber] = -1;

            if(i<n-1){
                waitingTime[i+1] = temp_burstTime[i] + waitingTime[i];
            }

            turnAroundTime[i] = waitingTime[i] + temp_burstTime[i];
        }

        for(int i=0; i<n; i++){
            waitingTime[i] = waitingTime[i]- temp_arrivalTime[i];
        }

        processes = temp_processes;
        burstTime = temp_burstTime;
        arrivalTime = temp_arrivalTime;

    }

    static float averageValue(int[] n_values, int n){
        float sum = 0;
        for(int i = 0; i<n; i++){
            sum = sum + n_values[i];
        }

        return sum/n;
    }

    static void displaySchedular(int n){
        if(isArrivalTimeDiff == true){
            System.out.println("P\tB.T\tA.T\tW.T\tTA.T");
            for(int i = 0; i<n; i++){
                System.out.println(processes[i]+"\t"+burstTime[i]+"\t"+arrivalTime[i]+"\t"+waitingTime[i]+"\t"+turnAroundTime[i]);
            }
            System.out.println("Average Waiting Time: "+averageValue(waitingTime, n));
            System.out.println("Average Turn Around Time: "+averageValue(turnAroundTime, n));
        }
        else{
            System.out.println("P\tB.T\tW.T\tTA.T");
            for(int i = 0; i<n; i++){
                System.out.println(processes[i]+"\t"+burstTime[i]+"\t"+waitingTime[i]+"\t"+turnAroundTime[i]);
            }
            System.out.println("Average Waiting Time: "+averageValue(waitingTime, n));
            System.out.println("Average Turn Around Time: "+averageValue(turnAroundTime, n));
        }
    }

    public static void main(String[] args){
        System.out.print("Enter Number of Processes: ");
        int n = input.nextInt();
        createProcesses(n);

        if(isArrivalTimeDiff == true){
            withArrivalTime(n);
            displaySchedular(n);
        }
        else{
            withoutArrivalTime(n);
            displaySchedular(n);
        }

    }
}