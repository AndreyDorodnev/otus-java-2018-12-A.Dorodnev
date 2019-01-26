package start;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        Benchmark benchmark = new Benchmark();
        ArrayList<Object> list = new ArrayList<>();

        while (true){
            benchmark.prepare();
            list.add(benchmark.measure1(String::new,"Create String Array"));
            //benchmark.clean();
            Thread.sleep(700);
        }
    }
}
