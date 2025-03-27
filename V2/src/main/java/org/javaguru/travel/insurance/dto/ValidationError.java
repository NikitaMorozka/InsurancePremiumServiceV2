package org.javaguru.travel.insurance.dto;

public record ValidationError(String errorCode, String description){
    public ValidationError(){
        this("...", "...");
    }
}
