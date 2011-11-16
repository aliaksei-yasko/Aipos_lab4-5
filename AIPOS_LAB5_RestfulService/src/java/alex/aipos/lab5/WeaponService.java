
package alex.aipos.lab5;

import alex.classes.Weapon;
import com.thoughtworks.xstream.XStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


/**
 * Class which represent restful web-service
 * for manipulation with weapons directory
 * @author Alexei Yasko
 */
@Path("/weapons")
public class WeaponService {
    @Context
    private UriInfo context;
    private Connection connection;

    public WeaponService(){
        /* Nessasary atribute fo connecting to data base*/
        String url = "jdbc:derby://localhost:1527/WeaponsDataBase";
        String userName = "alex";
        String password = "alex";
        try {
            /* Getting connection to data base */
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException ex) {
            Logger.getLogger(WeaponService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /**
    * Web-service method for getting all
    * weapon objects from weapons database
    * @return Xml string of weapon object
    */
    @GET
    @Produces("application/xml")
    public String viewAllWeapons() {
        try {
            /* Executing query to our data base */
            Statement statment = connection.createStatement();
            ResultSet resultLen = statment.executeQuery("SELECT * FROM ALEX.WEAPONS");

            /* Calculate how mach record we have */
            int length = 0;
            while(resultLen.next()) {
                length++;
            }

            Weapon[] weapons = new Weapon[length];
            length = 0;

            ResultSet result = statment.executeQuery("SELECT * FROM ALEX.WEAPONS");
            /* Translate obtained record to weapon objects */
            while(result.next()){
                Weapon weapon = new Weapon();
                weapon.setType(result.getString("TypeWeapon"));
                weapon.setName(result.getString("NameWeapon"));
                weapon.setWeight(result.getDouble("WeightWeapon"));
                weapon.setLength(result.getDouble("LengthWeapon"));
                weapon.setCaliber(result.getDouble("CaliberWeapon"));
                weapon.setSpeadOfTheBullet(result.getDouble("SpeadWeapon"));
                weapons[length] = weapon;
                length++;
            }

            /* Serilaze in xml */
            XStream xstream = new XStream();
            xstream.alias("weaponclasses",  Weapon[].class);
            String  xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xstream.toXML(weapons);
            return xml;

        } catch (SQLException ex) {
            Logger.getLogger(WeaponService.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    /**
     * Function that delete weapon from data base
     * @param weaponName string that represented weapon name
     * @return http response
     */
    @DELETE @Path("/{weaponName}")
    public Response deleteWeapon(@PathParam("weaponName") String weaponName){
       
        try {
            String deleteQuery =
                    "DELETE FROM ALEX.WEAPONS WHERE WEAPONS.NAMEWEAPON=?";
            /* Executing query to our data base */
            PreparedStatement statment = connection.prepareStatement(deleteQuery);
            statment.setString(1, weaponName);
            int result = statment.executeUpdate();
            if (result != 0) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(WeaponService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Function that added new weapon to data base
     * @param xmlWeapon xml string that represent added weapon object
     * @return http response
     */
    @POST
    @Consumes("application/xml")
    public Response addNewWeapon(String xmlWeapon){
        
        try {
            String insertQuery =
                    "INSERT INTO ALEX.WEAPONS VALUES(?, ?, ?, ?, ?, ?)";

            /* Create object from xml */
            XStream xstream = new XStream();
            xstream.alias("weaponclass",  Weapon.class);
            Weapon newWeapon = (Weapon) xstream.fromXML(xmlWeapon);
            /* Executing query to our data base */
            PreparedStatement statment = connection.prepareStatement(insertQuery);

            statment.setString(1, newWeapon.getType());
            statment.setString(2, newWeapon.getName());
            statment.setDouble(3, newWeapon.getWeight());
            statment.setDouble(4, newWeapon.getLength());
            statment.setDouble(5, newWeapon.getCaliber());
            statment.setDouble(6, newWeapon.getSpeadOfTheBullet());

            int result = statment.executeUpdate();

            if (result != 0) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(WeaponService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Function that update record in data base
     * @param xmlWeapon xml string that represent updated weapon object
     * @return http response
     */
    @PUT
    @Consumes("application/xml")
    public Response updateWeapon(String xmlWeapon){
        
        try {
            String insertQuery =
                    "UPDATE ALEX.WEAPONS SET  TYPEWEAPON=?, WEIGHTWEAPON=?, "
                    + "LENGTHWEAPON=?, CALIBERWEAPON=?, SPEADWEAPON=? WHERE  NAMEWEAPON=?";

            /* Create object from xml */
            XStream xstream = new XStream();
            xstream.alias("weaponclass",  Weapon.class);
            Weapon updateWeapon = (Weapon) xstream.fromXML(xmlWeapon);
            /* Executing query to our data base */
            PreparedStatement statment = connection.prepareStatement(insertQuery);

            statment.setString(1, updateWeapon.getType());
            statment.setDouble(2, updateWeapon.getWeight());
            statment.setDouble(3, updateWeapon.getLength());
            statment.setDouble(4, updateWeapon.getCaliber());
            statment.setDouble(5, updateWeapon.getSpeadOfTheBullet());
            statment.setString(6, updateWeapon.getName());

            int result = statment.executeUpdate();

            if (result != 0) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(WeaponService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
