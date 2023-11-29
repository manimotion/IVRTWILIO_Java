package unitTest;

import com.example.twilio.calls.TwilioJavaHandlePhoneCallsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TwilioJavaHandlePhoneCallsApplication.class)
@AutoConfigureMockMvc
public class PhoneCallHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHandleIncomingCall() throws Exception {
        mockMvc.perform(post("/"))
                .andExpect(status().isOk())
                .andExpect(content().xml("<Response><Gather action=\"/gatherResult\" hints=\"one, two, three\" input=\"dtmf speech\" numDigits=\"1\"><Say>Hello. Press or say One for a joke, Two for some music, or Three for information about our products.</Say></Gather><Say>Thanks for calling, have a great day</Say></Response>"));
    }

    @Test
    public void testHandleGatherResultWithDigitOne() throws Exception {
        mockMvc.perform(post("/gatherResult").param("Digits", "1"))
                .andExpect(status().isOk())
                .andExpect(content().xml("<Response><Say>How do you know if a bee is using your phone? The line will be buzzy.</Say><Say>Thanks for laughing with us! Have a great day!</Say></Response>"));
    }

    @Test
    public void testHandleGatherResultWithDigitTwo() throws Exception {
        mockMvc.perform(post("/gatherResult").param("Digits", "2"))
                .andExpect(status().isOk())
                .andExpect(content().xml("<Response><Play>http://demo.twilio.com/docs/classic.mp3</Play><Say>Thanks for listening to our music! Have a great day!</Say></Response>"));
    }

    @Test
    public void testHandleGatherResultWithDigitThree() throws Exception {
        mockMvc.perform(post("/gatherResult").param("Digits", "3"))
                .andExpect(status().isOk())
                .andExpect(content().xml("<Response><Say>Press or say One for information about appliances, Two for customer service.</Say><Gather action=\"/productSubMenu\" hints=\"one, two\" input=\"dtmf speech\" numDigits=\"1\"></Gather></Response>"));
    }

    @Test
    public void testHandleProductSubMenuWithDigitOne() throws Exception {
        mockMvc.perform(post("/productSubMenu").param("Digits", "1"))
                .andExpect(status().isOk())
                .andExpect(content().xml("<Response><Say>Thanks for your interest in our appliances. We offer a wide range of high-quality products.</Say></Response>"));
    }

    @Test
    public void testHandleProductSubMenuWithDigitTwo() throws Exception {
        mockMvc.perform(post("/productSubMenu").param("Digits", "2"))
                .andExpect(status().isOk())
                .andExpect(content().xml("<Response><Say>Our customer service team is here to assist you. Please hold for the next available representative.</Say></Response>"));
    }

    @Test
    public void testHandleGatherResultWithInvalidDigit() throws Exception {
        mockMvc.perform(post("/gatherResult").param("Digits", "4"))
                .andExpect(status().isOk())
                .andExpect(content().xml("<Response><Say>Sorry, that's not a valid option. Please try again.</Say><Gather action=\"/gatherResult\" hints=\"one, two, three\" input=\"dtmf speech\" numDigits=\"1\"><Say>Hello. Press or say One for a joke, Two for some music, or Three for information about our products.</Say></Gather></Response>"));
    }
}

