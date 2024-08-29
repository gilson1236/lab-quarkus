package infrastructure.repositories;

import domain.Candidate;
import domain.CandidateQuery;
import domain.CandidateRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class SQLCandidateRepository implements CandidateRepository {

    private final EntityManager entityManager;

    public SQLCandidateRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(List<Candidate> candidates) {
        candidates.stream()
                .map(infrastructure.repositories.entities.Candidate::fromDomain)
                .forEach(entityManager::merge);
    }

    @Override
    public List<Candidate> find(CandidateQuery query) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var createQuery = criteriaBuilder.createQuery(infrastructure.repositories.entities.Candidate.class);
        var root = createQuery.from(infrastructure.repositories.entities.Candidate.class);

        createQuery.select(root).where(conditions(query, criteriaBuilder, root));

        return entityManager.createQuery(createQuery)
                .getResultStream()
                .map(infrastructure.repositories.entities.Candidate::toDomain)
                .toList();
    }

    @Override
    public void delete(String id) {

        entityManager.remove(findById(id));
    }

    private Predicate[] conditions(CandidateQuery query, CriteriaBuilder criteriaBuilder,
                                   Root<infrastructure.repositories.entities.Candidate> root){
        return Stream.of(query.ids().map(id -> criteriaBuilder.in(root.get("id")).value(id)),
                        query.name().map(name-> criteriaBuilder.or(criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("familyName")), name.toLowerCase() + "%"),
                                                                    criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("givenName")), name.toLowerCase() + "%"))))
                .flatMap(Optional::stream)
                .toArray(Predicate[]::new);
    }
}
