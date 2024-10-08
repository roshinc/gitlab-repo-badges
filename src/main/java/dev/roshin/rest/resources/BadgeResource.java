package dev.roshin.rest.resources;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/badge")
public class BadgeResource {

    @GET
    @Produces("image/svg+xml")
    public Response getBadge(@QueryParam("repo") String repoName) {
        if (repoName == null || repoName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Repository name is required")
                    .build();
        }

        // Fetch data from external REST API
//        EntityData entityData = fetchEntityData(repoName);
//        if (entityData == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("Entity data not found")
//                    .build();
//        }

        // Generate SVG badge
        String badgeSvg = generateBadgeSvg("Hello");

        // Return the badge SVG image with caching headers
        return Response.ok(badgeSvg)
                .header("Content-Type", "image/svg+xml")
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .build();
    }

    private String generateBadgeSvg(String entityName) {
        String svgTemplate = "<svg xmlns='http://www.w3.org/2000/svg' width='100' height='20'>"
                + "<rect width='100' height='20' fill='#555'/>"
                + "<text x='50' y='15' fill='#fff' text-anchor='middle' font-size='10'>%s</text>"
                + "</svg>";
        return String.format(svgTemplate, entityName);
    }
}
