package goit;

import goit.conf.FlywayConfiguration;

public class App {
    public static void main(String[] args) {
        final String connectUrl = "jdbc:h2:./start;DB_CLOSE_DELAY=-1";
        new FlywayConfiguration().dbStart(connectUrl);
    }
}
