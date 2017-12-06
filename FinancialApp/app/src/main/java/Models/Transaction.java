package Models;

import java.util.Date;

/**
 * Created by Ludwig on 2017-09-20.
 */

public class Transaction {
    private int id;
    private String type;
    private Integer amount;
    private Integer date;

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

    public Integer getAmount(){
        return amount;
    }

    public void setAmount(Integer amount){
        this.amount = amount;
    }

    public Integer getDate(){
        return date;
    }

    public void setDate(Integer date){
        this.date = date;
    }

}
