## Feedback
This framework is well implemented since we could easily add more plugins without understanding the implementation
internals of the framework. The framework is not coupled to any specific plugin implementations. In addition, frontend is
easily updated when new plugins are provided.




However, when we use data plugins to retrieve some information, sometimes the visualization part shows the chart with empty result.
For example, The New York Times Article Search API
returns a max of 10 results at a time, which is a relative small dataset. In GoogleAPI.java, there is a mechanism that
filters out entity whose magnitude is less than 0.5. So when we try with New York Times data source, it often returns null
entity and thus gets an empty chart. This problem isn't mentioned in the README.md, thus it confused us for a while when 
our team implemented new plugins.