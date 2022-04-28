package edu.cmu.cs.cs214.hw6.framework.utils;
import java.util.Objects;

/**
 * This class defines the format of data produced by data plugins. It basically has two fields, the one
 * is the entity which represents the object on which people express their emotions. And the other is the
 * text which contains the corresponding text information being analyzed.
 */
public class DataNode {
    private String entity;
    private String text;

    public DataNode(String entity, String text) {
        this.entity = entity;
        this.text = text;
    }

    public String getEntity() {
        return entity;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataNode dataNode = (DataNode) o;
        return Objects.equals(entity, dataNode.entity) && Objects.equals(text, dataNode.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, text);
    }

    @Override
    public String toString() {
        return "DataNode{" +
                "entity='" + entity + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
