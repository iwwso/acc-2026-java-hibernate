package bootcamp.hibernate_practical.dto;

import java.time.Instant;

/**
 * Body returned for a failed request.
 *
 * @author Karyna Litvinova
 */
public record ErrorResponse(int status, String error, String message, Instant timestamp) {
}