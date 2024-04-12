## Issue Reproducer Glue JAR Incompatibility
Public Issue created: https://github.com/awslabs/aws-glue-libs/issues/207
This reproducer aims at showcasing and explaining the issue reported in the issue.

### How to run this reproducer
```bash
mvn compile
mvn exec:java -Dexec.mainClass="Main" -Dexec.args="--JOB-NAME jobName"
```
This results in the following error (excerpt):
```
java.lang.Exception: The specified mainClass doesn't contain a main method with appropriate signature.
    at org.codehaus.mojo.exec.ExecJavaMojo.lambda$execute$0 (ExecJavaMojo.java:301)
    at java.lang.Thread.run (Thread.java:829)
Caused by: java.lang.NoSuchMethodError: 'void com.amazonaws.services.glue.schema.types.Field.<init>(java.lang.String, com.amazonaws.services.glue.schema.types.DataType, com.amazonaws.services.glue.schema.SchemaProperties, java.lang.String)'
    at com.amazonaws.services.glue.util.DataCatalogWrapperUtils.$anonfun$getFieldsFromColumns$1 (DataCatalogWrapper.scala:536)
    at scala.collection.immutable.List.map (List.scala:282)
    at com.amazonaws.services.glue.util.DataCatalogWrapperUtils.getFieldsFromColumns (DataCatalogWrapper.scala:535)
    at com.amazonaws.services.glue.util.DataCatalogWrapperUtils.getFieldsFromColumns$ (DataCatalogWrapper.scala:535)
    at com.amazonaws.services.glue.util.DataCatalogWrapper.getFieldsFromColumns (DataCatalogWrapper.scala:166)
    at Main.main (Main.java:17)
    at org.codehaus.mojo.exec.ExecJavaMojo.doMain (ExecJavaMojo.java:385)
    at org.codehaus.mojo.exec.ExecJavaMojo.doExec (ExecJavaMojo.java:374)
    at org.codehaus.mojo.exec.ExecJavaMojo.lambda$execute$0 (ExecJavaMojo.java:296
```

### Context
- This issue showcases an incompatibility of the latest version of the latest versions of the following libraries:
  - `com.amazonaws.AWSGlueETL-4.0.0`
    - Last edit time: (at the time of writing): 2024-03-21T23:42:55.000Z
      - As seen in the maven repo metadata: https://aws-glue-etl-artifacts.s3.amazonaws.com/
    - Direct link to the JAR file in the maven repo: https://aws-glue-etl-artifacts.s3.amazonaws.com/release/com/amazonaws/AWSGlueETL/4.0.0/AWSGlueETL-4.0.0.jar
    - POM file: https://aws-glue-etl-artifacts.s3.amazonaws.com/release/com/amazonaws/AWSGlueETL/4.0.0/AWSGlueETL-4.0.0.pom
      - States a dependency on `com.amazonaws.AWSGlueDynamicSchema-0.9.0`
  - `com.amazonaws.AWSGlueDynamicSchema-0.9.0`
    - Last edit time (at the time of writing): 2024-03-28T15:34:03.000Z
      - As seen in the maven repo metadata: https://aws-glue-etl-artifacts.s3.amazonaws.com/
    - Direct link to the JAR file in the maven repo: https://aws-glue-etl-artifacts.s3.amazonaws.com/release/com/amazonaws/AWSGlueDynamicSchema/0.9.0/AWSGlueDynamicSchema-0.9.0.jar

### Hypothesis
- A new “version” of `AWSGlueDynamicSchema-0.9.0.jar` introducing a new constructor for the `Field` class with an additional parameter on a constructor of a class has been released sometime around the beginning of March 2024.
- Later a new “version” of `AWSGlueETL-4.0.0.jar` which uses this constructor has been released.
- Afterwards, another new “version” of `AWSGlueDynamicSchema-0.9.0.jar` removing the constructor again has been published.
- `AWSGlueETL-4.0.0.jar` has not been updated anymore.

### Request / Solution to fix this issue
Please update `AWSGlueETL-4.0.0.jar` in the official Maven repository (https://aws-glue-etl-artifacts.s3.amazonaws.com/) such that it is compatible again.