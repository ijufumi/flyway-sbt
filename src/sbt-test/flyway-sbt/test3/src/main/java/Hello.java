import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context

public class Hello extends BaseJavaMigration {
	public void migrate(context: Context) {
		throw new Exception("");
	}
}
