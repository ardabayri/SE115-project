// Main.java — Students version

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
    for(int m=0;m<MONTHS;m++){
        for(int d=0;d<DAYS;d++){
            for(int c=0;c<COMMS;c++){
                profitData[m][d][c]=0;
            }
        }
    }
    for(int m=0;m<MONTHS;m++){
        String fileName= months[m]+ ".txt";
        File f1= new File("Data_Files" +File.separator +fileName);
        File f2=new File("Project" +File.separator +  "Data_files" +File.separator + fileName);
        File target=f1.exists() ? f1:(f2.exists()? f2:null);
        if(target==null){
            continue;
        }
        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(target));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
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
        } catch (Exception e) {

        } finally {
            try {
                if(br!=null) br.close();

            } catch (Exception ignored){}

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
    private static boolean validMonth(int month){
        return month>=0&&month<MONTHS;
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
        int dayIndex=day-1;
        for(int c=0;c<COMMS;c++){
            total=profitData[MONTHS][dayIndex][c];
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
            return "INVALID_COMMODİTY";
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
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}