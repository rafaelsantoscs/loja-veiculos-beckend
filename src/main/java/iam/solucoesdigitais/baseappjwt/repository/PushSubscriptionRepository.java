package iam.solucoesdigitais.baseappjwt.repository;

import iam.solucoesdigitais.baseappjwt.model.PushSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PushSubscriptionRepository extends JpaRepository<PushSubscription, Long> {

    List<PushSubscription> findByUsernameAndActiveTrue(String username);
    
    List<PushSubscription> findByUsernameInAndActiveTrue(List<String> usernames);
    
    List<PushSubscription> findByActiveTrue();
    
    Optional<PushSubscription> findByUsernameAndEndpointAndActiveTrue(String username, String endpoint);

    @Modifying
    @Query("UPDATE PushSubscription p SET p.active = false WHERE p.username = :username")
    void deactivateAllByUsername(@Param("username") String username);

    @Modifying
    @Query("UPDATE PushSubscription p SET p.active = false WHERE p.username = :username AND p.endpoint = :endpoint")
    void deactivateByUsernameAndEndpoint(@Param("username") String username, @Param("endpoint") String endpoint);
}
