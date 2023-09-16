package org.vip.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vip.bookmyshow.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
