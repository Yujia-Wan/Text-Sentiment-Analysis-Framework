package edu.cmu.cs.cs214.hw6.framework.utils;
import java.util.Objects;

/**
 * This class defines the format of results from the sentimental analysis performed by our framework.
 * It basically has four fields:
 * the entity which represents the object on which people express their emotions;
 * the magnitude which indicates the overall strength of emotion (both positive and negative)
 * within the given text, between 0.0 and +inf;
 * the score which ranges between -1.0 (negative) and 1.0 (positive) and corresponds to the overall
 * emotional leaning of the text;
 * the salience which indicates the importance or relevance of this entity to the entire document text.
 * This score can assist information retrieval and summarization by prioritizing salient entities.
 * Scores closer to 0.0 are less important, while scores closer to 1.0 are highly important.
 */
public class ResultNode {
    private String entity;
    private float magnitude;
    private float score;

    private float salience;

    public ResultNode(String entity, float magnitude, float score, float salience) {
        this.entity = entity;
        this.magnitude = magnitude;
        this.score = score;
        this.salience = salience;
    }

    public String getEntity() {
        return entity;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public float getScore() {
        return score;
    }

    public float getSalience() {
        return salience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultNode that = (ResultNode) o;
        return Float.compare(that.magnitude, magnitude) == 0 && Float.compare(that.score, score) == 0 &&
                Float.compare(that.salience, salience) == 0 && Objects.equals(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, magnitude, score, salience);
    }

    @Override
    public String toString() {
        return "ResultNode{" +
                "entity='" + entity + '\'' +
                ", magnitude=" + magnitude +
                ", score=" + score +
                ", salience=" + salience +
                '}';
    }
}
