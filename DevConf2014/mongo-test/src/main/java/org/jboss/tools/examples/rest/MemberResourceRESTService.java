package org.jboss.tools.examples.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;

import org.jboss.tools.examples.model.Member;
import org.jboss.tools.examples.service.MemberRegistration;

/**
 * JAX-RS Example
 * 
 * This class produces a RESTful service to read the contents of the members
 * table.
 */
@Path("/members")
@RequestScoped
public class MemberResourceRESTService {
   public static List<String> members = new CopyOnWriteArrayList<>();

	@Inject
	private MemberRegistration memberRegistration;

	@GET
	@Produces("text/xml")
	public List<Member> listAllMembers() {
		return memberRegistration.listAllMembers();
	}

	@GET
	@Path("/{id:[0-9a-f][0-9a-f]*}")
	@Produces("text/xml")
	public Member lookupMemberById(@PathParam("id") String id) {
		return memberRegistration.findMemberById(id);
	}

   @PUT
   @Consumes("text/xml")
   public void addMember(Member member) throws Exception {
      members.add(member.toString());
      memberRegistration.register(member);
   }

}
