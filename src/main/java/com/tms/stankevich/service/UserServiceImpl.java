package com.tms.stankevich.service;

import com.tms.stankevich.dao.RoleRepository;
import com.tms.stankevich.dao.UserRepository;
import com.tms.stankevich.domain.user.Role;
import com.tms.stankevich.domain.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    /*@PersistenceContext
    private EntityManager em;*/
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final String USER_ROLE = "ROLE_USER";
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(roleRepository.findByName(USER_ROLE)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

   /* public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }*/

   @Override
    public List<User> getUsersByRole(String roleName) {
        return userRepository.findUsersByRoles(roleRepository.findByName(roleName));
    }

    @Override
    public void updateAdminRole(Long userId, String action) {
        Role userRole = roleRepository.findByName(USER_ROLE);
        Role adminRole = roleRepository.findByName(ADMIN_ROLE);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            switch (action) {
                case "delete":
                    if (user.getRoles().contains(adminRole)) {
                        user.getRoles().remove(adminRole);
                        user.getRoles().add(userRole);
                    }
                    break;
                case "add":
                    if (!user.getRoles().contains(adminRole)) {
                        user.getRoles().remove(userRole);
                        user.getRoles().add(adminRole);
                    }
                    break;
            }
            userRepository.save(user);
        }
    }
}
