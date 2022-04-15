package edu.cmu.cs214.hw6.dataPlugin;

public class Data {
    private String commentText;
    private String time;
    private double sentimentResult;

    public Data(String commentText, String time, double sentimentResult) {
        this.commentText = commentText;
        this.time = time;
        this.sentimentResult = sentimentResult;
    }

    public String getCommentText() {
        return this.commentText;
    }

    public String getTime() {
        return this.time;
    }

    public double getSentimentResult() {
        return this.sentimentResult;
    }

    public void setCommentText(String text) {
        this.commentText = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSentimentResult(double sentimentResult) {
        this.sentimentResult = sentimentResult;
    }

    @Override
    public String toString() {
        return "comment text : " + this.commentText + "; time : " + this.time + " ; sentiment result : " + this.sentimentResult;
    }
}
