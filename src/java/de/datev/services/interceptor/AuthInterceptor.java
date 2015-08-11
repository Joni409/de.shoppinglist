package de.datev.services.interceptor;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author TG00023
 */
@Provider
@PreMatching
public class AuthInterceptor implements ContainerRequestFilter
{
    @Override
    public void filter (ContainerRequestContext context) throws IOException
    {
            String headerValue = context.getHeaderString("X-AUTH");

            if ((headerValue == null) || (!headerValue.equals("1234")))
            {
                    //keinen Auth-header gefunden oder falscher Wert in Auth-header
                    ResponseBuilder responseBuilder = Response.status(Status.EXPECTATION_FAILED);
                    Response response = responseBuilder.build();
                    context.abortWith(response);
            }
    }
}
