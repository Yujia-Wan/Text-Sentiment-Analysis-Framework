package edu.cmu.cs.cs214.hw6.framework.core;

import edu.cmu.cs.cs214.hw6.framework.utils.DataNode;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface DataPlugin {

    /**
     * Connects to or gets authenticated by the data source (e.g., YouTube data API)
     */
    void connectToSource() throws GeneralSecurityException, IOException;

    /**
     * Requests data (should include the texts you want to analyze) associated with the given query from the data source
     * we are connected to and saves the data in an instance variable, which will be used in {@link #processData()} later
     *
     * @param query search query
     */
    void extractData(String query) throws IOException, GeneralSecurityException;

    /**
     * Processes the data received from the data source by converting it into a list of {@code DataNode}, each
     * representing an entity-text pair. Please see {@link edu.cmu.cs.cs214.hw6.framework.utils.DataNode} for details of
     * its data structure.
     *
     * In summary, {@code DataNode} has two string fields: entity and text.
     * Read about what an entity is at https://cloud.google.com/natural-language/docs/analyzing-entities.
     *
     * To set {@code entity}, you have two options.
     * One is that you set it to something you think is the entity of the text. For example, if you think a text is
     * about Farnam, set {@code entity} to "Farnam".
     * The other option is that you let the framework figure out the entity later. In this case, set {@code entity} to null.
     *
     * @return a list of {@link edu.cmu.cs.cs214.hw6.framework.utils.DataNode}, which contain all the extracted data
     */
    List<DataNode> processData();

    @Override
    String toString();
}


















