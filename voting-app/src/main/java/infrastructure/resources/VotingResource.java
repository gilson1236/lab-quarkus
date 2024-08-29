package infrastructure.resources;

import api.ElectionApi;
import api.dto.out.Election;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("/api/voting")
public class VotingResource {

    private final ElectionApi electionApi;

    public VotingResource(ElectionApi electionApi) {
        this.electionApi = electionApi;
    }

    @GET
    public List<Election> elections(){
        return electionApi.findAll();
    }

    @POST
    @Path("/elections/{electionId}/candidates/{candidateId}")
    @ResponseStatus(RestResponse.StatusCode.ACCEPTED)
    @Transactional
    public void vote(@PathParam("electionId") String electionId, @PathParam("candidateId") String candidateId){
        electionApi.vote(electionId, candidateId);
    }
}
