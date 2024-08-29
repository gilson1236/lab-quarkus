package domain;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ElectionService {
    private final ElectionRepository electionRepository;

    public ElectionService(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    public List<Election> findAll(){
        return electionRepository.findAll();
    }

    public void vote(String electionId, String candidateId) {
        Election election = electionRepository.findById(electionId);
        election.candidates().stream().filter(candidate -> candidate.id().equals(candidateId))
                .findFirst()
                .ifPresent(candidate -> electionRepository.vote(electionId, candidate));
    }
}
