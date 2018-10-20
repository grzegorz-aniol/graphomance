package org.gangel.graphomance;

public interface SchemaApi {

  void createClass(final String clsName);

  void dropClass(final String clsName);

  void createProperty(final String clsName, final String propName, Class type, boolean isMandatory);

  void createIndex(final String indexName, final String clsName, final IndexType indexType, boolean isUnique, String... propNames);
}
