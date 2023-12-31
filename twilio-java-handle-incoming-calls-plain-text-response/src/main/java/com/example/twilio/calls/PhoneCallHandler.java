package com.example.twilio.calls;

import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Play;
import com.twilio.twiml.voice.Say;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class PhoneCallHandler {

    private static final String MENU_OPTIONS = "Hello. Press or say One for a joke, Two for some music, or Three for information about our products.";
    private static final String GOODBYE = "Thanks for calling, have a great day";
    private static final String GOODBYE_JOKE = "Thanks for laughing with us! Have a great day!";
    private static final String GOODBYE_MUSIC = "Thanks for listening to our music! Have a great day!";
    private static final String GOODBYE_INFO = "Thanks for inquiring about our products! Have a great day!";


    @PostMapping(value = "/", produces = "application/xml")
    @ResponseBody
    public String handleIncomingCall() {

        return new VoiceResponse.Builder()
                .gather(new Gather.Builder()
                        .say(new Say.Builder(MENU_OPTIONS).build())
                        .inputs(Arrays.asList(Gather.Input.DTMF, Gather.Input.SPEECH))
                        .hints("one, two, three")
                        .numDigits(2)
                        .action("/gatherResult")
                        .build())
                .say(new Say.Builder(GOODBYE).build())
                .build().toXml();
    }

    private static final String JOKE = "How do you know if a bee is using your phone? The line will be buzzy.";
    private static final String MUSIC_URL = "http://demo.twilio.com/docs/classic.mp3";



    @PostMapping(value = "/gatherResult", produces = "application/xml")
    @ResponseBody
    public String handleGatherResult(
            @RequestParam(value = "Digits", required = false) String digits,
            @RequestParam(value = "SpeechResult", required = false) String speechResult) {

        if ("1".equals(digits) || "one".equals(speechResult)) {
            return new VoiceResponse.Builder()
                    .say(new Say.Builder(JOKE).build())
                    .say(new Say.Builder(GOODBYE_JOKE).build())
                    .build().toXml();
        }

        if ("2".equals(digits) || "two".equals(speechResult)) {
            return new VoiceResponse.Builder()
                    .play(new Play.Builder(MUSIC_URL).build())
                    .say(new Say.Builder(GOODBYE_MUSIC).build())
                    .build().toXml();
        }

        if ("3".equals(digits) || "three".equals(speechResult)) {
            return new VoiceResponse.Builder()
                    .say(new Say.Builder("Thank you for your interest in our products. Here's some information...").build())
                    .say(new Say.Builder(GOODBYE_INFO).build())
                    .build().toXml();
        }


        return new VoiceResponse.Builder()
                .say(new Say.Builder("Sorry, that's not a valid option. Please try again.").build())
                .gather(new Gather.Builder()
                        .say(new Say.Builder(MENU_OPTIONS).build())
                        .inputs(Arrays.asList(Gather.Input.DTMF, Gather.Input.SPEECH))
                        .hints("one, two, three")
                        .numDigits(1)
                        .action("/gatherResult")
                        .build())
                .build().toXml();
    }

}