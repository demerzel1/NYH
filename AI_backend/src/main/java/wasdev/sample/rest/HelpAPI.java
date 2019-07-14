package wasdev.sample.rest;

/**
 * Created by demerzel on 2019-07-14.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import com.google.gson.Gson;

import wasdev.sample.Utils.HumanPose;
import wasdev.sample.Visitor;
import wasdev.sample.store.CloudantVisitorStore;
import wasdev.sample.store.VisitorStore;
import wasdev.sample.store.VisitorStoreFactory;

@ApplicationPath("api")
@Path("/help")
public class HelpAPI {

    CloudantVisitorStore cloudantVisitorStore = new CloudantVisitorStore();

    @GET
    // @Path("/") // This anotation works in Liberty but not with Tomcat/Jersey.
    @Produces({"application/json"})
    public String getHelp() throws IOException {
        HumanPose humanPose = new HumanPose();
        List<Object> list = humanPose.getHelp();
        if(list.size()==2){
            Visitor visitor = new Visitor();
            visitor.setName(list.get(1).toString());
            cloudantVisitorStore.persist(visitor);
            return "1";
        }else
            return "0";
    }


}
