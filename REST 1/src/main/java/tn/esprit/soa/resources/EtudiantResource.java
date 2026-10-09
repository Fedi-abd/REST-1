package tn.esprit.soa.resources;

import tn.esprit.soa.entities.Etudiant;
import tn.esprit.soa.metier.GestionDonnees;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("etudiants")
public class EtudiantResource {

    // 1) Création d'un nouvel étudiant
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEtudiant(Etudiant etudiant) {
        if (GestionDonnees.addEtudiant(etudiant)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build(); // 404 if option doesn't exist
    }

    // 2) Récupération de la liste de tous les étudiants
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiants() {
        List<Etudiant> etudiants = GestionDonnees.getAllEtudiants();
        return Response.status(Response.Status.OK).entity(etudiants).build();
    }

    // 3) Récupération d'un étudiant ayant un identifiant spécifique
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantById(@PathParam("id") String id) {
        Etudiant etudiant = GestionDonnees.getEtudiantById(id);
        if (etudiant != null) {
            return Response.status(Response.Status.OK).entity(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 4) Suppression d'un étudiant ayant un identifiant spécifique
    @DELETE
    @Path("{id}")
    public Response deleteEtudiant(@PathParam("id") String id) {
        if (GestionDonnees.deleteEtudiant(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 5) Modification d'un étudiant ayant un identifiant spécifique
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("id") String id, Etudiant etudiant) {
        // Enforce the id from path
        if (!id.equals(etudiant.getIdentifiant())) {
            etudiant.setIdentifiant(id);
        }
        if (GestionDonnees.updateEtudiant(etudiant)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 6) Récupération de la liste des étudiants inscrits à une option donnée
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOptionCode(@QueryParam("codeOption") int codeOption) {
        if (GestionDonnees.getOptionByCode(codeOption) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<Etudiant> etudiants = GestionDonnees.getEtudiantsByOptionCode(codeOption);
        GenericEntity<List<Etudiant>> entity = new GenericEntity<List<Etudiant>>(etudiants) {};
        return Response.status(Response.Status.OK).entity(entity).build();
    }
}
