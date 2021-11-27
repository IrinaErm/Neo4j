import org.neo4j.driver.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class EventAddition {
    private String name;
    private String desc;
    private String parameters;

    private List<Parameter> parameterList;
    private static String prevName;

    private final Driver driver;

    public EventAddition () {
        driver = DBConnection.getConnection();
    }

    public String addEvent(String name, String desc, String params) {
        this.name = name.strip();
        this.desc = desc;
        this.parameters = params;
        parameterList = new ArrayList<>();

        return create();
    }

    /*public void newSeq() {
        prevName = null;
    }*/

    private String create() {
        //getParams();
        String query = "CREATE (e:Event \n" +
                "{title: $name,\n" +
                "description:$desc," +
                "parameters: $parameters})\n" +
                "\n";

        /*Integer i = 0;
        for(Parameter p : parameterList) {
            query += "CREATE (p" + i + ":Parameter \n" +
                    "{title: \"" + p.title +"\",\n" +
                    "type: \"" + p.type + "\",\n" +
                    "valueDesc: \""+ p.valueDesc +"\"})\n" +
                    "\n";
            query += "CREATE (e) - [:hasParameter] -> (p" + i + ")\n";
            ++i;
        }*/

        String screenName = name.substring(0,name.indexOf("_"));
        switch(screenName.toLowerCase(Locale.ROOT)) {
            case "mainscreen" :
                query += "CREATE (s:Screen:MainScreen \n" +
                        "{title:\"mainScreen\"})\n";
                break;
            case "authorscreen":
                query += "CREATE (s:Screen:AuthorScreen \n" +
                        "{title:\"authorScreen\"})\n";
                break;
            case "searchscreen":
                query += "CREATE (s:Screen:SearchScreen \n" +
                        "{title:\"searchScreen\"})\n";
                break;
            case "bookscreen":
                query += "CREATE (s:Screen:BookScreen \n" +
                        "{title:\"bookScreen\"})\n";
                break;

        }
        query += "CREATE (e) - [:happensOn] -> (s)\n";

        String actionName = name.substring(name.lastIndexOf("_"));
        if (actionName.indexOf("input") >= 0) {
            query += "CREATE (a:Action:Input\n" +
                    "{title:\"input\"})\n" +
                    "\n";
        }
        else if (actionName.indexOf("tap") >= 0) {
            query += "CREATE (a:Action:Tap\n" +
                    "{title:\"tap\"})\n" +
                    "\n";
        }
        else if (actionName.indexOf("view") >= 0) {
            query += "CREATE (a:Action:View\n" +
                    "{title:\"view\"})\n" +
                    "\n";
        }

        if(name.chars().filter(ch -> ch == '_').count() > 1) {
            String elName = name.substring(name.indexOf("_") + 1, name.lastIndexOf("_"));
            String elNameLower = elName.toLowerCase(Locale.ROOT);
            if (elNameLower.indexOf("block") >= 0) {
                query += "CREATE(el:Element:Block\n" +
                        "{title:\"" + elName + "\"})\n" +
                        "\n";
            }
            else if (elNameLower.indexOf("inputfield") >= 0) {
                query += "CREATE(el:Element:InputField\n" +
                        "{title:\"" + elName + "\"})\n" +
                        "\n";
            }
            else if (elNameLower.indexOf("listview") >= 0) {
                query += "CREATE(el:Element:ListView\n" +
                        "{title:\"" + elName + "\"})\n" +
                        "\n";
            }
            else if (elNameLower.indexOf("button") >= 0) {
                query += "CREATE(el:Element:Button\n" +
                        "{title:\"" + elName + "\"})\n" +
                        "\n";
            }

            query += "CREATE (s) - [:containsElement] -> (el) <- [:isPerformedOn] - (a) \n";
        }
        else {
            query += "CREATE (a) - [:isPerformedOn] -> (s)\n";
        }

        final String finalQuery = query;
        HashMap<String,Object> params = new HashMap <>();
        params.put("name", name);
        params.put("desc", desc);
        params.put("parameters", parameters);

        try (Session session = driver.session()) {
            String add = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result result = tx.run(finalQuery, params);
                    return result.toString();
                }
            });
            return "Data added successfully.";
            //return linkEvents();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    //user_authorized [bool] (состояние пользователя: true - user authorized, false - user unauthorized);
    //author_id [long] (id автора)
    /*private void getParams() {
        if(!params.strip().equals("-") && params != null) {
            String arr[] = this.params.split(";");
            for (String s : arr) {
                String name = s.strip().substring(0, s.indexOf("[") - 1);
                String type = s.substring(s.indexOf("[") + 1, s.indexOf("]"));
                String valDesc = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
                Parameter p = new Parameter(name, type, valDesc);
                parameterList.add(p);
            }
        }
    }*/

    public String linkEvents(String query) {
        //String query = "";
        //if(prevName != null) {
            /*query += "MATCH\n" +
                    "  (a:Event),\n" +
                    "  (b:Event)\n" +
                    "WHERE a.title = '" + prevName + "' AND b.title = '" + name + "'\n" +
                    "CREATE (a)-[r:isFollowedBy]->(b)\n";*/

            final String finalQuery = query;

            try (Session session = driver.session()) {
                String add = session.writeTransaction(new TransactionWork<String>() {
                    @Override
                    public String execute(Transaction tx) {
                        Result result = tx.run(finalQuery);
                        return result.toString();
                    }
                });
                return "Data added successfully";
            } catch (Exception e) {
                e.printStackTrace();
                return "Error";
            }
        //}
        //prevName = name;
    }

}
