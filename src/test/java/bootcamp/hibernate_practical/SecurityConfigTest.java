package bootcamp.hibernate_practical;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Checks the access rules from SecurityConfig: reading needs a login,
 * writing needs the ADMIN role.
 *
 * @author Karyna Litvinova
 */
@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    private static final String NEW_BOOK = """
            {
              "title": "Clean Code",
              "author": "Robert Martin",
              "genre": "Software Engineering",
              "publicationYear": 2008
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getWithoutCredentialsIsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getWithWrongPasswordIsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/books").with(httpBasic("user", "wrong")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void userCanRead() throws Exception {
        mockMvc.perform(get("/api/books").with(httpBasic("user", "user")))
                .andExpect(status().isOk());
    }

    @Test
    void adminCanRead() throws Exception {
        mockMvc.perform(get("/api/books").with(httpBasic("admin", "admin")))
                .andExpect(status().isOk());
    }

    @Test
    void userCannotCreate() throws Exception {
        mockMvc.perform(post("/api/books")
                        .with(httpBasic("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(NEW_BOOK))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanCreate() throws Exception {
        mockMvc.perform(post("/api/books")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(NEW_BOOK))
                .andExpect(status().isCreated());
    }
}