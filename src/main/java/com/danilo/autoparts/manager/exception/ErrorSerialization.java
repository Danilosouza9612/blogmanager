package com.danilo.autoparts.manager.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ErrorSerialization {
    private final Map<String, List<String>> listMap;

    public ErrorSerialization(){
        this.listMap = new HashMap<>();
    }

    public void addError(String key, String error){
        List<String> errorMessages;
        if(listMap.containsKey(key)){
            listMap.get(key).add(error);
        }else{
            errorMessages = new ArrayList<>();
            errorMessages.add(error);
            listMap.put(key, errorMessages);
        }
    }
}
