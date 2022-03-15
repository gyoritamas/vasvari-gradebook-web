package org.vasvari.gradebookweb.utilTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.vasvari.gradebookweb.business.util.JwtTokenUtil;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JwtTokenUtilTests {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static final String VALID_TOKEN_OF_ADMIN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY0NjYwNjc5MywiaWF0IjoxNjQ2NTc3OTkzLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9LHsiYXV0aG9yaXR5IjoiY291cnNlOnJlYWQifSx7ImF1dGhvcml0eSI6ImNvdXJzZTp3cml0ZSJ9LHsiYXV0aG9yaXR5Ijoic3R1ZGVudDpyZWFkIn0seyJhdXRob3JpdHkiOiJzdHVkZW50OndyaXRlIn1dfQ.NqjQ4tCB5XVF_9NVYp_qzyu1WDByZllU206Mjb4x06Gm4ud1BlD6uGV2t1DGkJTyZEuLigHwZaQbjNdtdQHDKQ";
    private static final String VALID_TOKEN_OF_TEACHER = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZWFjaGVyIiwiZXhwIjoxNjQ2NjA5NTEwLCJpYXQiOjE2NDY1ODA3MTAsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX1RFQUNIRVIifSx7ImF1dGhvcml0eSI6ImNvdXJzZTpyZWFkIn0seyJhdXRob3JpdHkiOiJzdHVkZW50OnJlYWQifV19.Hv7oKqgc3EoB-YMYO4Lr0o5gqamkNOT39rx7orM8Y-VpFrUXKL6nhEdNd5bqD_YIZ9MX2Qt9EpT5PdLVWet3kA";
    private static final String VALID_TOKEN_OF_STUDENT = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkZW50IiwiZXhwIjoxNjQ2NjA5NjQ3LCJpYXQiOjE2NDY1ODA4NDcsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX1NUVURFTlQifV19.qBvOCQz9AniAtdmJmNVjk2DAYawasnu0vNVRMJnAGgr8IpGfgXjUnqMe3rKXrhTqdUIkYZBknvnjGSAaCBw1uA";


    @Test
    public void testGetUserNameFromToken_shouldReturnAdmin() {
        String username = jwtTokenUtil.getUsernameFromToken(VALID_TOKEN_OF_ADMIN);

        assertThat(username).isEqualTo("admin");
    }

    @Test
    public void testGetUserRoleFromToken_shouldReturnAdmin() {
        String role = jwtTokenUtil.getUserRoleFromToken(VALID_TOKEN_OF_ADMIN);

        assertThat(role).isEqualTo("ADMIN");
    }

    @Test
    public void testGetUserNameFromToken_shouldReturnTeacher() {
        String username = jwtTokenUtil.getUsernameFromToken(VALID_TOKEN_OF_TEACHER);

        assertThat(username).isEqualTo("teacher");
    }

    @Test
    public void testGetUserRoleFromToken_shouldReturnTeacher() {
        String role = jwtTokenUtil.getUserRoleFromToken(VALID_TOKEN_OF_TEACHER);

        assertThat(role).isEqualTo("TEACHER");
    }

    @Test
    public void testGetUserNameFromToken_shouldReturnStudent() {
        String username = jwtTokenUtil.getUsernameFromToken(VALID_TOKEN_OF_STUDENT);

        assertThat(username).isEqualTo("student");
    }

    @Test
    public void testGetUserRoleFromToken_shouldReturnStudent() {
        String role = jwtTokenUtil.getUserRoleFromToken(VALID_TOKEN_OF_STUDENT);

        assertThat(role).isEqualTo("STUDENT");
    }

}
