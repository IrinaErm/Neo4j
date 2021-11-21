
public class Main {
    public static void main( String... args ) throws Exception
    {
        String name = "Robot-4";
        String phone = "Huawei";
        String chargeType = "Automatic";
        String cleanType = "Wet";
        String sensorType = "Ultrasound";

        try ( Robot robot = new Robot( "bolt://localhost:7687", "neo4j", "root" ) )
        {
            robot.addRobot(name, phone, chargeType, cleanType, sensorType);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
