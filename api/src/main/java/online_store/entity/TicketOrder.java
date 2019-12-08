package online_store.entity;

import lombok.Data;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class TicketOrder implements Serializable {

    private static final AtomicInteger atomicInteger= new AtomicInteger(0);
    private String orderId;

    public TicketOrder(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String date= formatter.format(new Date()).toString();
        int i= atomicInteger.incrementAndGet();
        String s= date+ String.format("%010d", i);
        this.orderId= s;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
