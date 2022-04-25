package com.example.slabiak.appointmentscheduler.tests;

import com.example.slabiak.appointmentscheduler.AppointmentSchedulerApplication;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = WorkController.class)
@SpringBootTest( classes = AppointmentSchedulerApplication.class)
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc(addFilters = false)
@WebAppConfiguration
public class BlackBoxTesting {
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    private void setup () {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Nested
    class WorkTest {
        String endPoint = "works";
        private String[] htmlBuilder(int id, String name, int duration, double price, boolean editable,
                                     String target, String desc) {
            String[] html = new String[5];
            html[0] = String.format("<td><span>%s</span></td>", name);
            html[1] = String.format("<td><span>%s</span></td>", target);
            html[2] = String.format("<td><span>%.1f PLN</span></td>", price);
            html[3] = String.format("<td><span>%d min</span></td>", duration);
            html[4] = String.format("<td><span>%d</span></td>", id);
            return html;
        }

        //Dont know how to test create new work .
        @Test
        @Order(1)
        public void getAllWork() throws Exception {
            String uri = "/" + endPoint + "/all";
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
            String content = mvcResult.getResponse().getContentAsString();
            String[] html = htmlBuilder(1, "English lesson", 60, 100.00, true
                , "retail", "This is english lesson with duration 60 minutes and price 100 pln");
            for (String rows : html) {
                assertTrue(content.contains(rows));
            }
        }

        @Test
        @Order(3)
        @WithMockUser(roles = {"ADMIN"})
        public void deleteWork() throws Exception {
            String uri = "/" + endPoint + "/delete";
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).with(httpBasic("admin", "qwerty123"))
                    .param("workId", String.valueOf(1))
                    .accept(MediaType.APPLICATION_JSON))
                .andReturn();
            String content = mvcResult.getResponse().getContentAsString();

            String[] html = htmlBuilder(1, "English lesson", 60, 100.00, true
                , "retail", "This is english lesson with duration 60 minutes and price 100 pln");
            for (String rows : html) {
                assertFalse(content.contains(rows));
            }
        }
    }

    @Nested
    class ProviderTest {
        String endPoint = "providers";
        private String[] htmlBuilder(String nameEmail,String appoinments, int works, int action) {
            String[] html = new String[4];
            html[0] = String.format("<td><span>%s</span></td>", nameEmail);
            html[1] = String.format("<td><span>%s</span></td>", appoinments);
            html[2] = String.format("<td><span>%d</span></td>", works);
            html[3] = String.format("<td><span>%d</span></td>", action);
            return html;
        }

        //Dont know how to test create new work .
        @Test
        @Order(1)
        @WithMockUser(roles = {"ADMIN"})
        public void getAllProviders() throws Exception {
            String uri = "/" + endPoint + "/all";
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
            String content = mvcResult.getResponse().getContentAsString();
            System.out.println(content);
            String[] html = htmlBuilder("null null", "", 0, 1);
            for (String rows : html) {
                assertTrue(content.contains(rows));
            }
        }

        @Test
        @Order(3)
        @WithMockUser(roles = {"ADMIN"})
        public void deleteProviders() throws Exception {
            String uri = "/" + endPoint + "/delete";
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).with(httpBasic("admin", "qwerty123"))
                    .param("providerId", String.valueOf(1))
                    .accept(MediaType.APPLICATION_JSON))
                .andReturn();
            String content = mvcResult.getResponse().getContentAsString();

            String[] html = htmlBuilder("null null", "", 0, 1);
            for (String rows : html) {
                assertFalse(content.contains(rows));
            }
        }
    }

    @Nested
    @Ignore //Dont know how to sign in user. Current User is NUll
    class NotificationsTest {
        String endPoint = "notifications";
        private String[] htmlBuilder(String nameEmail,String appoinments, int works, int action) {
            String[] html = new String[4];
            html[0] = String.format("<td><span>%s</span></td>", nameEmail);
            html[1] = String.format("<td><span>%s</span></td>", appoinments);
            html[2] = String.format("<td><span>%d</span></td>", works);
            html[3] = String.format("<td><span>%d</span></td>", action);
            return html;
        }

        //Dont know how to test create new work .
        @Test
        @Order(1)
        //@WithMockUser(username ="admin", password = "qwerty123", roles = {"ADMIN"})
        public void getAllNotifications() throws Exception {
            String uri = "/" + endPoint;
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
            String content = mvcResult.getResponse().getContentAsString();
            System.out.println(content);
            String[] html = htmlBuilder("null null", "", 0, 1);
            for (String rows : html) {
                assertTrue(content.contains(rows));
            }
        }

        @Test
        @Order(3)
        @WithMockUser(roles = {"ADMIN"})
        public void readNotifications() throws Exception {
            String uri = "/" + endPoint + "/delete";
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).with(httpBasic("admin", "qwerty123"))
                    .param("workId", String.valueOf(1))
                    .accept(MediaType.APPLICATION_JSON))
                .andReturn();
            String content = mvcResult.getResponse().getContentAsString();

            String[] html = htmlBuilder("null null", "", 0, 1);
            for (String rows : html) {
                assertFalse(content.contains(rows));
            }
        }
    }

    @Nested
    @Ignore //currentUser is null
    class HomeTest {
        @Test
        @Order(1)
        @WithMockUser(username ="admin", password = "qwerty123", roles = {"ADMIN"})
        public void goHome() throws Exception {
            String uri = "/";
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
            String content = mvcResult.getResponse().getContentAsString();

            assertTrue(content.contains("<div id='calender'"));
        }
    }

    @Nested
    class CustomersTest {
        String endPoint = "customers";
        private String[] htmlBuilder(String nameEmail,String type, String appoinments, int action) {
            String[] html = new String[4];
            html[0] = String.format("<td><span>%s</span></td>", nameEmail);
            html[1] = String.format("<td><span>%s</span></td>", type);
            html[2] = String.format("<td><span>%s</span></td>", appoinments);
            html[3] = String.format("<td><span>%d</span></td>", action);
            return html;
        }

        //Dont know how to test create new work .
        @Test
        @Order(1)
        public void getAllWork() throws Exception {
            String uri = "/" + endPoint + "/all";
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
            String content = mvcResult.getResponse().getContentAsString();
            String[] html = htmlBuilder("null null", "retail", "", 0);
            for (String rows : html) {
                assertTrue(content.contains(rows));
            }
        }

        @Test
        @Order(3)
        @WithMockUser(roles = {"ADMIN"})
        public void deleteWork() throws Exception {
            String uri = "/" + endPoint + "/delete";
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).with(httpBasic("admin", "qwerty123"))
                    .param("customerId", String.valueOf(1))
                    .accept(MediaType.APPLICATION_JSON))
                .andReturn();
            String content = mvcResult.getResponse().getContentAsString();

            String[] html = htmlBuilder("null null", "retail", "", 0);
            for (String rows : html) {
                assertFalse(content.contains(rows));
            }
        }
    }

    @Nested
    @Ignore //currentUser is null
    class AppointmentTest {

    }

    @Nested
    @Ignore //currentUSer is null
    class AjaxTest {

    }

}
