import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XMLHandler extends DefaultHandler {

    private Voter voter;
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        try {
            String birthDayStr = "";
            String name = "";

            if (qName.equals("voter")) {

                birthDayStr = attributes.getValue("birthDay");
                name = attributes.getValue("name");
                Date birthDay = birthDayFormat.parse(birthDayStr);
                voter = new Voter(name, birthDay);

            }
            else if (qName.equals("visit") && voter != null){

                SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
                birthDayStr = dayFormat.format(voter.getBirthDay()).replace('.', '-');

                int lengthQuery = DBConnection.insertQuery.length();

//                if(voter.getName().equals("Белопухов Аникей")){
//                    System.out.println("Белопухов Аникей");//Черевиков Рауль
//                }

                DBConnection.insertQuery.append((lengthQuery == 0 ? "" : ",") +
                        "('" + voter.getName() + "', '" + birthDayStr + "', 1)");
                if (lengthQuery > 2_200_000) {
                    DBConnection.executeMultiInsert();
                    lengthQuery = DBConnection.insertQuery.length();
                    DBConnection.insertQuery.delete(0,lengthQuery);
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {

        if (qName.equals("voter")) {
            voter = null;
        }
    }

}
