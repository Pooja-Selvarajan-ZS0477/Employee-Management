package com.employee.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.employee.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    public UserRepository userRepository;


    public UserService() {
		// TODO Auto-generated constructor stub
	}
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                        new ArrayList<>());
            }
            throw new UsernameNotFoundException("User is not available");
        }

        public List<User> getAllUsers() {
            List<User> user = new ArrayList<>();
            userRepository.findAll().forEach(user::add);
            return user;
        }

        public void insertUser(User user) {
            userRepository.save(user);
        }

        public void updateUser(User user) {
            userRepository.save(user);
        }

        public boolean deleteUser(int userId) {
            userRepository.deleteById(userId);
            return true;
        }
    }
