package Order.DTOs;

import java.net.URI;

/**
 * Created by Aismael on 11.04.2017.
 */
public class ServiceURIDTO {
    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public ServiceURIDTO(URI uri) {
        this.uri = uri;
    }

    private URI uri=null;
}
