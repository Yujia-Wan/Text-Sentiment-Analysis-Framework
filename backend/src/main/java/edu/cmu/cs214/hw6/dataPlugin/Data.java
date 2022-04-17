package edu.cmu.cs214.hw6.dataPlugin;

public class Data {
    private String text;
    private String time;
    private float score;

    public Data(String text, String time, float score) {
        this.text = text;
        this.time = time;
        this.score = score;
    }

    public String getText() {
        return this.text;
    }

    public String getTime() {
        return this.time;
    }

    public float getScore() {
        return this.score;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "text: " + this.text + "; time: " + this.time + "; score: " + this.score;
    }
}
