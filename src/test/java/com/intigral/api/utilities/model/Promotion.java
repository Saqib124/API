package com.intigral.api.utilities.model;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class Promotion {
    String promotionId;
    int orderId;
    List<String> promoArea;
    String promoType;
    Boolean showPrice;
    Boolean showText;
    HashMap<String,List<String>> localizedTexts;
    List<HashMap<String,String>> images;

    public Promotion(JSONObject object){
    }

}
