package com.example.hometestnew;

//import com.example.hometestnew.jwt.JwtUtil;
import com.example.hometestnew.models.ApiResponse;
import com.example.hometestnew.models.User;
import com.example.hometestnew.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
//@RequestMapping("/registration")
public class HomeTestNewApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeTestNewApplication.class, args);
	}
//	@GetMapping("/hello")
//	public String hello() {
//		return "Hello World";
//	}



}
