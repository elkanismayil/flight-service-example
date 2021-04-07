package com.example.tx.service;

import com.example.tx.dto.FlightBookingAcknowledgement;
import com.example.tx.dto.FlightBookingRequest;
import com.example.tx.entity.PassengerInfo;
import com.example.tx.entity.PaymentInfo;
import com.example.tx.repository.PassengerInfoRepository;
import com.example.tx.repository.PaymentInfoRepository;
import com.example.tx.utils.PaymentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FlightBookingService {

    @Autowired
    private PassengerInfoRepository passengerInfoRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public FlightBookingAcknowledgement bookFlightTicket(FlightBookingRequest request) {
        PassengerInfo passengerInfo = request.getPassengerInfo();
        passengerInfoRepository.save(passengerInfo);

        PaymentInfo paymentInfo = request.getPaymentInfo();

        PaymentUtils.validateCreditLimit(paymentInfo.getAccountNo(), passengerInfo.getFare());

        paymentInfo.setPassengerId(passengerInfo.getPId());
        paymentInfo.setAmount(passengerInfo.getFare());

        paymentInfoRepository.save(paymentInfo);

        return new FlightBookingAcknowledgement(
                "SUCCESS",
                passengerInfo.getFare(),
                UUID.randomUUID().toString().split("-")[0],
                passengerInfo
        );
    }
}
