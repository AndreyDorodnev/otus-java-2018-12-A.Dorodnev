package start;

public class GC_CollectInfo {
     private Integer young_count;
     private Integer old_count;
     private Long young_time;
     private Long old_time;
     private Long total_time;
     private Integer total_count;

     public GC_CollectInfo(){
         old_time=0l;
         old_count=0;
         young_time=0l;
         young_count=0;
         total_time=0l;
         total_count = 0;
     }

     public void increaseOldGenCount(){old_count++;}
     public void increaseYoungGenCount(){young_count++;}
     public void addOldGenTime(long time){old_time+=time;}
     public void addYoungGenTime(long time){young_time+=time;}
     public void calcTotal(){
         total_count += old_count + young_count;
         total_time += old_time + young_time;
     }
     public void clear(){
         old_time=0l;
         old_count=0;
         young_time=0l;
         young_count=0;
     }
     public String getOldGenInfo(){
         return "Old Generation\n" + "Count: " + old_count.toString() +"\n" + "Time: " + old_time.toString() + "ms\n";
     }
    public String getYoungGenInfo(){
        return "Young Generation\n" + "Count: " + young_count.toString() +"\n" + "Time: " + young_time.toString() + "ms\n";
    }
    public String getTotalInfo(){
         return "Total collect count: " + total_count.toString() + "\n" + "Total collect time: " + total_time.toString() + "ms\n";
    }

}
