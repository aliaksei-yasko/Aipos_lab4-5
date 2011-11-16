/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alex.aipos.lab5;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Admin
 */

public class RestfulApplication extends Application{
    @Override
     public Set<Class<?>> getClasses() {
         Set<Class<?>> s = new HashSet<Class<?>>();
         s.add(WeaponService.class);
         return s;
     }
}
