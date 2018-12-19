package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.ProductLine;

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

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		String productLineId = request.getParameter("id");
		ProductLine productLine = productLineService.find(productLineId);

		if (productLine == null) {
			try {
				response.sendError(notFoundError());
			} catch (IOException e) {
				e.getStackTrace();
			}
			return;
		}

		byte[] image = productLine.getImage();

		// have to find out the content type associated with the image....
		response.setContentType("image/jpg");
		try {
			response.getOutputStream().write(image);
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	private int notFoundError() {
		return HttpServletResponse.SC_NOT_FOUND;
	}

}
