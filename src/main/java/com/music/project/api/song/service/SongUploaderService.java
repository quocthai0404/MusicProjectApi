package com.music.project.api.song.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.GetTemporaryLinkErrorException;
import com.dropbox.core.v2.files.UploadErrorException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class SongUploaderService {
    private static final String DROPBOX_ACCESS_TOKEN = "sl.u.AFlj1_Esi6esuASGiAeiMZVI6DLNYQwbZ2lwtOds2FXQXbsnKbH4F2M1AVLlaTv4trokAT76wx_7gLUrq_Z2Ce1_Dor79wtQFGkmGtuGMhMBRzyFWMCt8U__vkE4ZmY0SY7luNH3OtXZy4BlUD-Bq8BDvrVojrB7ewb9xEi5678bWmT3dnTyYDvEb1sHA7KXE5w3evZf5Vu3T32WNprSzVuhZJTN_dG7NiQ6L6LeJIl5WbT9jecPkjYmtYuQzH36uhNEgLfL-Wska7zkYResYvVRXviftwEyUVMk7yREiDNbcp_NFxcSfHn8a_5E9kGW51shEFyc6sBFx1OBOg_ecmZETYfEu33J3c4onjIAGv0zxYiyebk4wVSwudKJrG2n1lH7bqW95EzXr0nYfjT-BRgkZyqtyVACgmqH_6WSbAIs-Xvn74ojnDn99K6ycV_w-zk-euYmEyQk5nnML3s2Kr2ZLdQ8FzYHinSpgKB0T5P2qRbM8Ed-_qC6xXsxXY7UzdF3913MUFqb9ohBdLZu88C6kjNapH1c2QJRGCTU-hNH0WH_OZ6yndQUUpdae41nGnyglqr8hd0KgWDFgCshbC_-eHCMcXkRVMbaGFuFnI2tKmwGYH-oxsfinRCxnjCjpP-lEZcHx_dBCULABjiBwa-tu4NGHcZHS-cQne_tSLlic-efvWK07AGM5cUWcI_IvOPRU6L2kckFvmNzhGCQZS_ulf11L8WAzkHepFG70EVqNvEsLKD8FEISDpYIXDeaZacZ7S5G-ORGgKuLOwy73BchUtvd6hmfvTYF0g5KdJnHTZTIVmzAxDpcQvsGxWhDKRG1vzPMCMzHPMSG8qtpQT8exlOtjkiL8AWdqflYvN6PGb4bfPKZTqDqfi2boIkS_tivXBAJAfwD-17TwWZ0mCBfKGDc5hmQIJZOQ495D7YsVeZ3OarlAkp_bUEvXel6NIfNOu-QYYAYGV51cLvacyGPu5BqI3QH6z_bVKyLb5rIPvYQMVJfygbZ4B6q4A10_gjCRra_m4Oaxauva4ZGXRubH9X7cD2Y3LhZVrtJR7Hi9BKGpzS8-OlQx_G5hJEKwO7NGuQPDUESByl1Q998y1lIS0QNCTB1R8_6qPA6pSqofUZ2B418llGFfO-45CZpt8Fh64nsjLEDtvXDxuHpOG4TO1lYC_KD9NCqqu9KXTrnVjVX86BmmscibRl9FSKU4s89yZTZUasvcndhcJfLGQNo0TW4u6jtGtJswzEUqYLrcvdwySns-DHG0H9yrr53lZ84ViwSxbnegeQ8G0rSll94rH7G9FwLJU2QvvZ2BkXy6fFx0s9mVfVI2TPR7vBh7HyF6483bD8KoopU1SblKS1nQxCdDjizog-gHq9FlGZ-_Jjz0ZPPRAHBn6ys5-Kwb8vot_0gcC7MATNJd0fc-A59";
    private final DbxClientV2 dropboxClient;

    public SongUploaderService() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        this.dropboxClient = new DbxClientV2(config, DROPBOX_ACCESS_TOKEN);

//        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
//                "cloud_name", CLOUDINARY_CLOUD_NAME,
//                "api_key", CLOUDINARY_API_KEY,
//                "api_secret", CLOUDINARY_API_SECRET
//        ));
    }

    public String uploadMp3ToDropbox(MultipartFile file) throws IOException {
        File tempFile = convertMultipartFileToFile(file);
        try (FileInputStream inputStream = new FileInputStream(tempFile)) {
            UUID uuid = UUID.randomUUID();
            String uuidAsString = uuid.toString();
            FileMetadata metadata = dropboxClient.files().uploadBuilder("/" + uuidAsString + ".mp3")
                    .uploadAndFinish(inputStream);
            return dropboxClient.files().getTemporaryLink(metadata.getPathLower()).getLink();
        } catch (GetTemporaryLinkErrorException e) {
            throw new RuntimeException(e);
        } catch (UploadErrorException e) {
            throw new RuntimeException(e);
        } catch (DbxException e) {
            throw new RuntimeException(e);
        } finally {
            tempFile.delete();
        }
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }
}
