package com.example.postventa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.postventa.model.PostventaModel;

@Repository
public interface PostventaRepository extends JpaRepository<PostventaModel, Integer> {

}
