package wasdev.sample.rest;

import wasdev.sample.Visitor;
import wasdev.sample.store.CloudantVisitorStore;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

/**
 * Created by demerzel on 2019-07-14.
 */
@ApplicationPath("api")
@Path("/volunteer")
public class UserAPI {

    private double dis(double x1, double y1, double x2, double y2){
        return Math.sqrt((x2-x1) * (x2-x1) + (y2-y1) * (y2-y1));
    }

    CloudantVisitorStore cloudantVisitorStore = new CloudantVisitorStore();
    @POST
    @Consumes("application/json")
    public List<String> getLocation(String location) {
        String[] arrayLocation = location.split(":");
        double x1 = Double.parseDouble(arrayLocation[0]);
        double y1 = Double.parseDouble(arrayLocation[1]);
        Collection<Visitor> docs = cloudantVisitorStore.getAll();
        List<String> list = new ArrayList<>();
        for (Visitor visitor: docs
             ) {
            String[] array2 =  visitor.getName().split(":");
            double x2 = Double.parseDouble(array2[0]);
            double y2 = Double.parseDouble(array2[1]);
            if(dis(x1,y1,x2,y2)<20){
                list.add(visitor.getName());
            }
        }
        return list;
    }
}
