package org.acme;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import org.acme.model.Booking;

import java.util.Date;

@RegisterAiService(
        tools = Tools.class
)
public interface BookingService {

    @SystemMessage("You are a robot that handles bookings for a hotel.")
    @UserMessage("Create a booking for {name} with email {email} from {startDate} to {endDate}." +
            "Don't send any confirmation emails to the customer, only return the object representing the booking.")
    Booking createBooking(String name, String email, Date startDate, Date endDate);

    @UserMessage("Send a confirmation the previous booking to email {toAddress}. The subject will be 'Booking confirmation'.")
    String sendEmail(String toAddress);

}
