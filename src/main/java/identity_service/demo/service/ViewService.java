package identity_service.demo.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ViewService {

    @PreAuthorize("hasAuthority('CREATE_POST')")
    public String view() {
        return "Hello World";
    }
}
