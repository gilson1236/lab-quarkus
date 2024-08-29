package api.dto.out;

import java.util.List;
import java.util.Optional;

public record Election(String id, List<Candidate> candidates) {

    public record Candidate(String id,
                            Optional<String> photo,
                            String fullName,
                            String email,
                            Optional<String> phone,
                            Optional<String> jogTitle,
                            Integer votes) {
        public static api.dto.out.Candidate fromDomain(domain.Candidate candidate){
            return new api.dto.out.Candidate(candidate.id(),
                                                candidate.photo(),
                                        candidate.givenName()  + " " + candidate.familyName(),
                                                candidate.email(),
                                                candidate.phone(),
                                                candidate.jobTitle());
        }
    }

    public static Election fromDomain(domain.Election election){
        var candidates = election.votes()
                .entrySet()
                .stream()
                .map(entry -> new Candidate(entry.getKey().id(),
                        entry.getKey().photo(),
                        entry.getKey().givenName() + " " + entry.getKey().familyName(),
                        entry.getKey().email(),
                        entry.getKey().phone(),
                        entry.getKey().jobTitle(),
                        entry.getValue()))
                .toList();

        return new Election(election.id(), candidates);
    }
}
