package com.example.hometestnew.auth;

import com.example.hometestnew.Service.BannerService;
import com.example.hometestnew.Service.ServiceService;
import com.example.hometestnew.Service.TransactionService;
import com.example.hometestnew.Service.UserService;
import com.example.hometestnew.jwt.JwtUtil;
import com.example.hometestnew.models.*;
import com.example.hometestnew.repository.ServiceRepository;
//import com.example.hometestnew.repository.TransactionTypeRepository;
import com.example.hometestnew.repository.TransactionRepository;
import com.example.hometestnew.repository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    BannerService bannerService;

    @Autowired
    ServiceService serviceService;

    @Autowired
    private ServiceRepository serviceRepository;

    private List<Map<String, Object>> transactionHistory = new ArrayList<>();

//    @Autowired
//    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;


    // Endpoint Login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody Map<String, String> loginData) {
        // Define the required parameters for login
        String email = loginData.get("email");
        String password = loginData.get("password");

        // Logic to validate the email and password
        if (email == null || !email.contains("@")) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, 102, "Parameter email tidak sesuai format");
        }

        // Logic to validate the email and password
        User user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) { // Compare hashed passwords instead
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 103, "Username atau password salah");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(email);
        Map<String, Object> data = Map.of("token", token);
        // Respond with the token if the login is successful
        return ResponseEntity.ok(new ApiResponse(0, "Login berhasil", data));
    }

    // Endpoint Register
    @PostMapping("/registration")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody User user) {
        // Validate the email format
        if (!user.getEmail().contains("@")) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, 102, "Format email tidak valid");
        }
        user.setBalance(0); // Set default balance when creating a new user

        // Save the user to the database
        userRepository.save(user);

        //Respond with a success message if the registration is successful
        return ResponseEntity.ok(new ApiResponse(0, "Registrasi berhasil silahkan login", null));
    }

    // Endpoint Profile
    @SecurityRequirement(name = "JavaInUseSecurityScheme")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile(HttpServletRequest request) {
        // Validate the Authorization header
        String authHeader = request.getHeader("Authorization");

        // Check if the Authorization header is present and valid
        if (!isValidAuthHeader(authHeader)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Extract the token from the Authorization header
        String token = authHeader.substring(7);

        // Check if the token is valid
        if (!jwtUtil.isTokenValid(token)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Extract the email from the token
        String email = jwtUtil.extractEmail(token);
        User user = userRepository.findByEmail(email);

        // Check if the user exists
        if (user == null) {
            return createErrorResponse(HttpStatus.NOT_FOUND, 109, "User not found");
        }

        // Prepare the response data
        Map<String, Object> data = createUserProfileData(user);
        return ResponseEntity.ok(new ApiResponse(0, "Sukses", data));
    }

    // Endpoint Update Profile
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "JavaInUseSecurityScheme")
    @PutMapping(value = "/profile/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> updateProfile(@RequestBody Map<String, String> updateData, HttpServletRequest request) {
        // Validate the Authorization header
        String authHeader = request.getHeader("Authorization");

        // Check if the Authorization header is present and valid
        if (!isValidAuthHeader(authHeader)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Extract the token from the Authorization header
        String token = authHeader.substring(7);

        // Check if the token is valid
        if (!jwtUtil.isTokenValid(token)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Extract the email from the token
        String email = jwtUtil.extractEmail(token);
        User user = userRepository.findByEmail(email);

        // Check if the user exists
        if (user == null) {
            return createErrorResponse(HttpStatus.NOT_FOUND, 109, "User not found");
        }

        // Update the user profile data
        String firstName = updateData.get("first_name");
        String lastName = updateData.get("last_name");

        // Update the user profile data
        if (firstName != null) {
            user.setFirstName(firstName);
        }

        // Update the user profile data
        if (lastName != null) {
            user.setLastName(lastName);
        }

        // Save the updated user profile data
        userRepository.save(user);

        // Prepare the response data
        Map<String, Object> data = createUserProfileData(user);

        // Respond with a success message if the profile is updated successfully
        return ResponseEntity.ok(new ApiResponse(0, "Update Profile berhasil", data));
    }

    // Helper method to create user profile data
    private Map<String, Object> createUserProfileData(User user) {
        Map<String, Object> data = new HashMap<>();
        data.put("email", user.getEmail());
        data.put("first_name", user.getFirstName());
        data.put("last_name", user.getLastName());
        data.put("profile_image", user.getProfileImage());
        return data;
    }

    // Helper method to validate the Authorization header
    private boolean isValidAuthHeader(String authHeader) {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }

    // Helper method to create error response
    private ResponseEntity<ApiResponse> createErrorResponse(HttpStatus httpStatus, int customCode, String message){
        ApiResponse errorResponse = new ApiResponse(customCode, message, null);
        return ResponseEntity.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    // Endpoint Upload Profile Image
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "JavaInUseSecurityScheme")
    @PutMapping(value = "/profile/image", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<ApiResponse> uploadProfileImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        // Validate the Authorization header
        String authHeader = request.getHeader("Authorization");

        // Check if the Authorization header is present and valid
        if (!isValidAuthHeader(authHeader)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Extract the token from the Authorization header
        String token = authHeader.substring(7);

        // Check if the token is valid
        if (!jwtUtil.isTokenValid(token)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Extract the email from the token
        String email = jwtUtil.extractEmail(token);

        // Check if the user exists
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return createErrorResponse(HttpStatus.NOT_FOUND, 109, "User not found");
        }

        // Check if the file is an image
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !(originalFilename.toLowerCase().endsWith(".jpg") ||
                originalFilename.toLowerCase().endsWith(".jpeg") ||
                originalFilename.toLowerCase().endsWith(".png"))) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, 104, "Format Image tidak sesuai");
        }

        // Save the uploaded image to the server
        Path uploadPath = Paths.get("./uploads/" + email + "" + System.currentTimeMillis() + "" + originalFilename);
        Files.createDirectories(uploadPath.getParent());
        Files.copy(file.getInputStream(), uploadPath);

        // Update the user profile image
        user.setProfileImage(uploadPath.toString());
        // Save the updated user profile image
        userRepository.save(user);

        // Prepare the response data
        Map<String, Object> data = new HashMap<>();
        data.put("email", user.getEmail());
        data.put("first_name", user.getFirstName());
        data.put("last_name", user.getLastName());
        data.put("profile_image", uploadPath.toString());

        // Respond with a success message if the profile image is updated successfully
        return ResponseEntity.ok(new ApiResponse(0, "Update Profile Image berhasil", data));
    }


    // Endpoint banner
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "JavaInUseSecurityScheme")
    @GetMapping(value = "/banner", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getBanners() {
        // Retrieve the list of banners from the database
        List<Banner> banners = bannerService.getAllBanners();

        // Map each banner to match the required format
        List<Map<String, Object>> bannerList = banners.stream().map(banner -> {
            Map<String, Object> bannerData = new HashMap<>();
            bannerData.put("banner_name", banner.getBannerName());
            bannerData.put("banner_image", banner.getBannerImage());
            bannerData.put("description", banner.getDescription());
            return bannerData;
        }).collect(Collectors.toList());

        // Prepare the response data according to the specified format
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", 0);
        responseData.put("message", "Sukses");
        responseData.put("data", bannerList);

        // Respond with the banner data
        return ResponseEntity.ok(new ApiResponse(0, "Sukses", responseData));
    }

    // Endpoint Services
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "JavaInUseSecurityScheme")
    @GetMapping(value = "/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getServices(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        // Check if the Authorization header is present and valid
        if (!isValidAuthHeader(authHeader)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Extract the token from the Authorization header
        String token = authHeader.substring(7);

        // Check if the token is valid
        if (!jwtUtil.isTokenValid(token)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Retrieve the list of services from the database
        List<Service> services = serviceService.getAllServices();

        // Check if services are retrieved
        if (services.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(0, "Tidak ada layanan", null)); // Handle empty services
        }

        // Map each service to match the required format
        List<Map<String, Object>> serviceList = services.stream().map(service -> {
            Map<String, Object> serviceData = new HashMap<>();
            serviceData.put("service_code", service.getServiceCode());
            serviceData.put("service_name", service.getServiceName());
            serviceData.put("service_icon", service.getServiceIcon());
            serviceData.put("service_tariff", service.getServiceTariff());
            return serviceData;
        }).collect(Collectors.toList());

        // Prepare the response data
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", 0);
        responseData.put("message", "Sukses");
        responseData.put("data", serviceList); // Use serviceList instead of empty data

        // Respond with the service data
        return ResponseEntity.ok(new ApiResponse(0, "Sukses", responseData));
    }

    // Endpoint balance

    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "JavaInUseSecurityScheme")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ApiResponse> getBalance(HttpServletRequest request) {
        // Validate the Authorization header
        String authHeader = request.getHeader("Authorization");

        // Validate the Authorization header
        if (!isValidAuthHeader(authHeader)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Extract the token from the Authorization header
        String token = authHeader.substring(7);

        // Validate the token
        if (!jwtUtil.isTokenValid(token)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Extract email from the token
        String email = jwtUtil.extractEmail(token);
        User user = userRepository.findByEmail(email);

        // Check if the user exists
        if (user == null) {
            return createErrorResponse(HttpStatus.NOT_FOUND, 109, "User not found");
        }

        // Get the user's balance
        double balance = user.getBalance(); // Assuming balance is a field in User model

        // Prepare the response data
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("balance", balance);

        // Respond with the user's balance
        return ResponseEntity.ok(new ApiResponse(0, "Get Balance Berhasil", responseData));
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "JavaInUseSecurityScheme")
    @PostMapping(value = "/topup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> topup(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        // Validate the Authorization header
        String authHeader = request.getHeader("Authorization");
        if (!isValidAuthHeader(authHeader)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Extract and validate the token
        String token = authHeader.substring(7);
        if (!jwtUtil.isTokenValid(token)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Extract the email from the token
        String email = jwtUtil.extractEmail(token);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return createErrorResponse(HttpStatus.NOT_FOUND, 109, "User not found");
        }

        // Validate the request body for top-up amount
        Object topUpAmountObj = requestBody.get("top_up_amount");
        if (topUpAmountObj == null || !(topUpAmountObj instanceof Number) || ((Number) topUpAmountObj).doubleValue() < 0) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, 102, "Parameter amount hanya boleh angka dan tidak boleh lebih kecil dari 0");
        }

        double topUpAmount = ((Number) topUpAmountObj).doubleValue();

        //  Update the user's balance and transaction details
        double newBalance = user.getBalance() + topUpAmount;
        user.setBalance(newBalance);
        user.setTransactionType("TOPUP"); // Set transaction type to TOPUP
        user.setDescription("Top up Balance"); // Set static description for the top-up

        // Save updated user balance and transaction details
        userRepository.save(user);

        //  Prepare and return the success response
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("balance", newBalance);
        return ResponseEntity.ok(new ApiResponse(0, "Top Up Balance berhasil", responseData));
    }


    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "JavaInUseSecurityScheme")
    @PostMapping(value = "/transaction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> performTransaction(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {

        // Step 1: Validate Authorization header
        String authHeader = request.getHeader("Authorization");
        if (!isValidAuthHeader(authHeader)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Step 2: Extract and validate JWT token
        String token = authHeader.substring(7);
        if (!jwtUtil.isTokenValid(token)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Step 3: Extract email from token and find user
        String email = jwtUtil.extractEmail(token);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return createErrorResponse(HttpStatus.NOT_FOUND, 109, "User not found");
        }

        // Step 4: Extract service_code from request body
        String serviceCode = (String) requestBody.get("service_code");
        if (serviceCode == null || serviceCode.isEmpty()) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, 102, "Service atau Layanan tidak ditemukan");
        }

        // Step 5: Fetch service details from the database
        Service service = serviceRepository.findByServiceCode(serviceCode);
        if (service == null) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, 102, "Service atau Layanan tidak ditemukan");
        }

        // Step 6: Check if user balance is sufficient for the transaction
        if (user.getBalance() < service.getServiceTariff()) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, 103, "Saldo tidak mencukupi untuk transaksi");
        }

        // Step 7: Deduct the transaction amount and update user balance
        user.setBalance(user.getBalance() - service.getServiceTariff());

        // Create a new Transaction object
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        String invoiceNumber = "INV-" + LocalDate.now().toString().replace("-", "") + "-" + "001";
        transaction.setInvoiceNumber(invoiceNumber);
        transaction.setTransactionType("PAYMENT");
        transaction.setDescription(service.getServiceName());
        transaction.setTotalAmount(service.getServiceTariff());
        transaction.setCreatedOn(ZonedDateTime.now(ZoneId.of("UTC")));

        // Save the new transaction to the database
        transactionRepository.save(transaction);

        // Save updated user data with transaction information
        userRepository.save(user);

        // Prepare response data
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("invoice_number", invoiceNumber);
        responseData.put("service_code", service.getServiceCode());
        responseData.put("service_name", service.getServiceName());
        responseData.put("transaction_type", transaction.getTransactionType());
        responseData.put("total_amount", transaction.getTotalAmount());
        responseData.put("created_on", transaction.getCreatedOn().format(DateTimeFormatter.ISO_INSTANT));

        // Step 10: Return the success response
        return ResponseEntity.ok(new ApiResponse(0, "Transaksi berhasil", responseData));
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name = "JavaInUseSecurityScheme")
    @GetMapping(value = "/transaction/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getTransactionHistory(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "3") int limit) {

        // Validate Authorization header
        String authHeader = request.getHeader("Authorization");

        if (!isValidAuthHeader(authHeader)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Extract and validate JWT token
        String token = authHeader.substring(7);

        if (!jwtUtil.isTokenValid(token)) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, 108, "Token tidak valid atau kadaluwarsa");
        }

        // Extract email from token and find user
        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return createErrorResponse(HttpStatus.NOT_FOUND, 109, "User not found");
        }

        // Fetch transactions with pagination
        Page<Transaction> transactionPage = transactionService.getTransactionsByUserId(user.getId(), offset, limit);

        // Build transaction records with pagination
        List<Map<String, Object>> records = transactionPage.getContent().stream()
                .map(transaction -> {
                    Map<String, Object> transactionData = new HashMap<>();
                    transactionData.put("invoice_number", transaction.getInvoiceNumber());
                    transactionData.put("transaction_type", transaction.getTransactionType());
                    transactionData.put("description", transaction.getDescription());
                    transactionData.put("total_amount", transaction.getTotalAmount());
                    // Format created_on to ISO 8601 string
                    transactionData.put("created_on", transaction.getCreatedOn().withZoneSameInstant(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_INSTANT));
                    return transactionData;
                })
                .collect(Collectors.toList());

        // Prepare response data with offset, limit, and records
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("offset", offset);
        responseData.put("limit", limit);
        responseData.put("records", records);


        // Return success response
        return ResponseEntity.ok(new ApiResponse(0, "Get History Berhasil", responseData));
    }

}