/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alex.clients;

import alex.classes.Weapon;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.thoughtworks.xstream.XStream;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Alexei Yasko
 * Class that perform all operations with restful service
 */
public class RestfulPerformer implements Performer{
    private String endPoint = "http://localhost:8080/RestfulService";

     /**
     * Function that get service endpoint
     * @return endPoint string with endpoint
     */
    @Override
    public String getEndPoint() {
        return endPoint;
    }

    /**
     * Function that set service endpoint
     * @param aEndPoint string with endpoint
     */
    @Override
    public void setEndPoint(String aEndPoint) {
        endPoint = aEndPoint;
    }

    /**
     * Function that getting all information from the service
     * @return Array of weapon objects
     * @throws Exception
     */
    @Override
    public Weapon[] getAllInformation() throws Exception{
        /* Getting web resources object */
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(endPoint);
        /* Send http queary to restful service */
        String xml = service.path("resources").path("weapons").accept(
                    MediaType.APPLICATION_XML).get(String.class);
        /* Parse xml document to weapon object */
        XStream xstream = new XStream();
        xstream.alias("weaponclasses",  Weapon[].class);
        Weapon[] weapons = (Weapon[]) xstream.fromXML(xml);
        return weapons;
    }

    /**
     * Function that add new weapon in service base
     * @param weapon added weapon object
     * @return logical results of operation
     * @throws Exception
     */
    @Override
    public boolean addNewWeapon(Weapon weapon) {
        /* Getting web resources object */
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(endPoint);
        /* Parse object to xml document */
        XStream xstream = new XStream();
        xstream.alias("weaponclass",  Weapon.class);
        String xml = xstream.toXML(weapon);
        /* Send http queary to restful service */
        ClientResponse response = service.path("resources").path("weapons").
                type("application/xml").post(ClientResponse.class, xml);
        /* Check respons status */
        if (response.getStatus() == 200) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Function that delete weapon from the service
     * @param weapon deleted weapon object
     * @return logical results of operation
     * @throws Exception
     */
    @Override
    public boolean deleteWeapon(Weapon weapon) {
        /* Getting web resources object */
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(endPoint);
        /* Parse object to xml document */
        XStream xstream = new XStream();
        xstream.alias("weaponclass",  Weapon.class);
        String xml = xstream.toXML(weapon);
        /* Send http queary to restful service */
        ClientResponse response = service.path("resources")
                .path("weapons/" + weapon.getName()).delete(ClientResponse.class);
        /* Check respons status */
        if (response.getStatus() == 200) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Function that update records in service base
     * @param weapon updated weapon object
     * @return logical results of operation
     * @throws Exception
     */
    @Override
    public boolean updateWeapon(Weapon weapon) {
        /* Getting web resources object */
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(endPoint);
        /* Parse object to xml document */
        XStream xstream = new XStream();
        xstream.alias("weaponclass",  Weapon.class);
        String xml = xstream.toXML(weapon);
        /* Send http queary to restful service */
        ClientResponse response = service.path("resources")
                .path("weapons").type(MediaType.APPLICATION_XML).put(ClientResponse.class, xml);
        /* Check respons status */
        if (response.getStatus() == 200) {
            return true;
        } else {
            return false;
        }
    }
}
