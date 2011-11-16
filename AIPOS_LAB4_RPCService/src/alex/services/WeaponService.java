package alex.services;

import alex.classes.Weapon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class which represent rpc web-service
 * for manipulation with weapons directory
 * @author Alexei Yasko
 */
public class WeaponService {
    private Connection connection;
    public WeaponService(){
        try {
            /* Nessasary atribute fo connecting to data base*/
            String url = "jdbc:derby://localhost:1527/WeaponsDataBase";
            String userName = "alex";
            String password = "alex";
            /* Getting connection to data base */
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException ex) {
            Logger.getLogger(WeaponService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
    * Web-service method for getting all
    * weapon objects from weapons directory
    * @return array of weapon object
    */
    public Weapon[] viewAllWeapons() {
        

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
            return weapons;

        } catch (SQLException ex) {
            Logger.getLogger(WeaponService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Function that delete weapon from data base
     * @param deletedWeapon
     * @return logical results of operation
     */
    public boolean deleteWeapon(Weapon deletedWeapon){
        
        try {
            String deleteQuery =
                "DELETE FROM ALEX.WEAPONS WHERE WEAPONS.NAMEWEAPON=?";

            /* Executing query to our data base */
            PreparedStatement statment = connection.prepareStatement(deleteQuery);
            statment.setString(1, deletedWeapon.getName());
            int result = statment.executeUpdate();
            if (result == 1)
                return true;
            else
                return false;

        } catch (SQLException ex) {
            Logger.getLogger(WeaponService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Function that added new weapon to data base
     * @param newWeapon added weapon object
     * @return logical results of operation
     */
    public boolean addNewWeapon(Weapon newWeapon){
        try {
            String insertQuery =
                "INSERT INTO ALEX.WEAPONS VALUES(?, ?, ?, ?, ?, ?)";

            /* Executing query to our data base */
            PreparedStatement statment = connection.prepareStatement(insertQuery);

            statment.setString(1, newWeapon.getType());
            statment.setString(2, newWeapon.getName());
            statment.setDouble(3, newWeapon.getWeight());
            statment.setDouble(4, newWeapon.getLength());
            statment.setDouble(5, newWeapon.getCaliber());
            statment.setDouble(6, newWeapon.getSpeadOfTheBullet());

            int result = statment.executeUpdate();
            if (result == 1)
                return true;
            else
                return false;

        } catch (SQLException ex) {
            Logger.getLogger(WeaponService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Function that update record in data base
     * @param updateWeapon updated weapon object
     * @return logical results of operation
     */
    public boolean updateWeapon(Weapon updateWeapon){
        try {
            String insertQuery =
                "UPDATE ALEX.WEAPONS SET  TYPEWEAPON=?, WEIGHTWEAPON=?, "
                + "LENGTHWEAPON=?, CALIBERWEAPON=?, SPEADWEAPON=? WHERE  NAMEWEAPON=?";

            /* Executing query to our data base */
            PreparedStatement statment = connection.prepareStatement(insertQuery);

            statment.setString(1, updateWeapon.getType());
            statment.setDouble(2, updateWeapon.getWeight());
            statment.setDouble(3, updateWeapon.getLength());
            statment.setDouble(4, updateWeapon.getCaliber());
            statment.setDouble(5, updateWeapon.getSpeadOfTheBullet());
            statment.setString(6, updateWeapon.getName());
            
            int result = statment.executeUpdate();
            if (result == 1)
                return true;
            else
                return false;

        } catch (SQLException ex) {
            Logger.getLogger(WeaponService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}

