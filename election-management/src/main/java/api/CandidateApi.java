package api;

import api.dto.out.Candidate;
import domain.CandidateService;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CandidateApi {

    private final CandidateService candidateService;

    public CandidateApi(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    public void create(api.dto.in.CreateCandidate dto) {
        candidateService.save(dto.toDomain());
    }

    public api.dto.out.Candidate update(String id, api.dto.in.UpdateCandidate dto){
        candidateService.save(dto.toDomain(id));
        return api.dto.out.Candidate.fromDomain(candidateService.findById(id));
    }

    public List<api.dto.out.Candidate> list() {
        return candidateService.findAll().stream().map(Candidate::fromDomain).toList();
    }

    public void delete(String id) {
        candidateService.delete(id);
    }
}
