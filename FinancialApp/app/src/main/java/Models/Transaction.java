package Models;

import java.util.Date;

/**
 * Created by Ludwig on 2017-09-20.
 */

public class Transaction {
    private int id;
    private String type;
    private String amount;
    private String date;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getAmount(){
        return amount;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

}
