package FinanceDataProcessingAndAccessControl.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import FinanceDataProcessingAndAccessControl.dto.DashboardSummary;
import FinanceDataProcessingAndAccessControl.dto.UserRequest;
import FinanceDataProcessingAndAccessControl.dto.UserResponse;
import FinanceDataProcessingAndAccessControl.entity.User;
import FinanceDataProcessingAndAccessControl.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    // CREATE
    public UserResponse create(UserRequest req) {
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(req.getPassword());
        u.setRole(req.getRole());

        repo.save(u);
        return mapToResponse(u);
    }

    // READ
    public List<UserResponse> getAll() {
    	List<User> users = repo.findByActiveTrue();
    	List<UserResponse> list = new ArrayList<>();

        for (User u : users) {
            list.add(mapToResponse(u));
        }

        return list;
    }

    // UPDATE
    public UserResponse update(Long id, UserRequest req) {
        User u = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setRole(req.getRole());

        repo.save(u);
        return mapToResponse(u);
    }

    // DELETE (Soft Delete)
    public String delete(Long id) {
        User u = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!u.isActive()) {
            return "User already inactive";
        }

        u.setActive(false); 
        repo.save(u);

        return "User deactivated successfully";
    }

    // MAPPER
    private UserResponse mapToResponse(User u) {
        UserResponse res = new UserResponse();
        res.setId(u.getId());
        res.setName(u.getName());
        res.setEmail(u.getEmail());
        res.setRole(u.getRole());
        return res;
    }
}