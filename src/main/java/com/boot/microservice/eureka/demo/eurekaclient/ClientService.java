package com.boot.microservice.eureka.demo.eurekaclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;

@RestController
@RequestMapping("/rest/client")
public class ClientService {
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod= "fallback", groupKey= "Hello",
			commandKey ="hello",
			threadPoolKey ="helloThread")
	@RequestMapping
	public String hello(){
		String url="http://config-server-client/rest/users/hello";
		return restTemplate.getForObject(url , String.class);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/getData")
	public List getData(){
		String url="http://config-server-client/rest/users/all";
		return restTemplate.getForObject(url ,List.class);
	}
	
	public String fallback(Throwable hystrixCommand){
		return "Fall Back Called";
	}

}
