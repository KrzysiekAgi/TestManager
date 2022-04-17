package io.github.krzysiekagi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.krzysiekagi.controller.TestCaseController;
import io.github.krzysiekagi.model.TestCase;
import io.github.krzysiekagi.model.TestStatus;
import io.github.krzysiekagi.service.TestCaseService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TestCaseController.class)

public class TestCaseControllerIntegrationTest {

    TestCase test = new TestCase("test");
    TestCase test1 = new TestCase("test1");
    List<TestCase> allTests = List.of(test, test1);
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TestCaseService testCaseService;

    @Test
    public void getAllTestsShouldReturnTestsFromService() throws Exception {

        given(testCaseService.getTests()).willReturn(allTests);

        MvcResult result = mvc.perform(get("/api/tests")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<TestCase> actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(actual.get(0)).isEqualTo(test);
        assertThat(actual.get(1)).isEqualTo(test1);
    }

    @Test
    public void getTestByIdShouldReturnExactTest() throws Exception {
        given(testCaseService.getTestById(1L)).willReturn(test);

        MvcResult result = mvc.perform(get("/api/tests/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        TestCase actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(actual).isEqualTo(test);
    }
}
