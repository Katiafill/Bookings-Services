package ru.katiafill.bookings.flight.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import ru.katiafill.bookings.shared.exception.ResourceNotFoundException;
import ru.katiafill.bookings.shared.exception.ResponseError;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 404) {
            String message =  getResponseErrorMessage(response).orElse(s);
            return new ResourceNotFoundException(message);
        }
        return new Exception("Error occurred while calling " + s);
    }

    private static Optional<String> getResponseErrorMessage(Response response) {
        try (InputStream inputStream = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            ResponseError responseError = mapper.readValue(inputStream, ResponseError.class);
            return Optional.of(responseError.getMessage());
        } catch (IOException e) {
            return Optional.empty();
        }
    }


}
