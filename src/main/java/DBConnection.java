import org.neo4j.driver.*;
import org.neo4j.driver.internal.logging.JULogging;

import java.util.logging.Level;

public class DBConnection {
    private static Driver driver;

    public static Driver getConnection() {
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "root"),
                Config.builder().withLogging(new JULogging(Level.WARNING)).build());
        driver.session(SessionConfig.forDatabase("EventsDBMS"));
        return driver;
    }

    public void close() throws Exception {
        driver.close();
    }
}
