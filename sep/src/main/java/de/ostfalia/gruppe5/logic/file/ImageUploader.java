package de.ostfalia.gruppe5.logic.file;

import de.ostfalia.gruppe5.models.ProductLine;
import de.ostfalia.gruppe5.services.ProductLineService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@ViewScoped
@Named
public class ImageUploader implements Serializable {

    private Part imageFile;
    private String fileContent;
    private final List<String> supportedFileTypes = new ArrayList<>();
    private static final int MAX_FILESIZE = 1024 * 500; //512 kilobytes
    private byte[] image;
    @Inject
    private ProductLineService productLineService;

    public ImageUploader() {
        supportedFileTypes.add("image/jpeg");
        supportedFileTypes.add("image/png");
        supportedFileTypes.add("image/gif");
        supportedFileTypes.add("image/jpg");
        supportedFileTypes.add("image/tif");
    }

    public void validateFile(FacesContext context, UIComponent component, Object val) {
        List<FacesMessage> messages = new ArrayList<>();
        Part file = (Part) val;

        if (file.getSize() > MAX_FILESIZE) {
            messages.add(new FacesMessage("File:Size -> larger than 512 kb"));
        }

        if (!supportedFileTypes.contains(file.getContentType())) {
            messages.add(new FacesMessage("File:Type -> image type not supported"));
        }

        if (!messages.isEmpty()) {
            throw new ValidatorException(messages);
        }
    }

    public void uploadFile(String productLine) {
        try {
            fileContent = new Scanner(imageFile.getInputStream()).useDelimiter("\\A").next();
        } catch (IOException e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "error uploading file",
                    null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        setImage(fileContent.getBytes());
        ProductLine databaseLine = productLineService.findById(productLine);
        databaseLine.setImage(getImage());
        productLineService.update(databaseLine);
    }

    public Part getImageFile() {
        return imageFile;
    }

    public void setImageFile(Part imageFile) {
        this.imageFile = imageFile;
    }

    public String getFileContent() {
        return fileContent;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
