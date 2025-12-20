// Main.java — Students version

import java.util.*;
import java.nio.file.Paths;
import java.io.*;


public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};
    

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    static int[][][] profitData=new int[MONTHS][DAYS][COMMS];
    static boolean dataLoaded=false;
    public static void loadData() {
        for (int m = 0; m < MONTHS; m++) {
            String fileName = months[m] + ".txt";
            String path = "../Data_Files/" + fileName;


            Scanner reader = null;
            try {
                reader = new Scanner(Paths.get(path));

                while (reader.hasNextLine()) {
                    String line = reader.nextLine().trim();
                    if (line.length() == 0) continue;


                    String[] parts = line.split(",");
                    if (parts.length != 3) continue;

                    int day;
                    int profit;
                    try {
                        day = Integer.parseInt(parts[0].trim());
                        profit = Integer.parseInt(parts[2].trim());
                    } catch (Exception e) {
                        continue;
                    }

                    String comm = parts[1].trim();
                    int cIndex = commodityIndex(comm);
                    if (cIndex == -1) continue;
                    if (day < 1 || day > DAYS) continue;

                    profitData[m][day - 1][cIndex] = profit;
                }

            } catch (IOException e) {

            } finally {
                if (reader != null) reader.close();
            }
        }
    }
    private static int commodityIndex(String comm){
        if(comm==null)return-1;
        for(int i=0;i<COMMS;i++){
            if(commodities[i].equals(comm)) return i;
        }
        return -1;
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if(month<0||month>MONTHS){
            return "INVALID_MONTH";
        }
        int bestIndex=0;
        int bestSum=0;
        for(int d=0;d<DAYS;d++){
            bestSum+=profitData[month][d][0];
        }
        for(int c=1;c<COMMS;c++){
            int sum=0;
            for(int d=0;d<DAYS;d++){
                sum+=profitData[month][d][c];
            }
            if(sum>bestSum){
                bestSum=sum;
                bestIndex=c;
            }
        }
        return commodities[bestIndex] + " " + bestSum;

    }

    public static int totalProfitOnDay(int month, int day) {
        if(month<0||month>MONTHS){
            return -99999;
        }
        if(day<1||day>DAYS){
            return -99999;
        }
        int total=0;
        int dayIndex=day -1;
        for(int c=0;c<COMMS;c++){
            total+=profitData[month][dayIndex][c];
        }

        return total;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        int cIndex=commodityIndex(commodity);
        if(cIndex==1){
            return -99999;
        }
        if(from<1||to>DAYS||from>to){
            return -99999;
        }
        int sum=0;
        int start=from-1;
        int end=to-1;
        for(int m=0;m<MONTHS;m++){
            for(int d=start;d<=end;d++){
                sum+=profitData[m][d][cIndex];

            }
        }

        return sum;
    }

    public static int bestDayOfMonth(int month) {
        if(month<0||month>MONTHS){
            return -1;
        }
        int bestDay=1;
        int besttotal=totalProfitOnDay(month,1);
        for(int day=2;day<=DAYS;day++){
            int currentdayprofit=totalProfitOnDay(month,day);
            if(currentdayprofit>besttotal){
                besttotal=currentdayprofit;
                bestDay=day;
            }
        }
        return bestDay;
    }
    
    public static String bestMonthForCommodity(String comm) {
        int cINDEX=commodityIndex(comm);
        if(cINDEX==-1){
            return "INVALID_COMMODITY";
        }
        int bestMonth=0;
        int bestSum=0;
        for(int d=0;d<DAYS;d++){
            bestSum+=profitData[0][d][cINDEX];
        }
        for(int m=1;m<MONTHS;m++){
            int sum=0;
            for(int d=0;d<DAYS;d++){
                sum+=profitData[m][d][cINDEX];
                if (sum >= bestSum) {
                    bestSum = sum;
                    bestMonth = m;
                }
            }
        }


        return months[bestMonth];
    }

    public static int consecutiveLossDays(String comm) {
        int cINDEX=commodityIndex(comm);
        if(cINDEX==-1) {
            return -1;
        }
        int maxst=0;
        int currentst=0;
        for(int m=0;m<MONTHS;m++){
            for(int d=0;d<DAYS;d++){
                int profit=profitData[m][d][cINDEX];
                if(profit<0){
                    currentst++;
                    if(currentst>maxst){
                        maxst=currentst;
                    }
                } else {
                    currentst = 0;
                }
            }
        }
        return maxst;
    }
    
    public static int daysAboveThreshold(String comm, int threshold) {
        int cINDEX= commodityIndex(comm);
        if(cINDEX==-1){
            return -1;
        }
        int count=0;
        for(int m=0;m<MONTHS;m++){
            for(int d= 0;d<DAYS;d++){
                if(profitData[m][d][cINDEX]>threshold){
                    count++;
                }
            }
        }
        return count;
    }

    public static int biggestDailySwing(int month) {
        if(month<0||month>=MONTHS){
            return -99999;
        }
        int previous=totalProfitOnDay(month,1);
        int maxdiff=0;
        for(int day=2;day<=DAYS;day++){
            int current=totalProfitOnDay(month,day);
            int diff=current-previous;
            if(diff<0){
                diff=-diff;
            }
            if(diff>maxdiff){
                maxdiff=diff;
            }
            previous=current;
        }
        return maxdiff;
    }
    
    public static String compareTwoCommodities(String c1, String c2) {
        int i1=commodityIndex(c1);
        int i2=commodityIndex(c2);
        if(i1==-1||i2==-1){
            return "INVALID_COMMODİTY";
        }
        int sum1=0;
        int sum2=0;
        for(int m=0;m<MONTHS;m++){
            for(int d=0;d<DAYS;d++){
                sum1+=profitData[m][d][i1];
                sum2+=profitData[m][d][i2];
            }
        }
        if(sum1==sum2){
            return "Equal";
        }
        int diff=Math.abs(sum1-sum2);
        if(sum1>sum2){
            return c1  +  " is better by " + diff;
        } else {
            return c2  +  " is better by " + diff;
        }
    }
    
    public static String bestWeekOfMonth(int month) {
        if(month<0||month>=MONTHS){
            return "INVALID_MONTH";
        }
        int bestweek=1;
        int bestsum=0;
        for(int day=1;day<=7;day++){
            bestsum+=totalProfitOnDay(month,day);
        }
        for(int week=2;week<=4;week++){
            int sum=0;
            int startday=(week-1)*7+1;
            int endday=week*7;
            for(int day=startday;day<=endday;day++){
                sum+=totalProfitOnDay(month,day);
            }
            if(sum>bestsum){
                bestsum=sum;
                bestweek=week;
            }
        }
        return "Week" + bestweek;
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
    }
