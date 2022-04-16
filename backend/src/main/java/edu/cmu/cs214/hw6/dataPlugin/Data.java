package edu.cmu.cs214.hw6.dataPlugin;

public class Data {
    private String text;
    private String time;
    private double sentimentResult;

    public Data(String text, String time, double sentimentResult) {
        this.text = text;
        this.time = time;
        this.sentimentResult = sentimentResult;
    }

    public String getText() {
        return this.text;
    }

    public String getTime() {
        return this.time;
    }

    public double getSentimentResult() {
        return this.sentimentResult;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSentimentResult(double sentimentResult) {
        this.sentimentResult = sentimentResult;
    }

    @Override
    public String toString() {
        return "comment text : " + this.text + "; time : " + this.time + " ; sentiment result : " + this.sentimentResult;
    }
}
