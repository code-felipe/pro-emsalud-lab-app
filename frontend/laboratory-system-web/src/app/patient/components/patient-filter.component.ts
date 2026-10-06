export interface PatientFilter {
  search?: string;
  gender?: string;
  city?: string;
  dateOfBirth?: string; // LocalDate → "YYYY-MM-DD"
  createdAtAfter?: string; // Instant → ISO 8601
  createdAtBefore?: string;
  updatedAtAfter?: string;
  updatedAtBefore?: string;
}
