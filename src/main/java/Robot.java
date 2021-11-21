import org.neo4j.driver.*;
import org.neo4j.driver.internal.logging.JULogging;

import java.util.HashMap;
import java.util.logging.Level;

public class Robot implements AutoCloseable {
    private final Driver driver;

    public Robot(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password),
                Config.builder().withLogging(new JULogging(Level.WARNING)).build());
        driver.session(SessionConfig.forDatabase("Robot"));
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    public void addRobot(String name, String phone, String chargeType, String cleanType, String sensorType) {
        String query = "CREATE (RVacuum:Robot {title: $name })\n" +
                "CREATE (Smartphone:Smartphone {title: $phone })\n" +
                "CREATE (ChargingDevice:ChargingDevice {title:\"Charging Device\", type: $chargeType })\n" +
                "\n" +
                "CREATE (Cleaning:BasicFunction {title:\"Cleaning\", type: $cleanType }) \n" +
                "CREATE (LC:AdditionalFunction {title:\"LiquidsCollection\"})\n" +
                "CREATE (CTC:AdditionalFunction {title:\"CleaningTimeCalculation\"})\n" +
                "CREATE (RVacuum) - [:hasFunction] -> (Cleaning)\n" +
                "CREATE (RVacuum) - [:hasFunction] -> (LC)\n" +
                "CREATE (RVacuum) - [:hasFunction] -> (CTC)\n" +
                "\n" +
                "CREATE (Component:Component {title:\"Sensor\", type: $sensorType })\n" +
                "CREATE (RVacuum) - [:hasComponent] -> (Component)\n" +
                "\n" +
                "CREATE (RVacuum) - [:canBeControlledWith] -> (Smartphone)\n" +
                "\n" +
                "CREATE (RVacuum) - [:chargesWith] -> (ChargingDevice)";
        HashMap<String,Object> params = new HashMap <>();
        params.put("name", name);
        params.put("phone", phone);
        params.put("chargeType", chargeType);
        params.put("cleanType", cleanType);
        params.put("sensorType", sensorType);

        try (Session session = driver.session()) {
            String add = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result result = tx.run(query, params);
                    return result.toString();
                }
            });
            System.out.println("Data added successfully");
        }
    }
}
