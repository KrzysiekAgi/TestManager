package io.github.krzysiekagi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.krzysiekagi.controller.TestCaseController;
import io.github.krzysiekagi.model.domain.TestCase;
import io.github.krzysiekagi.model.domain.TestStatus;
import io.github.krzysiekagi.service.TestCaseService;
import org.junit.Test;
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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TestCaseController.class)

public class TestCaseControllerIntegrationTest {

    TestCase test = new TestCase("test");
    TestCase test2 = new TestCase("test2");
    List<TestCase> allTests = List.of(test, test2);
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TestCaseService testCaseService;

    @Test
    public void givenTests_whenGetAllTests_thenReturnTests() throws Exception {

        given(testCaseService.getTests()).willReturn(allTests);

        MvcResult result = mvc.perform(get("/tests")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(allTests.size())))
                .andReturn();

        List<TestCase> actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(actual.get(0)).isEqualTo(test);
        assertThat(actual.get(1)).isEqualTo(test2);
    }

    @Test
    public void givenTestId_whenGetTestById_thenReturnTestCase() throws Exception {
        Long id = 1L;
        given(testCaseService.getTestById(id)).willReturn(test);

        mvc.perform(get("/tests/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.testName", is("test")));
    }

    @Test
    public void givenCorrectTestStatus_whenPutStatus_thenStatus200() throws Exception {
        Long id = 1L;
        TestCase passingTest = new TestCase("passingTest");
        passingTest.setStatus(TestStatus.PASSING);
        given(testCaseService.updateStatus(id, "Passing")).willReturn(passingTest);

        mvc.perform(put("/tests/{id}/status", id)
                .contentType(MediaType.APPLICATION_JSON)
                        .content("Passing"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("PASSING")));
    }

    @Test
    public void givenName_whenPutName_thenStatus200() throws Exception {
        String newName = "renamedTest";
        Long id = 1L;
        TestCase renamedTest = new TestCase(newName);
        renamedTest.setStatus(TestStatus.PASSING);
        given(testCaseService.updateTestName(id, newName)).willReturn(renamedTest);

        mvc.perform(put("/tests/{id}/name", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.testName", is(newName)));
    }

    @Test
    public void givenName_whenPostTest_thenStatus201() throws Exception {
        String newName = "newTest";
        TestCase newTest = new TestCase(newName);
        given(testCaseService.addTest(newName)).willReturn(newTest);

        mvc.perform(post("/tests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newName))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.testName", is(newName)))
                .andExpect(jsonPath("$.status", is(TestStatus.UNDEFINED.name())));
    }

    @Test
    public void givenId_whenDeleteTest_thenStatus200() throws Exception {
        Long id = 1L;
        given(testCaseService.deleteTest(id)).willReturn(String.valueOf(id));

        mvc.perform(delete("/tests/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(id.intValue())));
    }
}
