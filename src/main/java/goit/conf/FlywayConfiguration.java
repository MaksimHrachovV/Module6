package goit.conf;

import org.flywaydb.core.Flyway;

public class FlywayConfiguration {
    public void dbStart(String connectUrl){
        // Create the Flyway instance and point it to the database
        Flyway flyway = Flyway
                .configure()
                .dataSource(connectUrl, null, null)
                .load();

        // Start the migration
        flyway.migrate();

    }


}
