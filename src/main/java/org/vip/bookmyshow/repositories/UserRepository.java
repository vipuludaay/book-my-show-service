package org.vip.bookmyshow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vip.bookmyshow.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
