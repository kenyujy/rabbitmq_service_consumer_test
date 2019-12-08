package online_store.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderResult implements Serializable {
    private TicketOrder ticketOrder;
    private String result;
    private int resultCode;

    public OrderResult() { }

    public OrderResult(TicketOrder ticketOrder, String result, int resultCode) {
        this.ticketOrder = ticketOrder;
        this.result = result;
        this.resultCode = resultCode;
    }

    public TicketOrder getTicketOrder() {
        return ticketOrder;
    }

    public void setTicketOrder(TicketOrder ticketOrder) {
        this.ticketOrder = ticketOrder;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
