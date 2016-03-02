package es.uvigo.esei.daa.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.uvigo.esei.daa.dao.DAOException;
import es.uvigo.esei.daa.dao.PetDAO;
import es.uvigo.esei.daa.entities.Pet;

/**
 * REST resource for managing people.
 * 
 * @author Miguel Reboiro Jato.
 */
@Path("/pet")
@Produces(MediaType.APPLICATION_JSON)
public class PetResource {
	private final static Logger LOG = Logger.getLogger(PetResource.class.getName());
	
	private final PetDAO dao;
	
	/**
	 * Constructs a new instance of {@link PeopleResource}.
	 */
	public PetResource() {
		this(new PetDAO());
	}
	
	// Needed for testing purposes
	PetResource(PetDAO dao) {
		this.dao = dao;
	}
	
	
	@GET
	@Path("/{id}")
	public Response get(
		@PathParam("id") int id
	) {
		try {
			final Pet pet = this.dao.get(id);
			
			return Response.ok(pet).build();
		} catch (IllegalArgumentException iae) {
			LOG.log(Level.FINE, "Invalid pet id in get method", iae);
			
			return Response.status(Response.Status.BAD_REQUEST)
				.entity(iae.getMessage())
			.build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error getting a pet", e);
			
			return Response.serverError()
				.entity(e.getMessage())
			.build();
		}
	}

	@GET
	public Response list() {
		try {
			return Response.ok(this.dao.list()).build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error listing pet", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@POST
	public Response add(
		@FormParam("name") String name, 
		@FormParam("tipe") String tipe,
		@FormParam("idPeople") int idPeople
	) {
		try {
			final Pet newPet = this.dao.add(name, tipe, idPeople);
			
			return Response.ok(newPet).build();
		} catch (IllegalArgumentException iae) {
			LOG.log(Level.FINE, "Invalid pet id in add method", iae);
			
			return Response.status(Response.Status.BAD_REQUEST)
				.entity(iae.getMessage())
			.build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error adding a pet", e);
			
			return Response.serverError() 
				.entity(e.getMessage())
			.build();
		}
	}

	@PUT
	@Path("/{id}")
	public Response modify(
		@PathParam("id") int id, 
		@FormParam("name") String name, 
		@FormParam("tipe") String tipe,
		@FormParam("idPeople") int idPeople
	) {
		try {
			final Pet modifiedPet = new Pet(id, name, tipe, idPeople);
			this.dao.modify(modifiedPet);
			
			return Response.ok(modifiedPet).build();
		} catch (NullPointerException npe) {
			final String message = String.format("Invalid data for pet (name: %s, tipe: %s)", name, tipe);
			
			LOG.log(Level.FINE, message);
			
			return Response.status(Response.Status.BAD_REQUEST)
				.entity(message)
			.build();
		} catch (IllegalArgumentException iae) {
			LOG.log(Level.FINE, "Invalid pet id in modify method", iae);
			
			return Response.status(Response.Status.BAD_REQUEST)
				.entity(iae.getMessage())
			.build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error modifying a pet", e);
			
			return Response.serverError()
				.entity(e.getMessage())
			.build();
		}
	}

	
	@DELETE
	@Path("/{id}")
	public Response delete(
		@PathParam("id") int id
	) {
		try {
			this.dao.delete(id);
			
			return Response.ok(id).build();
		} catch (IllegalArgumentException iae) {
			LOG.log(Level.FINE, "Invalid pet id in delete method", iae);
			
			return Response.status(Response.Status.BAD_REQUEST)
				.entity(iae.getMessage())
			.build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error deleting a pet", e);
			
			return Response.serverError()
				.entity(e.getMessage())
			.build();
		}
	}
}
