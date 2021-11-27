import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadFile {
    public static EventAddition event = new EventAddition();

    //searchScreen_bookBlock_tap
    //Отправляется, когда пользователь выбирает книгу.
    //book_id [long] (id просматриваемой книги)
    public static String readFile() throws IOException {
        String fromFile = "";
        BufferedReader br = new BufferedReader(new FileReader("events.txt"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            fromFile = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            br.close();
        }

        String arr[] = fromFile.split("!\r\n");
        for(String s : arr) {
            String arr2[] = s.split("\r\n");
            String name = arr2[0];
            String desc = arr2[1];
            String params = arr2[2];
            event.addEvent(name, desc, params);
        }

        return "Data added successfully.";
    }

    public static String readLinks() throws IOException {
        String fromFile = "";
        BufferedReader br = new BufferedReader(new FileReader("relationships.txt"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            fromFile = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            br.close();
        }

        String arr[] = fromFile.split("!\r\n");
        for(String s : arr) {
            event.linkEvents(s);
        }

        return "Data added successfully.";
    }
}
