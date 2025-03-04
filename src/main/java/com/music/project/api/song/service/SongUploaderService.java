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

@Service
public class SongUploaderService {
    private static final String DROPBOX_ACCESS_TOKEN = "sl.u.AFnIv40ia7FQRuX_k3JDnGJ4g-2m0FJNdN_VNKepUWa8w6OZF5dtsYw19TXcLRP54Rho9FWMERtbBt58OLjv5ym-we_oetdHvwHS5zy298oi9npHzy5mDt3wACiNavUvOXbdpFc81Ez81xM5A84S022stUPYe6JdFu5wnMxS1OPqGaaicQ21PV_nnlh4rCL2m-Dc1leaFh1-G_R3WZWi8XWwL6d2g3t3hIIA382fURWtAxz6U7MrQ5gcyjXtxrziORgILJClXsX_z9XfDVfDeyUYcyoRBYs3NKEmpxJ6UI9yVu7qSyUNeUpRpxAzyPALJEvv_SNWnl8ryM5jVULXxy93a2co-VaWsG7qNxSkBHMidZ-OM1BE4nVT3sn4k9rXpE4b0YfpCKqpTF02LMxED96VfbC8ZMOdiPiHMwLlziNlhSTsBH27e6qkfeH5XtBltCZL1qic1CuhiHXUDJl3zcsPGOUJEZSsyk3Q4t3PKCeHeR_sP78F27gvB_Y_ySV8lhRRfykXTaT9ImYbJRwBZ4Mk2JJmmLos0JTj8HPi-OE-3fVIFC-6jmJQ3KHDDV_ZL5cEHtcRQPlCXfJQohCySV-vV-8H4YLCEXoX2Dx66phtGt1W7edf17q3hx5L4rs_7ifmOVzneoqTXA33wUamT1a3vlcX35_tPSS6OHcJ0BOrCyiSqwr-811NNuZKmNOKMkzXb4sZmL1xg1m0elc_rdZzUlaa07AGaYQRuiRDJfjOFLyf7pxkB1OyuWSA8YwwIrCk43qeItifsrb0Xwg1PVPL3y5AhpmvhcdPlytP3lnPuAyXbJ9X-NGWyLZg8EDIFZOTafoWs8vqTqcvOBsE1AAENGxdm8DCl9BdSQpT1u89vs-gOpS8OAvS4ChMV_LTFP0s3bL1ym0e1yCEGRnGiLRIkd6HjXS22RotnzKfxiO5lOEYLm1YqE7I93h9tNBM5O2nV2fCnOC7EceLB2DfPvZl7ODQcLOHOSqVFi6aNyCqf_ftFggsgwNnaWVT-P4ibnsj-vbSoWCyX724RiC9BMwAvRVWI03lu8NQt-xB1ScQ-GFONtSIaJUaC38b-SW5lz4O2zNZbxRIsigy53FPTPos24BEbiS8xZOnt0tY895lKne32Vxm-oBgJdy_DvcsptlBqU3_uMSGccBZY40uusn0ckcsxCDFoTQnOSJBhVwN7TQG4H955om8KIVx1hJnojv4cVhlruhfwF3-REvSNqekmgLyCXE5BhOwpQwMRPj-rD6K5OmBSfSdZME0UBlq2nW0JViS0wQTeZH49ze4283mIgXax61lRnqOlmO2OcE4UEBaPNuusl1nC-3YEiH-rfbkHmKtkRfyXg22OpqDW34ci09nPQz0aZ2ij_nr2MDieo3WsKuiXlOEOwpeVni-vHM";
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
            FileMetadata metadata = dropboxClient.files().uploadBuilder("/" + tempFile.getName())
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
