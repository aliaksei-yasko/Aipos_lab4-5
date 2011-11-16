/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alex.clients;

import alex.classes.Weapon;

/**
 *
 * @author Admin
 * interface that represent verify operations with web-services
 */
public interface Performer {
    /**
     * Function that set service endpoint
     * @param endPoint string with endpoint
     */
   void setEndPoint(String endPoint);
   /**
     * Function that get service endpoint
     * @return endPoint string with endpoint
     */
   String getEndPoint();
   /**
     * Function that getting all information from the service
     * @return array of weapon objects
     * @throws Exception
     */
   Weapon[] getAllInformation() throws Exception;
   /**
     * Function that delete weapon from the service
     * @param weapon deleted weapon object
     * @return logical results of operation
     * @throws Exception
     */
   boolean deleteWeapon(Weapon weapon) throws Exception;
   /**
     * Function that add new weapon in service base
     * @param weapon added weapon object
     * @return logical results of operation
     * @throws Exception
     */
   boolean addNewWeapon(Weapon weapon) throws Exception;
   /**
     * Function that update records in service base
     * @param weapon updated weapon object
     * @return logical results of operation
     * @throws Exception
     */
   boolean updateWeapon(Weapon weapon) throws Exception;
}
