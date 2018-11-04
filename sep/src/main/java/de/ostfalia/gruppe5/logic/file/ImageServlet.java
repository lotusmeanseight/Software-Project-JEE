package de.ostfalia.gruppe5.logic.file;

import de.ostfalia.gruppe5.models.ProductLine;
import de.ostfalia.gruppe5.services.ProductLineService;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {

    @Inject
    private ProductLineService productLineService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productLine_id = request.getParameter("productLine");

        if (productLine_id == null) {
            response.sendError(notFoundError());
            return;
        }

        ProductLine productLine = productLineService.findById(productLine_id);

        if (productLine == null) {
            response.sendError(notFoundError());
            return;
        }
        response.reset();
        byte[] image = productLine.getBLOB();
        //have to find out the content type associated with the image....
        response.setContentType("image/jpeg");
        response.setContentLength(image.length);

        response.getOutputStream().write(image);
    }

    private int notFoundError() {
        return HttpServletResponse.SC_NOT_FOUND;
    }


}
