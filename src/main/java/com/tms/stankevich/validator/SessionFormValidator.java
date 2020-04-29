package com.tms.stankevich.validator;

import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.domain.movie.Session;
import com.tms.stankevich.service.MovieServiceImpl;
import com.tms.stankevich.service.SessionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Component
public class SessionFormValidator implements Validator {

    @Autowired
    SessionServiceImpl sessionService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Session.class.equals(clazz);
    }

    @Value("${session.time.before.hour}")
    private int hoursBefore;

    @Value("${session.time.between.min}")
    private int minutesBetween;

    @Override
    public void validate(Object target, Errors errors) {
        Session session = (Session) target;
        LocalDateTime startTime;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateFormatJSP", "NotEmpty.sessionForm.dateTime");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.sessionForm.price");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hall", "NotEmpty.sessionForm.hall");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "movie", "NotEmpty.sessionForm.movie");

        try {
            startTime = LocalDateTime.parse(session.getDateFormatJSP().replace("T", " "), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            session.setStartTime(startTime);

            if (LocalDateTime.now().isAfter(startTime.minusHours(hoursBefore))) {
                errors.rejectValue("dateFormatJSP", "Valid.sessionForm.dateTimeBefore");
            } else {
                LocalDateTime dateTimeEnd;

                Optional<Session> nextSession = sessionService.getNextSession(startTime, session.getHall());
                if (nextSession.isPresent()) {
                    dateTimeEnd = session.getStartTime().plusMinutes(session.getMovie().getTimeLength());
                    if (dateTimeEnd.isAfter(nextSession.get().getStartTime())) {
                        errors.rejectValue("dateFormatJSP", "Valid.sessionForm.dateTimeSessionNext");
                    }
                }

                Optional<Session> prevSession = sessionService.getPrevSession(startTime, session.getHall());
                if (prevSession.isPresent()) {
                    dateTimeEnd = prevSession.get().getStartTime().plusMinutes(prevSession.get().getMovie().getTimeLength());
                    if (dateTimeEnd.plusMinutes(minutesBetween).isAfter(startTime)) {
                        errors.rejectValue("dateFormatJSP", "Valid.sessionForm.dateTimeSessionPrev");
                    }
                }
            }

        } catch (
                Exception e) {
            errors.rejectValue("dateFormatJSP", "Valid.sessionForm.dateTime");
        }
    }
}