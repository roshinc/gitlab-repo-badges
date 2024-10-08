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

        // Prepare label and value
        String label = "Entity";
        String value = "hello";

        // Colors for the badge
        String labelColor = "#555"; // Grey
        String valueColor = "#4c1"; // Green

        // Generate the SVG badge
        String badgeSvg = generateBadgeSvg(label, value, labelColor, valueColor);

        // Return the badge SVG
        return Response.ok(badgeSvg)
                .header("Content-Type", "image/svg+xml;charset=utf-8")
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .build();
    }

    private String generateSingleBadgeSvg(String entityName) {
        String svgTemplate = "<svg xmlns='http://www.w3.org/2000/svg' width='100' height='20'>"
                + "<rect width='100' height='20' fill='#555'/>"
                + "<text x='50' y='15' fill='#fff' text-anchor='middle' font-size='10'>%s</text>"
                + "</svg>";
        return String.format(svgTemplate, entityName);
    }

    private String generateBadgeSvg(String label, String value, String labelColor, String valueColor) {
        // Calculate text widths
        int labelTextWidth = getTextWidth(label);
        int valueTextWidth = getTextWidth(value);

        // Calculate badge dimensions
        int labelWidth = labelTextWidth + 10; // Padding
        int valueWidth = valueTextWidth + 10; // Padding
        int totalWidth = labelWidth + valueWidth;

        // SVG template
        String svgTemplate = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%d\" height=\"20\" role=\"img\" aria-label=\"%s: %s\">"
                + "<title>%s: %s</title>"
                + "<linearGradient id=\"s\" x2=\"0\" y2=\"100%%\">"
                + "<stop offset=\"0\" stop-color=\"#bbb\" stop-opacity=\".1\"/>"
                + "<stop offset=\"1\" stop-opacity=\".1\"/>"
                + "</linearGradient>"
                + "<clipPath id=\"r\">"
                + "<rect width=\"%d\" height=\"20\" rx=\"3\" fill=\"#fff\"/>"
                + "</clipPath>"
                + "<g clip-path=\"url(#r)\">"
                + "<rect width=\"%d\" height=\"20\" fill=\"%s\"/>"
                + "<rect x=\"%d\" width=\"%d\" height=\"20\" fill=\"%s\"/>"
                + "<rect width=\"%d\" height=\"20\" fill=\"url(#s)\"/>"
                + "</g>"
                + "<g fill=\"#fff\" text-anchor=\"middle\" "
                + "font-family=\"Verdana,Geneva,DejaVu Sans,sans-serif\" "
                + "font-size=\"110\">"
                + "<text x=\"%d\" y=\"140\" transform=\"scale(.1)\" textLength=\"%d\">%s</text>"
                + "<text x=\"%d\" y=\"140\" transform=\"scale(.1)\" textLength=\"%d\">%s</text>"
                + "</g>"
                + "</svg>";

        // Positions for the text
        int labelTextX = labelWidth * 5;
        int valueTextX = labelWidth * 10 + valueWidth * 5;

        // Prepare SVG content
        String svgContent = String.format(svgTemplate,
                totalWidth, label, value, label, value, totalWidth,labelWidth,labelWidth,labelTextX,5,labelColor, labelWidth, valueWidth, 7, valueColor, totalWidth, labelTextX, labelTextWidth * 10);

        return svgContent;
    }

    private int getTextWidth(String text) {
        // Approximate width per character
        int averageCharWidth = 7;
        return text.length() * averageCharWidth;
    }

}
