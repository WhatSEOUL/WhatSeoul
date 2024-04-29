package com.example.whatseoul.repository.area;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.whatseoul.entity.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {
}
