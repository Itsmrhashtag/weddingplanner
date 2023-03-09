package com.decorationmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decorationmanagement.dao.DecorationDao;
import com.decorationmanagement.entity.Decoration;

@Service
public class DecorationService {
	
	@Autowired
	private DecorationDao decorationDao;
	
	public Decoration addDecoration(Decoration decoration) {
		return decorationDao.save(decoration);
	}
	 
	public Decoration getDecorationById(int decorationId) {
		return decorationDao.findById(decorationId).get();
	}
	
	public List<Decoration> getAllDecoration() {
		return this.decorationDao.findAll();
	}

}
