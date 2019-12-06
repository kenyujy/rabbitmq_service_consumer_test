package online_store;

import online_store.order_process_controller.OrderProcess;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit  //开启rabbitMQ Listener
@SpringBootApplication
public class OrderApplication implements CommandLineRunner {

    @Autowired
    OrderProcess orderProcess;

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        orderProcess.processOrder();
    }
}
