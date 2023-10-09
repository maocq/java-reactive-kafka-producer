package co.com.bancolombia.model.information;
import lombok.*;

@Builder(toBuilder = true)
public record Information(int id, String message) { }
