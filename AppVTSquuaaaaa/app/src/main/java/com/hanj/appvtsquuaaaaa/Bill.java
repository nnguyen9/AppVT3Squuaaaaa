package com.hanj.appvtsquuaaaaa;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jinwoo on 2/28/2016.
 */
public class Bill {
    private String description;
    private double price;
    private String part_phone;

    public Bill() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPart_phone() {
        return part_phone;
    }

    public void setPart_phone(String part_phone) {
        this.part_phone = part_phone;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("description", description);
            obj.put("price", price);
            obj.put("part_phone", part_phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
