package HRAts.service;

import HRAts.model.Role;
import HRAts.model.User;
import HRAts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Iterable<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(int userId) {
        userRepository.delete(userId);
    }
}
