package start;

public class GC_CollectInfo {
     private Integer youngCount;
     private Integer oldCount;
     private Long youngTime;
     private Long oldTime;
     private Long totalTime;
     private Integer totalCount;

     public GC_CollectInfo(){
         oldTime =0l;
         oldCount =0;
         youngTime =0l;
         youngCount =0;
         totalTime =0l;
         totalCount = 0;
     }

     public void increaseOldGenCount(){
         oldCount++;}
     public void increaseYoungGenCount(){
         youngCount++;}
     public void addOldGenTime(long time){
         oldTime +=time;}
     public void addYoungGenTime(long time){
         youngTime +=time;}
     public void calcTotal(){
         totalCount += oldCount + youngCount;
         totalTime += oldTime + youngTime;
     }
     public void clear(){
         oldTime =0l;
         oldCount =0;
         youngTime =0l;
         youngCount =0;
     }
     public String getOldGenInfo(){
         return "Old Generation\n" + "Count: " + oldCount.toString() +"\n" + "Time: " + oldTime.toString() + "ms\n";
     }
    public String getYoungGenInfo(){
        return "Young Generation\n" + "Count: " + youngCount.toString() +"\n" + "Time: " + youngTime.toString() + "ms\n";
    }
    public String getTotalInfo(){
         return "Total collect count: " + totalCount.toString() + "\n" + "Total collect time: " + totalTime.toString() + "ms\n";
    }

}
