package com.decorationmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.decorationmanagement.entity.Decoration;

@Repository
public interface DecorationDao extends JpaRepository<Decoration, Integer> {

}

