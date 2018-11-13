package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.entity.ProductLine;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Transient;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named
public class ImageUploader implements Serializable {

    @Transient
    private Part imageFile;
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
        byte[] uploadImage = null;
        try {
            InputStream inputStream = imageFile.getInputStream();
            long length = imageFile.getSize();
            uploadImage = new byte[(int) length];
            int offset = 0;
            int current = 0;
            while (offset < uploadImage.length && (current = inputStream.read(uploadImage, offset, uploadImage.length - offset)) >= 0) {
                offset += current;
            }
            inputStream.close();
        } catch (IOException e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "error uploading file",
                    null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        setImage(uploadImage);
        ProductLine databaseLine = productLineService.find(productLine);
        databaseLine.setImage(getImage());
        productLineService.update(databaseLine);
    }

    public Part getImageFile() {
        return imageFile;
    }

    public void setImageFile(Part imageFile) {
        this.imageFile = imageFile;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
