package com.example.tx.controller;

import com.example.tx.dto.FlightBookingAcknowledgement;
import com.example.tx.dto.FlightBookingRequest;
import com.example.tx.service.FlightBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightBookingController {

    @Autowired
    private FlightBookingService flightBookingService;

    @PostMapping("/book-flight-ticket")
    public FlightBookingAcknowledgement bookFlightTicket(@RequestBody FlightBookingRequest request){
        return flightBookingService.bookFlightTicket(request);
    }
}
