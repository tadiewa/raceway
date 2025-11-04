/**
 * @author : tadiewa
 * date: 10/17/2025
 */


package com.records.exception;

public class RacewayDataNotFoundException extends RacewayDataException {
    public RacewayDataNotFoundException(String s, Exception e) {
        super();
    }
    public RacewayDataNotFoundException(String message) {
        super(message);
    }

    public RacewayDataNotFoundException(Long id) {
        super("Raceway data not found with id: " + id);
    }


}
