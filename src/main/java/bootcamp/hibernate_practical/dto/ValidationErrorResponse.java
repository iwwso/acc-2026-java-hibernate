package bootcamp.hibernate_practical.dto;

import java.time.Instant;
import java.util.Map;

/**
 * Body returned when request validation fails: one entry per rejected field.
 *
 * @author Karyna Litvinova
 */
public record ValidationErrorResponse(int status, String error, Map<String, String> fieldErrors, Instant timestamp) {
}