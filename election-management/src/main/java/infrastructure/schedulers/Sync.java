package infrastructure.schedulers;

import domain.Election;
import domain.annotations.Principal;
import infrastructure.repositories.RedisElectionRepository;
import infrastructure.repositories.SQLElectionRepository;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class Sync {
    private final SQLElectionRepository sqlRepository;
    private static RedisElectionRepository redisRepository;

    public Sync(@Principal SQLElectionRepository sqlElectionRepository,
                RedisElectionRepository redisElectionRepository) {
        this.sqlRepository = sqlElectionRepository;
        this.redisRepository = redisElectionRepository;
    }

    @Scheduled(cron = "*/10 * * * * ?")
    void sync(){
        sqlRepository.findAll().forEach(election -> sqlRepository
                        .sync(redisRepository.sync(election)));

    }
}
