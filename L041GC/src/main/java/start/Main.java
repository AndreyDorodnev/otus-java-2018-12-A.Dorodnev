package start;

//-XX:+UseParallelGC
//-XX:+UseSerialGC
//-XX:+UseConcMarkSweepGC

import com.sun.management.GarbageCollectionNotificationInfo;
import javax.management.ListenerNotFoundException;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.*;

public class Main {
    private static List<Runnable> registration = new ArrayList<>();
    private static Timer timer = new Timer();
    private static GC_CollectInfo gcInf = new GC_CollectInfo();
    private static final long TIMER_PERIOD = 5*1000;

    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();
        int size = 10*1000*1000;
        installGCMonitoring();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(gcInf.getOldGenInfo());
                System.out.println(gcInf.getYoungGenInfo());
                gcInf.calcTotal();
                gcInf.clear();
            }
        },TIMER_PERIOD,TIMER_PERIOD);

        try{
            while (true){
                Object[] array = new Object[size];
                int i=0;
                while (i < size) {
                    array[i] = new String();
                    i++;
                }
                list.add(Arrays.copyOf(array, size / 64));
            }
        }
        catch (OutOfMemoryError err){
            System.out.println(gcInf.getTotalInfo());
            System.exit(0);
        }

    }
    private static void installGCMonitoring(){
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean gcbean : gcbeans) {

            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                        GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                        long duration = info.getGcInfo().getDuration();
                        String gctype = info.getGcAction();

                        switch (info.getGcName()){
                            case "Copy":
                            case "PS Scavenge":
                            case "ParNew":
                            case "G1 Young Generation":
                                gcInf.increaseYoungGenCount();
                                gcInf.addYoungGenTime(duration);
                                break;
                            case "PS MarkSweep":
                            case "MarkSweepCompact":
                            case "ConcurrentMarkSweep":
                            case "G1 Mixed Generation":
                            case "G1 Old Generation":
                                gcInf.increaseOldGenCount();
                                gcInf.addOldGenTime(duration);
                                break;
                        }

                    }
                }
            };

            emitter.addNotificationListener(listener, null, null);

            registration.add(() -> {
                try {
                    emitter.removeNotificationListener(listener);
                } catch (ListenerNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
