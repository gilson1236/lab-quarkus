package infrastructure.resources;

import domain.Election;
import domain.ElectionApi;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/elections")
public class ElectionResource {
    private final ElectionApi electionApi;

    public ElectionResource(ElectionApi electionApi) {
        this.electionApi = electionApi;
    }

    @POST
    @ResponseStatus(RestResponse.StatusCode.CREATED)
    @Transactional
    public void submit(){
        electionApi.submit();
    }

    @GET
    public List<Election> list(){
        return electionApi.findAll();
    }
}
