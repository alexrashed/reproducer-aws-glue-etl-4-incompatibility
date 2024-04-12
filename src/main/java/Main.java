import com.amazonaws.services.glue.AWSGlue;
import com.amazonaws.services.glue.AWSGlueClientBuilder;
import com.amazonaws.services.glue.model.Column;
import com.amazonaws.services.glue.util.DataCatalogWrapper;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;

public class Main {
    public static void main(String[] args) {
        Column column = new Column();
        column.setName("Test");
        column.setType("boolean");
        List nil = Nil$.MODULE$;
        List cols = nil.$colon$colon(column);
        AWSGlue client = AWSGlueClientBuilder.standard().withRegion("us-east-1").build();
        DataCatalogWrapper wrapper = new DataCatalogWrapper(client);
        wrapper.getFieldsFromColumns(cols);
    }
}
