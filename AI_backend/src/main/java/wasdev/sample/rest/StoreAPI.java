package wasdev.sample.rest;

import wasdev.sample.Visitor;
import wasdev.sample.store.CloudantVisitorStore;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collection;

/**
 * Created by demerzel on 2019-07-14.
 */
@ApplicationPath("api")
@Path("/store")
public class StoreAPI {
    CloudantVisitorStore cloudantVisitorStore = new CloudantVisitorStore();

    @GET
    // @Path("/") // This anotation works in Liberty but not with Tomcat/Jersey.
    @Produces({"application/json"})
    public Collection<Visitor> getHelp() {
        Collection<Visitor> docs = cloudantVisitorStore.getAll();
        return docs;
    }
}
