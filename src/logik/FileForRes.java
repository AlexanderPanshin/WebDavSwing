package logik;

import com.github.sardine.DavResource;
import com.github.sardine.model.Response;

import javax.xml.namespace.QName;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FileForRes extends DavResource {

    public FileForRes(Response response) throws URISyntaxException {
        super(response);
    }

    public FileForRes(String href, Date creation, Date modified, String contentType, Long contentLength, String etag, String displayName, String lockToken, List<QName> resourceTypes, String contentLanguage, List<QName> supportedReports, Map<QName, String> customProps) throws URISyntaxException {
        super(href, creation, modified, contentType, contentLength, etag, displayName, lockToken, resourceTypes, contentLanguage, supportedReports, customProps);
    }

    @Override
    public String toString() {
        return super.getName();
    }
}
