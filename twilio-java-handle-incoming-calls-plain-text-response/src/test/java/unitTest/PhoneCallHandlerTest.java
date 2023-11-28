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
                .andExpect(content().xml("<Response><Gather input='dtmf speech' hints='one, two' numDigits='1' action='/gatherResult'><Say>Hello. Press or say One for a joke, or Two for some music.</Say></Gather><Say>Thanks for calling, have a great day</Say></Response>"));
    }

    @Test
    public void testHandleGatherResultWithDigitOne() throws Exception {
        mockMvc.perform(post("/gatherResult").param("Digits", "1"))
                .andExpect(status().isOk())
                .andExpect(content().xml("<Response><Say>How do you know if a bee is using your phone? The line will be buzzy.</Say></Response>"));
    }

    @Test
    public void testHandleGatherResultWithDigitTwo() throws Exception {
        mockMvc.perform(post("/gatherResult").param("Digits", "2"))
                .andExpect(status().isOk())
                .andExpect(content().xml("<Response><Play>http://demo.twilio.com/docs/classic.mp3</Play></Response>"));
    }

    @Test
    public void testHandleGatherResultWithInvalidDigit() throws Exception {
        mockMvc.perform(post("/gatherResult").param("Digits", "3")) // Proporciona un dígito incorrecto (3 en este ejemplo)
                .andExpect(status().isOk())
                .andExpect(content().xml("<Response><Say>Sorry, that's not a valid option. Please try again.</Say>" +
                        "<Gather input='dtmf speech' hints='one, two' numDigits='1' action='/gatherResult'><Say>Hello. Press or say One for a joke, or Two for some music.</Say></Gather></Response>"));
    }
}
