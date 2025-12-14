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
            return "Invalid Month";
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
        return 1234;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) { 
        return 1234; 
    }
    
    public static String bestMonthForCommodity(String comm) { 
        return "DUMMY"; 
    }

    public static int consecutiveLossDays(String comm) { 
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) { 
        return 1234; 
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