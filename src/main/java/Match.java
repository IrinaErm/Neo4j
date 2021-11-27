import org.neo4j.driver.*;
import java.util.List;

public class Match {
    private final Driver driver;

    public Match() {
        driver = DBConnection.getConnection();
    }

    public String screenStat(String name) {
        String finalQuery = "MATCH (ev)-[:happensOn]->(s:" + name + ")\n" +
                "RETURN  ev.title, ev.description, ev.parameters";
        /*String finalQuery2 = "MATCH (ev)-[:happensOn]->(s:" + name + ") WHERE NOT (ev)-[:hasParameter]->(:Parameter)\n" +
                "RETURN  ev.title, ev.description";*/

        try ( Session session = driver.session() )
        {
            return session.readTransaction( tx -> {
                String events = "";
                Result result = tx.run( finalQuery );
                while ( result.hasNext() )
                {
                    List<Value> vals = result.next().values();
                    events += vals.get(0).toString().replace("\"", "") + "\n";
                    events += vals.get(1).toString().replace("\"", "") + "\n";
                    events += vals.get(2).toString().replace("\"", "") + "\n";
                    /*for(Object n : vals.get(2).asList()) {
                        events += ((Node) n).get("title").toString().replace("\"", "");
                        events += " [" + ((Node) n).get("type").toString().replace("\"", "");
                        events += "] (" + ((Node) n).get("valueDesc").toString().replace("\"", "") + "); ";
                    }*/
                    events += "\n";
                }

                /*Result result2 = tx.run( finalQuery2 );
                while ( result2.hasNext() )
                {
                    List<Value> vals = result2.next().values();
                    events += vals.get(0).toString().replace("\"", "") + "\n";
                    events += vals.get(1).toString().replace("\"", "") + "\n";
                    events += "\n\n";
                }*/
                return events;
            } );
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public String elementStat(String name) {
        if(name.indexOf("Screen") >= 0) {
            return screenStat(name);
        }
        String finalQuery = "MATCH (ev)-[:happensOn]->(s)-[:containsElement]->(el:" + name + ")\n" +
                "RETURN  ev.title, ev.description, ev.parameters";
        /*String finalQuery2 = "MATCH (ev)-[:happensOn]->(s)-[:containsElement]->(el:" + name + ")\n " +
                "WHERE NOT (ev)-[:hasParameter]->(:Parameter)\n" +
                "RETURN  ev.title, ev.description";*/

        try ( Session session = driver.session() )
        {
            return session.readTransaction( tx -> {
                String events = "";
                Result result = tx.run( finalQuery );
                while ( result.hasNext() )
                {
                    List<Value> vals = result.next().values();
                    events += vals.get(0).toString().replace("\"", "") + "\n";
                    events += vals.get(1).toString().replace("\"", "") + "\n";
                    events += vals.get(2).toString().replace("\"", "") + "\n";
                    /*for(Object n : vals.get(2).asList()) {
                        events += ((Node) n).get("title").toString().replace("\"", "");
                        events += " [" + ((Node) n).get("type").toString().replace("\"", "");
                        events += "] (" + ((Node) n).get("valueDesc").toString().replace("\"", "") + "); ";
                    }*/
                    events += "\n";
                }

                /*Result result2 = tx.run( finalQuery2 );
                while ( result2.hasNext() )
                {
                    List<Value> vals = result2.next().values();
                    events += vals.get(0).toString().replace("\"", "") + "\n";
                    events += vals.get(1).toString().replace("\"", "") + "\n";
                    events += "\n\n";
                }*/
                return events;
            } );
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public String getSeq(String s) {
        String res = "";
        String finalQuery = "";
        if (s.equals("В")) {
            for (Integer i = 1; i < 12; i++) {
                res += "Сценарий " + i + ": \n";
            /*String finalQuery = "MATCH (p) <- [:hasParameter] - (ev)- [r:isFollowedBy]->(ev2)\n" +
                    "WHERE r.lvl CONTAINS \""+ 1 +"\" AND (NOT (ev2) - [:hasParameter] -> (:Parameter) OR (ev2) - [:hasParameter] -> (:Parameter))\n" +
                    "RETURN  ev, p, ev2";*/
                String c = i.toString();
                if (i == 10) {
                    c = "A";
                }
                if (i == 11) {
                    c = "B";
                }
                finalQuery = "MATCH (ev)- [r:isFollowedBy]->(ev2)\n" +
                        "WHERE r.lvl CONTAINS \"" + c + "\"\n" +
                        "return ev, collect(r)";
                res += getSeq1(finalQuery, i);
                res += "\n\n";
            }
        }
        else {
            if (Integer.parseInt(s.strip()) == 10) {
                finalQuery = "MATCH (ev)- [r:isFollowedBy]->(ev2)\n" +
                        "WHERE r.lvl CONTAINS \"" + "A" + "\"\n" +
                        "return ev, collect(r)";
            }
            else if (Integer.parseInt(s.strip()) == 11) {
                finalQuery = "MATCH (ev)- [r:isFollowedBy]->(ev2)\n" +
                        "WHERE r.lvl CONTAINS \"" + "B" + "\"\n" +
                        "return ev, collect(r)";
            }
            else {
                finalQuery = "MATCH (ev)- [r:isFollowedBy]->(ev2)\n" +
                        "WHERE r.lvl CONTAINS \"" + s.strip() + "\"\n" +
                        "return ev, collect(r)";
            }
            res += getSeq1(finalQuery, Integer.parseInt(s.strip()));
        }
        return res;
    }

    private String getSeq1(String finalQuery, Integer c) {
        try ( Session session = driver.session() )
        {
            return session.readTransaction( tx -> {
                String events = "";
                Result result = tx.run( finalQuery );
                int i = 0;
                String temp = "";
                while ( result.hasNext() )
                {
                    i++;
                    List<Value> vals = result.next().values();
                    if(i == 2 && c != 1 & c != 2) {
                        temp += vals.get(0).get("title").toString().replace("\"", "") + "\n";
                        temp += vals.get(0).get("description").toString().replace("\"", "") + "\n";
                        temp += vals.get(0).get("parameters").toString().replace("\"", "") + "\n";
                        temp += "\n\n";
                        continue;
                    }
                    /*events += vals.get(0).toString().replace("\"", "") + "\n";
                    events += vals.get(1).toString().replace("\"", "") + "\n";
                    for(Object n : vals.get(2).asList()) {
                        events += ((Node) n).get("title").toString().replace("\"", "");
                        events += " [" + ((Node) n).get("type").toString().replace("\"", "");
                        events += "] (" + ((Node) n).get("valueDesc").toString().replace("\"", "") + "); ";
                    }*/
                    events += vals.get(0).get("title").toString().replace("\"", "") + "\n";
                    events += vals.get(0).get("description").toString().replace("\"", "") + "\n";
                    events += vals.get(0).get("parameters").toString().replace("\"", "") + "\n";
                    events += "\n\n";
                }
                return events + temp + "\n";
            } );
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

}
