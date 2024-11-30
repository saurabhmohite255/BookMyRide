package com.codeBySaurabh.bookRide.BookRideApp.services;



public interface EmailSenderService {

     void sendEmail(String toEmail, String subject, String body);

     void sendEmail(String[] toEmail, String subject, String body);

}
