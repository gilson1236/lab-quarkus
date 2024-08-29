package api;

import api.dto.out.Election;
import domain.ElectionService;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ElectionApi {
    private final ElectionService electionService;

    public ElectionApi(ElectionService electionService) {
        this.electionService = electionService;
    }

    public List<Election> findAll(){
        return electionService.findAll().stream().map(Election::fromDomain).toList();
    }

    public void vote(String electionId, String candidateId){
        electionService.vote(electionId, candidateId);
    }
}
