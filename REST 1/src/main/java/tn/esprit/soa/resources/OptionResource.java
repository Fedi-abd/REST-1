package tn.esprit.soa.resources;

import tn.esprit.soa.entities.Option;
import tn.esprit.soa.metier.GestionDonnees;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("options")
public class OptionResource {

    // 1) Création d'une nouvelle option
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOption(Option option) {
        if (GestionDonnees.addOption(option)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 2) Récupération de la liste de toutes les options
    // 3) Récupération de la liste des options d'un domaine spécifique
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptions(@QueryParam("domaine") String domaine) {
        List<Option> options;
        if (domaine != null && !domaine.isEmpty()) {
            options = GestionDonnees.getOptionsByDomain(domaine);
        } else {
            options = GestionDonnees.getAllOptions();
        }
        return Response.status(Response.Status.OK).entity(options).build();
    }

    // 4) Suppression d'une option ayant un code spécifique
    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (GestionDonnees.deleteOption(code)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 5) Modification d'une option ayant un code spécifique
    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int code, Option option) {
        // Enforce the code from path
        if (option.getCodeOption() != code) {
            option.setCodeOption(code);
        }
        if (GestionDonnees.updateOption(option)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 6) Récupération d'une option ayant un code donné
    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptionByCode(@PathParam("code") int code) {
        Option option = GestionDonnees.getOptionByCode(code);
        if (option != null) {
            return Response.status(Response.Status.OK).entity(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
