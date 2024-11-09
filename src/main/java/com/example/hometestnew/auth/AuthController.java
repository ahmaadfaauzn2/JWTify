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
