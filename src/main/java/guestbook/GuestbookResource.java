package guestbook;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/guestbook")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GuestbookResource {

    @GET
    public List<GuestEntry> getEntries() {
        return GuestEntry.listAll();
    }

    @POST
    @Transactional
    public GuestEntry addEntry(GuestEntry entry) {
        entry.persist();
        return entry;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteEntry(@PathParam("id") Long id) {
        GuestEntry entry = GuestEntry.findById(id);
        if (entry != null) {
            entry.delete();
        }
    }
}
