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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;

/**
 * Root resource (exposed at "departments" path)
 */
@Path("/departments")
@Singleton
public class DepartmentsResource {
  private final Map<Integer, Department> db = new ConcurrentHashMap<>();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Department> getAll() {
    return db.values().stream().collect(Collectors.toCollection(() -> new LinkedList<Department>()));
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
    Department dep = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(in)), Department.class);
    if (db.containsKey(dep.getId())) {
      return Response.status(Status.CONFLICT).build();
    }

    int newId = db.size() + 1;
    dep.setId(newId);
    db.put(newId, dep);
    return Response.created(new URI(uriInfo.getPath() + "/" + dep.getId())).build();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Response update(final @Context HttpHeaders headers, final byte[] in) {
    Gson gson = new Gson();
    Department dep = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(in)), Department.class);
    if (!db.containsKey(dep.getId())) {
      Response.status(Status.NOT_FOUND);
      return Response.status(Status.NOT_FOUND).build();
    }

    db.put(dep.getId(), dep);
    return Response.accepted().build();
  }
}
