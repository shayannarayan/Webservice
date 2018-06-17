package com.awo.app.controller.registration;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.awo.app.constant.StatusCode;
import com.awo.app.domain.login.Authentication;
import com.awo.app.domain.registration.Registration;
import com.awo.app.model.address.AddrModel;
import com.awo.app.model.registration.RegAdr;
import com.awo.app.model.registration.RegistrationModel;
import com.awo.app.response.ErrorObject;
import com.awo.app.response.Response;
import com.awo.app.service.registration.RegService;
import com.awo.app.utils.CommonUtils;



@RestController
@CrossOrigin(origins="http://localhost:4300", allowedHeaders="*")
public class RegController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(RegController.class);
	
	@Autowired
	RegService regService;
	
	@PostMapping(value="/user")
	public Response saveReg(@RequestBody RegistrationModel model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("AddUser: Received request URL:"+ request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("AddUser: Received request: "+ CommonUtils.getJson(model));
		return regService.saveReg(model);
	}
	
	@GetMapping(value= "/userdetails/{area}/{pincode}", produces = "application/json")
	public @ResponseBody String getDetailsByAreaAndPincode(@PathVariable("area") String area, @PathVariable("pincode") String pincode, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		logger.info("Get User/DonorDetails:" + request.getRequestURL().toString()
				+((request.getQueryString()==null) ? "" : "?" + request.getQueryString().toString()));
		List<AddrModel> model = regService.getDetailsByAreaAndPincode(area, pincode);
		Response res = CommonUtils.getResponseObject("User Details");
		if(model == null) {
			ErrorObject err = CommonUtils.getErrorResponse("User/Donor Not Found", "User/Donor Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		}else
		{
			res.setData(model);
			
		}
		logger.info("getUser: sent Response");
		return CommonUtils.getJson(res);
		
	}
	
	@GetMapping(value= "/userdetails/{regId}", produces = "application/json")
	public @ResponseBody String getDetailsById(@PathVariable("regId") String regId, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		logger.info("Get User/DonorDetails:" + request.getRequestURL().toString()
				+((request.getQueryString()==null) ? "" : "?" + request.getQueryString().toString()));
		Registration model = regService.getDetailsById(regId);
		Response res = CommonUtils.getResponseObject("User Details");
		if(model == null) {
			ErrorObject err = CommonUtils.getErrorResponse("User/Donor Not Found", "User/Donor Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		}else
		{
			res.setData(model);
			
		}
		logger.info("getUser: sent Response");
		return CommonUtils.getJson(res);
		
	}
	
	
	@GetMapping(value="/user/{regId}", produces = "application/json")
	public @ResponseBody String getById(@PathVariable("regId") String regId, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		logger.info("Get User/DonorDetails:" + request.getRequestURL().toString()
				+((request.getQueryString()==null) ? "" : "?" + request.getQueryString().toString()));
		RegAdr model = regService.getById(regId);
		Response res = CommonUtils.getResponseObject("User Details");
		if(model == null) {
			ErrorObject err = CommonUtils.getErrorResponse("User/Donor Not Found", "User/Donor Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		}else
		{
			res.setData(model);
			
		}
		logger.info("getUser: sent Response");
		return CommonUtils.getJson(res);
		
	}
	
	@DeleteMapping(value="/user/{regId}")
	public Response deleteById(@PathVariable("regId") String regId, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		logger.info("Delete User :" + request.getRequestURI().toString()
		+((request.getQueryString()==null) ? "" : "?" + request.getQueryString().toString()));
		return  regService.deleteById(regId);
		
	}
	
	@PostMapping(value="/login")
	public @ResponseBody String authenticate(@RequestBody RegistrationModel model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("authenticate User: Received request URL:"+ request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("authenticate User: Received request: "+ CommonUtils.getJson(model));
		Authentication log = regService.authenticate(model);
		Response res = CommonUtils.getResponseObject("authenticate User");
		if(log == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Exception in User Login", "Invalid Username or Password");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		}else {
			res.setData(log);
		}
		logger.info("Authenticate: sent Response");
		return CommonUtils.getJson(res);
		
	}
	
	@GetMapping(value="/users", produces = "application/json")
	public @ResponseBody String getUsers(HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Get Users: Received request: "+ request.getRequestURI().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<RegistrationModel> reg = regService.getUsers();
		Response res = CommonUtils.getResponseObject("List of Users");
		if(reg==null) {
			ErrorObject err = CommonUtils.getErrorResponse("Users not found", "Users not found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		}else {
			res.setData(reg);
		}
		logger.info("Get AllUsers: sent Response");
		return CommonUtils.getJson(res); 
		
	}
	
	

}
