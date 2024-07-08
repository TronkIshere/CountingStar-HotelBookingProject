package com.example.CoutingStarHotel.service;

import com.example.CoutingStarHotel.exception.RoleAlreadyExistException;
import com.example.CoutingStarHotel.exception.UserAlreadyExistsException;
import com.example.CoutingStarHotel.model.Role;
import com.example.CoutingStarHotel.model.User;
import com.example.CoutingStarHotel.repository.RoleReponsitory;
import com.example.CoutingStarHotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{
    private final RoleReponsitory roleReponsitory;
    private final UserRepository userRepository;
    @Override
    public List<Role> getRoles() {
        return roleReponsitory.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_"+theRole.getName().toUpperCase();
        Role role = new Role(roleName);
        if (roleReponsitory.existsByName(roleName)){
            throw new RoleAlreadyExistException(theRole.getName()+" role already exists");
        }
        return roleReponsitory.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUsersFromRole(roleId);
        roleReponsitory.deleteById(roleId);
    }

    @Override
    public Role findByName(String name) {
        return roleReponsitory.findByName(name).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role>  role = roleReponsitory.findById(roleId);
        if (user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlreadyExistsException(
                    user.get().getFirstName()+ " is already assigned to the" + role.get().getName()+ " role");
        }
        if (role.isPresent()){
            role.get().assignRoleToUser(user.get());
            roleReponsitory.save(role.get());
        }
        return user.get();
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role>  role = roleReponsitory.findById(roleId);
        if (user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlreadyExistsException(
                    user.get().getFirstName()+ " is already assigned to the" + role.get().getName()+ " role");
        }
        if (role.isPresent()){
            role.get().assignRoleToUser(user.get());
            roleReponsitory.save(role.get());
        }
        return user.get();
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role = roleReponsitory.findById(roleId);
        role.ifPresent(Role::removeAllUsersFromRole);
        return roleReponsitory.save(role.get());
    }
}
