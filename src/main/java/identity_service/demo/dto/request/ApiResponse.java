package identity_service.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Nhung field nao NULL se khong hien thi tu ket qua tra ve
public class ApiResponse <T>{

    private boolean success;
    private T result;
}
