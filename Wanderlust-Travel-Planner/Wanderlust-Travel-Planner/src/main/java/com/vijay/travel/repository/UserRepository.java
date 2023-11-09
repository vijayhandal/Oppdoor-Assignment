package com.vijay.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vijay.travel.model.User;

public interface UserRepository extends JpaRepository<User, String> {
}
