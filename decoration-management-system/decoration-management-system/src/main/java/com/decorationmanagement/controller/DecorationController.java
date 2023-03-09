package com.decorationmanagement.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.decorationmanagement.dto.CommanApiResponse;
import com.decorationmanagement.dto.DecorationAddRequest;
import com.decorationmanagement.entity.Decoration;
import com.decorationmanagement.service.DecorationService;
import com.decorationmanagement.utility.Constants.ResponseCode;
import com.decorationmanagement.utility.StorageService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/decoration/")
@CrossOrigin(origins = "http://localhost:3000")
public class DecorationController {
		
	Logger LOG = LoggerFactory.getLogger(DecorationController.class);
	
	@Autowired
	private DecorationService decorationService;
	
	@Autowired
	private StorageService storageService;
	
	
	@PostMapping("add")
	@ApiOperation(value = "Api to add decoration")
	public ResponseEntity<?> addDecoration(DecorationAddRequest decorationAddRequest) {
		LOG.info("Recieved request for Add Decoration");

		CommanApiResponse response = new CommanApiResponse();

		if (decorationAddRequest == null) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Hotel Location is not selected");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
		
		Decoration decoration = new Decoration();
		decoration.setDescription(decorationAddRequest.getDescription());
		decoration.setName(decorationAddRequest.getName());
        decoration.setPrice(decorationAddRequest.getPrice());
		
        String image = storageService.store(decorationAddRequest.getImage());
        
        decoration.setImage(image);
        
        Decoration addedDecoration = this.decorationService.addDecoration(decoration);
        
		if (addedDecoration != null) {
			response.setResponseCode(ResponseCode.SUCCESS.value());
			response.setResponseMessage("Decoration Added Successfully");
			return new ResponseEntity(response, HttpStatus.OK);
		}

		else {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Failed to add Decoration");
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("id")
	@ApiOperation(value = "Api to fetch decorations by using Decoration Id")
	public ResponseEntity<?> fetchHotel(@RequestParam("decorationId") int decorationId) {
		LOG.info("Recieved request for Fetch Hotel using Decoration Id");

		Decoration decoration = null;
		
		decoration = this.decorationService.getDecorationById(decorationId);

		return new ResponseEntity(decoration, HttpStatus.OK);

	}

	@GetMapping("fetch")
	@ApiOperation(value = "Api to fetch all decorations")
	public ResponseEntity<?> fetchAllDecorations() {
		LOG.info("Recieved request for Fetch Decorations");

		List<Decoration> decorations = new ArrayList<>();

		decorations = this.decorationService.getAllDecoration();
		
		return new ResponseEntity(decorations, HttpStatus.OK);

	}
	
	@GetMapping(value="/{decorationImageName}", produces = "image/*")
	@ApiOperation(value = "Api to fetch decoration image by using image name")
	public void fetchProductImage(@PathVariable("decorationImageName") String decorationImageName, HttpServletResponse resp) {
		System.out.println("request came for fetching product pic");
		System.out.println("Loading file: " + decorationImageName);
		Resource resource = storageService.load(decorationImageName);
		if(resource != null) {
			try(InputStream in = resource.getInputStream()) {
				ServletOutputStream out = resp.getOutputStream();
				FileCopyUtils.copy(in, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("response sent!");
	}
	
}
