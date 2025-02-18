package pro.anieldev.tecconn.repository;

import org.springframework.data.repository.CrudRepository;
import pro.anieldev.tecconn.model.User;

public interface UserRepo extends CrudRepository<User, Integer> {
    public User findByEmail(String email);
}
