package com.test;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import com.test.kafka.ProducerSample;

/**
 * Root resource (exposed at "myresource" path)

     Read: GET http://localhost:8080/rest/myresource/{id}
     Read: GET http://localhost:8080/rest/myresource/search/{name}
     Update: PUT http://localhost:8080/rest/myresource/{id}
     http://localhost:8080/RESTApp/rest/myresource/  - execute getIt()
     http://localhost:8080/RESTApp/index.jsp main links file
     
     For purposes of the kafka test, RESTProducer streams the search string coming through 
     REST call into the topic urltest in findByName method

 */
@Path("myresource")
public class RESTProducer {

	private RESTDAO dao ;
	
    public RESTDAO getDao() {
		return dao;
	}

	public void setDao(RESTDAO dao) {
		this.dao = dao;
	}

	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Connection succeeded!";
    }

    @GET @Path("search/{query}")
    public String findByName(@PathParam("query") String query)
    {
    	ProducerSample ps = new ProducerSample("urltest",0);
    	ps.sendData("searchkey", query);
    	ps.closeProducer();
       	if ( null == dao) dao = new RESTDAO();
       	return "Sent search string: " + dao.findByName(query).getName();
 
    }

    @GET @Path("{id}")
    //@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String findById(@PathParam("id") String id)
    {
    	if ( null == dao) dao = new RESTDAO();
    	System.out.println("in findById()");
    	
    	return dao.findById(id).getName();
    }

    
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String create(RESTDAO inObj)
    {
    	if ( null == dao) dao = new RESTDAO();
    	return dao.create(inObj).getName();
    }
}
