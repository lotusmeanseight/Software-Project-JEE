package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.ProductLine;
import de.ostfalia.gruppe5.business.boundary.ProductLineService;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {

    @Inject
    private ProductLineService productLineService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
//    	String productLine_id = request.getParameter("productLine");
    	String productLine_id = request.getParameter("id");

    	System.out.println("productLine_id: " + productLine_id);
        if (productLine_id == null) {
            response.sendError(notFoundError());
            return;
        }

        ProductLine productLine = productLineService.findById(productLine_id);
        
        if (productLine == null) {
            response.sendError(notFoundError());
            return;
        }
        
        System.out.println("Danach: " + productLine.getProductLine());

        //  response.reset(); //Hier koennte ein Fehler sein.
        byte[] image = productLine.getImage();
        
        //have to find out the content type associated with the image....
        response.setContentType("image/jpg");
        // response.setContentLength(image.length);
        response.getOutputStream().write(image);
//        response.flushBuffer();
//        response.getOutputStream().close();
        
        
    }

    private int notFoundError() {
        return HttpServletResponse.SC_NOT_FOUND;
    }


}
