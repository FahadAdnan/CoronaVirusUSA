package com.example.coronavirususa;

public class StateObj {
    private String state;
    private int positive;
    private String grade;
    private int score;
    private int negative;
    private int hospitalizedCurrently;
    private int hospitalizedCumulative;
    private int inIcuCurrently;
    private int inIcuCumulative;
    private int onVentilatorCurrently;
    private int onVentilatorCumulative;
    private int recovered;
    private String lastUpdateEt;
    private int death;
    private int hospitalized;
    private int totalTestResults;
    private int posNeg;
    private String dateModified;
    private boolean isExpanded;

    public StateObj(String state, int positive, String grade, int score, int negative, int hospitalizedCurrently, int hospitalizedCumulative,
                    int inIcuCurrently, int inIcuCumulative, int onVentilatorCurrently, int onVentilatorCumulative, int recovered, String lastUpdateEt,
                    int death, int hospitalized, int totalTestResults, int posNeg, String dateModified){
        this.state = state;
        this.positive = positive;
        this.grade=grade;
        this.score=score;
        this.negative=negative;
        this.hospitalizedCurrently = hospitalizedCurrently;
        this.hospitalizedCumulative = hospitalizedCumulative;
        this.inIcuCurrently = inIcuCurrently;
        this.inIcuCumulative = inIcuCumulative;
        this.onVentilatorCurrently = onVentilatorCurrently;
        this.onVentilatorCumulative = onVentilatorCumulative;
        this.recovered=recovered;
        this.lastUpdateEt = lastUpdateEt;
        this.death = death;
        this.hospitalized = hospitalized;
        this.totalTestResults = totalTestResults;
        this.posNeg = posNeg;
        this.dateModified = dateModified;
        this.isExpanded = false;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNegative() {
        return negative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public int getHospitalizedCurrently() {
        return hospitalizedCurrently;
    }

    public void setHospitalizedCurrently(int hospitalizedCurrently) {
        this.hospitalizedCurrently = hospitalizedCurrently;
    }

    public int getHospitalizedCumulative() {
        return hospitalizedCumulative;
    }

    public void setHospitalizedCumulative(int hospitalizedCumulative) {
        this.hospitalizedCumulative = hospitalizedCumulative;
    }

    public int getInIcuCurrently() {
        return inIcuCurrently;
    }

    public void setInIcuCurrently(int inIcuCurrently) {
        this.inIcuCurrently = inIcuCurrently;
    }

    public int getInIcuCumulative() {
        return inIcuCumulative;
    }

    public void setInIcuCumulative(int inIcuCumulative) {
        this.inIcuCumulative = inIcuCumulative;
    }

    public int getOnVentilatorCurrently() {
        return onVentilatorCurrently;
    }

    public void setOnVentilatorCurrently(int onVentilatorCurrently) {
        this.onVentilatorCurrently = onVentilatorCurrently;
    }

    public int getOnVentilatorCumulative() {
        return onVentilatorCumulative;
    }

    public void setOnVentilatorCumulative(int onVentilatorCumulative) {
        this.onVentilatorCumulative = onVentilatorCumulative;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public String getLastUpdateEt() {
        return lastUpdateEt;
    }

    public void setLastUpdateEt(String lastUpdateEt) {
        this.lastUpdateEt = lastUpdateEt;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getHospitalized() {
        return hospitalized;
    }

    public void setHospitalized(int hospitalized) {
        this.hospitalized = hospitalized;
    }

    public int getTotalTestResults() {
        return totalTestResults;
    }

    public void setTotalTestResults(int totalTestResults) {
        this.totalTestResults = totalTestResults;
    }

    public int getPosNeg() {
        return posNeg;
    }

    public void setPosNeg(int posNeg) {
        this.posNeg = posNeg;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
    public boolean getisExpanded() {
        return isExpanded;
    }

    public void setisExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }

}

