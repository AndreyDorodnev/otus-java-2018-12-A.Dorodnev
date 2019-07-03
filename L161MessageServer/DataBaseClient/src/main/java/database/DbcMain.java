package database;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class DbcMain {
    public static void main(String[] args) throws IOException {

        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "SpringBeans.xml");
        DataBaseService dataBaseService = context.getBean("dataBaseService",DataBaseService.class);
        dataBaseService.init(Integer.valueOf(args[1]));
//        dataBaseService.createUsers();
        dataBaseService.start();
    }
}
