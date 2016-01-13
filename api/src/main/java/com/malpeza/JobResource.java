package com.malpeza;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

@Path("/Jobs")
@Singleton
public class JobResource {
	  private final Map<Integer, Job> db = new ConcurrentHashMap<>();

	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Job> getAll() {
	    return db.values().stream().collect(Collectors.toCollection(() -> new LinkedList<Job>()));
	  }

	  @GET
	  @Path("/{id}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response getById(final @PathParam("id") String id) {
	    int actualId = Integer.parseInt(id);
	    if (!db.containsKey(actualId)) {
	      return Response.status(Status.NOT_FOUND).build();
	    }

	    return Response.ok(db.get(actualId)).build();
	  }

	  @DELETE
	  @Path("/{id}")
	  public Response deleteById(final @PathParam("id") String id) {
	    int actualId = Integer.parseInt(id);
	    if (!db.containsKey(actualId)) {
	      return Response.status(Status.NOT_FOUND).build();
	    }

	    db.remove(actualId);
	    return Response.ok().build();
	  }

	  @POST
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response add(final @Context UriInfo uriInfo, final byte[] in) throws URISyntaxException {
	    Gson gson = new Gson();
	    Job jobPost = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(in)), Job.class);
	    if (db.containsKey(jobPost.getId())) {
	      return Response.status(Status.CONFLICT).build();
	    }

	    int newId = db.size() + 1;
	    jobPost.setId(newId);
	    db.put(newId, jobPost);
	    return Response.created(new URI(uriInfo.getPath() + "/" + jobPost.getId())).build();
	  }

	  @PUT
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response update(final @Context HttpHeaders headers, final byte[] in) {
	    Gson gson = new Gson();
	    Job jobPut = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(in)), Job.class);
	    if (!db.containsKey(jobPut.getId())) {
	      Response.status(Status.NOT_FOUND);
	      return Response.status(Status.NOT_FOUND).build();
	    }

	    db.put(jobPut.getId(), jobPut);
	    return Response.accepted().build();
	  }
}
