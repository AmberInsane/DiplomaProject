package com.tms.stankevich.validator;

import com.tms.stankevich.domain.movie.Session;
import com.tms.stankevich.domain.movie.Ticket;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class TicketFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Ticket.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Ticket ticket = (Ticket) target;
        System.out.println(ticket);
        int ticketCount = ticket.getUsersFor().size();
        if (ticketCount == 0) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usersFor", "NotEmpty.ticketForm.usersFor");
        } else {
            Session session = ticket.getSession();
            if (session.getHall().getCapacity() - session.getTicketsSold() < ticketCount) {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usersFor", "Valid.ticketForm.ticketCount");
            }
        }
    }

}