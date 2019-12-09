package com.example.snowtamair;

import java.io.Serializable;
import java.util.HashMap;

public class Runway implements Serializable {

    private String id;
    private String observationDate;
    private String clearedRunwayLength;
    private String clearedRunwayWidth;
    private String condition;
    private String thickness;
    private String frictionCoefficient;
    private String criticalDrift;
    private String obscuredLimelight;
    private String nextClearing;

    private String other;

    public Runway(String observationDate, String id, String clearedRunwayLength, String clearedRunwayWidth, String condition, String thickness, String frictionCoefficient, String criticalDrift, String obscuredLimelight, String nextClearing,String other) {
        this.observationDate = observationDate;
        this.id = id;
        this.clearedRunwayLength = clearedRunwayLength;
        this.clearedRunwayWidth = clearedRunwayWidth;
        this.condition = condition;
        this.thickness = thickness;
        this.frictionCoefficient = frictionCoefficient;
        this.criticalDrift = criticalDrift;
        this.obscuredLimelight = obscuredLimelight;
        this.nextClearing = nextClearing;
        this.other=other;
    }

    public Runway(HashMap<String,String> SnowtamInfo) {
        this.observationDate = SnowtamInfo.get("B)");
        this.id = SnowtamInfo.get("C)");
        this.clearedRunwayLength = SnowtamInfo.get("D)");
        this.clearedRunwayWidth = SnowtamInfo.get("E)");
        this.condition = decodeCondition(SnowtamInfo.get("F)"));
        this.thickness = SnowtamInfo.get("G)");
        this.frictionCoefficient = decodeFriction(SnowtamInfo.get("H)"));
        this.criticalDrift = SnowtamInfo.get("J)");
        this.obscuredLimelight = SnowtamInfo.get("K)");
        this.nextClearing = SnowtamInfo.get("L)");
        this.other=SnowtamInfo.get("T)");

    }

    public String decodeCondition(String coded){
        if(coded == null || coded.isEmpty()){
            return "NIL";
        }
        String condition ="";
        String[] parsedCondition=coded.split("/");
        for(String part:parsedCondition){
            if (part.equals("NIL")){
                String state=Condition.values()[0].name();
                condition += " "+state;
            }
            else{
                for(int value : part.toCharArray()){
                    String c = Character.toString ((char) value);
                    String state=Condition.values()[Integer.parseInt(c)].name();
                    condition += " "+state;
                }
            }
        }
        return condition.substring(1);
    }
    public String decodeFriction(String coded){
        if(coded == null || coded.isEmpty()){
            return "NIL";
        }
        String friction ="";
        String[] parsedCondition=coded.split("/");
        for(String part:parsedCondition){
            for(int value : part.toCharArray()){
                String c = Character.toString ((char) value);
                String state=Friction.values()[Integer.parseInt(c)-1].name();
                friction += " "+state;
            }
        }
        return friction.substring(1);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public String getThickness() {
        return thickness;
    }

    public String getFrictionCoefficient() {
        return frictionCoefficient;
    }

    public String getObservationDate() { return observationDate; }

    public void setObservationDate(String observationDate) {
        this.observationDate = observationDate;
    }

}