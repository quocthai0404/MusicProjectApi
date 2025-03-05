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
    private static final String DROPBOX_ACCESS_TOKEN = "sl.u.AFkyQs4PYb3-9EMtxWC27NlJqtni7S3zNeZ9MbL83u-cxzPRAGXXbn3Ta5ttViv_ZyXZMEwPDhlpQ7uYT7cf1sN22kwmnmM9ZP5pNkjHjntzuavvlrzFMWVzHj_gVcpFYqrpiIa3JRXgIQ77oYrzdTFesL5LuCC9bF7WJMPYBTYA_D0cVKMPExw-T2nUdP-J0DBdUJ7dNiWhQD3FdiI4M60p29NppWgY4A2XySt2xof01OAwwOPpKkJYqCXBAqpZ3_xKAj0ircNj8FdcGXg2KdqbnbylKwR97J91r_kMz1ezH0wxbTJ-V6289zDThZLPPMRaFxiKqRa2xwTauk3eJEj_TKIopJsl44R0B5WigNH5ct6LyQa8qdKu-6N0V4arh4OcLv68OzyExDzmZ-SxnuxA8NQWjsargJmkI5s2q9V3hhUHJfQA02FI1LG4RGL59hyRqge9FICxeGSvH9lJPo17CC0IWpV0K4WjSg79AJ2zqDD408DGnOKcImw0jpHrXYXeWnuqatGysH-8-TfvTI9cjwiJ2plAMzGTu8eCZ_76Ni3Puk0khSYfM9RR8qLUZpG9GPOUW1qgNvL0si_VzsyzH91t14j9SR9TV8-mkHBfweJ2t8D42WUnkpBr2ViYHpaHqjwP57swLx52874LnKFbM6vgSRgPBNXStFP1FRYrm5nuWcEvgNkvxirRqEfcULExDVZSnzNLSpHRv0U7KOC5HXWDU4pLaxSDSEJEgJA3ltBoDI8hSEwVfw1hFXs_x5TPBBo4rHVyUimOCAcjAPz9LQggl_lsb2qCAFhgPw_4KSnFLzL4M0svYLu9c9FmLPAVbsTqy2aQffGbBJ86835d6VJqIo-BTKTA6dKbwAOOxqG-ZPPXbVRlLE98b0qR3zsbKlHQmVbdlDCMOpfEhfZjhkt944lAhPV0-lD5-OnhzvDUpdwCmuAeucQmwB658eISTRcYDIfA2U-To6G3QigkBvhHalA0tFbopoxdg2SgxoCQG9j_BmPfJo9uCNUmZtr5aoD2LqeGyvtS1OwePrb9LaocpeUuEeAX2JmFe-Lr3Jfu-Mr3W6Wo775kUG66APqmlIVB9oUxZOjEHbKFyUIVTVXKERhRQx58sjnCOTnb-A4Kg2L4tjBdgrJT4cSIFXpit7PN11s4RjUCFAGOtmcGs5ceia3GZpQItVB7GKd1p33CHmKewpjbZrMKwGNCmWYzVr1j2lkrZbVU7ilmrXNxFmtObo0mkuQlOcGrrFSToYZS0sLh2hOm38Ugu4TCB-em68Eb9Cd5ekb5RqPYkCSQRN78uwXUE-LQk_2W0nB8pD_-q6PbsTOTVocVwryEPkKyaU5xaZ52W1dFYIioKDIr_TxJPTJkhGIXBgxdYLxYpsy6IzUquf3KkmZnPQUPohpWKFwc-va0yWb_lbfo5poe";
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
