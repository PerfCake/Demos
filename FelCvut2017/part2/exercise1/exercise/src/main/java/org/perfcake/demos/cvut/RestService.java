/*
 * -----------------------------------------------------------------------\
 * PerfCake
 *  
 * Copyright (C) 2010 - 2016 the original author or authors.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -----------------------------------------------------------------------/
 */
package org.perfcake.demos.cvut;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author <a href="mailto:marvenec@gmail.com">Martin Večeřa</a>
 */
@Path("/service")
@RequestScoped
public class RestService {

   private static List<String> correlations = new ArrayList<>();

   @GET
   @Path("/hello/{id:[0-9][0-9]*}")
   @Produces(MediaType.APPLICATION_JSON)
   public String getSerialNumbers(@PathParam("id") final String id) {
      return "Hello: " + id;
   }

   @GET
   @Path("/forward")
   public synchronized Response forwardService(@HeaderParam("perfcake.correlation.id") String correlationId, @HeaderParam("targetUrl") String targetUrl) {
      correlations.add(correlationId);

      if (correlations.size() > 3) {
         final Invocation.Builder b = ClientBuilder.newClient().target(targetUrl).request()
               .header("Access-Control-Request-Method", "POST");
         correlations.forEach(c -> b.header("perfcake.correlation.id", c));
         b.get();
         correlations = new ArrayList<>();
      }

      return Response.status(200).entity("It is allright!").build();
   }
}
