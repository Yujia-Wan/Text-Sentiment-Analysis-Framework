package edu.cmu.cs.cs214.hw6.framework.core;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface DataFramework {
    /**
     * Extracts data related to {@code query} (a Twitter user ID, for example) from a data plugin
     * @param query search query provided to the selected data plugin
     * See implementation details in {@link edu.cmu.cs.cs214.hw6.framework.core.DataFrameworkImpl}
     */
    void extractData(String query) throws GeneralSecurityException, IOException;

    /**
     * Analyzes the entities and sentiments of the extracted data.
     * See implementation details in {@link edu.cmu.cs.cs214.hw6.framework.core.DataFrameworkImpl}
     */
    void analyzeEntitySentiment();

    /**
     * Visualizes the results of the sentimental analysis
     * See implementation details in {@link edu.cmu.cs.cs214.hw6.framework.core.DataFrameworkImpl}
     */
    void visualize();
}
