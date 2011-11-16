package alex.clients;

import javax.xml.rpc.ServiceException;
import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import alex.classes.Weapon;
import javax.xml.namespace.QName;

/**
 *
 * @author Alexei Yasko
 * Class that perform all operations with rpc service
 */
public class RPCPerformer implements Performer{

    private String endPoint  =
            "http://localhost:8080/axis/services/WeaponService";

    /**
     * Function that set service endpoint
     * @param endPoint string with endpoint
     */
    @Override
    public void setEndPoint(String aEndPoint){
        endPoint = aEndPoint;
    }

     /**
     * Function that get service endpoint
     * @return endPoint string with endpoint
     */
    @Override
    public String getEndPoint(){
        return endPoint;
    }

    /**
     * Function that getting all information from the service
     * @return Array of weapon objects
     * @throws ServiceException
     * @throws AxisFault
     */
    @Override
    public Weapon[] getAllInformation() throws ServiceException, AxisFault{
        /* Create service object */
        Service service = new Service();
        /* Get call object */
        Call call;
        call = (Call) service.createCall();
        call.setTargetEndpointAddress(endPoint);

        Weapon[] retWeapon = null;
        QName qn = new QName( "urn:WeaponService", "Weapon" );

        /* Register for serialization our type "Weapon" */
        call.registerTypeMapping(Weapon.class, qn,
                  new org.apache.axis.encoding.ser.BeanSerializerFactory(Weapon.class, qn),
                  new org.apache.axis.encoding.ser.BeanDeserializerFactory(Weapon.class, qn));

        /* Geting results */
        retWeapon = (Weapon[])call.invoke("viewAllWeapons", new Object[]{});
        return retWeapon;
    }

    /**
     * Function that delete weapon from the service
     * @param deletedWeapon deleted weapon object
     * @return logical results of operation
     * @throws ServiceException
     * @throws AxisFault
     */
    @Override
    public boolean deleteWeapon(Weapon deletedWeapon) throws ServiceException, AxisFault{
        /* Create service object*/
        Service service = new Service();
        /* Get call object */
        Call call;
        call = (Call) service.createCall();
        call.setTargetEndpointAddress(endPoint);

        QName qn = new QName( "urn:WeaponService", "Weapon" );

        /* Register for serialization our type "Weapon" */
        call.registerTypeMapping(Weapon.class, qn,
                  new org.apache.axis.encoding.ser.BeanSerializerFactory(Weapon.class, qn),
                  new org.apache.axis.encoding.ser.BeanDeserializerFactory(Weapon.class, qn));

        /* Geting results */
        boolean result = (Boolean)call.invoke("deleteWeapon", new Object[]{deletedWeapon});

        return result;
    }

    /**
     * Function that add new weapon in service base
     * @param newWeapon added weapon object
     * @return logical results of operation
     * @throws ServiceException
     * @throws AxisFault
     */
    @Override
    public boolean addNewWeapon(Weapon newWeapon) throws ServiceException, AxisFault{
        /* Create service object */
        Service service = new Service();
        /* Get call object */
        Call call;
        call = (Call) service.createCall();
        call.setTargetEndpointAddress(endPoint);

        QName qn = new QName( "urn:WeaponService", "Weapon" );

        /* Register for serialization our type "Weapon" */
        call.registerTypeMapping(Weapon.class, qn,
                  new org.apache.axis.encoding.ser.BeanSerializerFactory(Weapon.class, qn),
                  new org.apache.axis.encoding.ser.BeanDeserializerFactory(Weapon.class, qn));

        /* Geting results */
        boolean result = (Boolean)call.invoke("addNewWeapon", new Object[]{newWeapon});

        return result;
    }

    /**
     * Function thta update records in service base
     * @param updateWeapon updated weapon object
     * @return logical results of operation
     * @throws ServiceException
     * @throws AxisFault
     */
    @Override
    public boolean updateWeapon(Weapon updateWeapon) throws ServiceException, AxisFault{
        /* Create service object */
        Service service = new Service();
        /* Get call object */
        Call call;
        call = (Call) service.createCall();
        call.setTargetEndpointAddress(endPoint);

        QName qn = new QName( "urn:WeaponService", "Weapon" );

        /* Register for serialization our type "Weapon" */
        call.registerTypeMapping(Weapon.class, qn,
                  new org.apache.axis.encoding.ser.BeanSerializerFactory(Weapon.class, qn),
                  new org.apache.axis.encoding.ser.BeanDeserializerFactory(Weapon.class, qn));

        /* Geting results */
        boolean result = (Boolean)call.invoke("updateWeapon", new Object[]{updateWeapon});

        return result;
    }
}
