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
    private static final String DROPBOX_ACCESS_TOKEN =
"sl.u.AFmunb3-4HJfw-jdx2ttLBe-cyRdUCr0TY7PSkbOdIOG81Ppk2mWTXsDWdbw5xRgRDNMAOrSNSUycte6_9qGTWL8IvFLlq4IpH4oj2szxsAVJ9EOi0hpbAd3o4hMoFsid6WpYQ3pwO6xXs5dfFCtQ4qbNkKpFLw9-5gn68irQBbMbQ_qqMaCj63eslJJtDMBJnBJqBB0ANeiA30AguxQ3Q6IJ48DCAgXJRg-5lYkH1cLcIk-EfuvoGcorTFvX1h6d6EQvG4-v80a30R7rTxxhd41QMlWz8dTqXGonSQFf__EO-Plr22hE_d7X--3AJ3p7dLgSW8D_qWhdiuzdAhGe0bSlekp3nz7ZGEMpUUE-FIZvda0y8bqXL4nc87CM0590-VrrbevhUfLZjA8FFE45tgxBXDVsGXDmYGSOZK-HyIJJ491N_yOMgDv5uc7XJBj7pzTFrFpFea3f6n6eefQgYR85WIDs0t8FIV-RUKNKJgosExyf9L16jLZIMtcO_-T--fFDkKAUAeVRgkx3bqcScfhZtBRBVtpA7iCNC1KPDigx_oXwX5RNLDAZmtUrQkem5FEw2m778gGrUupvXvGHCfM3zpqAYdkJp6JgCVfV89m8n9zcZbcmj2jWi2in4Nscz78XNikaf2u8PJ6-8y5DyUMQGc0rNJK5f0qDAOemZV5U0q2GSYQrhKchtBVvFoWv5ZaI_jL0lK5dsZQK94qeFeKPU0bckzP-07ky2CsEGcIBWvXapu0ba94_RIp83bVDTgxPTSK7ra7MNcILSX0lWvj2mZjBtJWeRDMJOPFlT-9er7UbcFLcoPIwPvyXUerR0CkCFnNnrruP3yoL1lpW9fLx83KVQaoYdy7eTM0qK5CJ6xs9m4BiSGc8T3KNqtAxuqHfQ-yj8IP0Wrjm-ZdC8k_sB1Q5KbxBe2XZflpeCwFAWwxcLA1x6yK3gxAVgxzIwL3mRldKJt1cYME2zHR3zMLtu9teHlBAfs3aIJrtJWeilhmZZsrCrpbZHGQMvzVLMKho1eF8o-c_C0zkSCbw9xQXlzRRfqQHYJe_PgSaj32LOf2GtS2cEhvqStsPBeAmCNIrjI9IhgayG5OXMBJmdO6cJbjWsDm6IlHkE4GE6mNvFuX52L-hiI7O9wfqJtW3Kwrhd6f3cYtLyPTEA_ydMScz4u8MZCyi18YxVGErUfKj_GG96lhIZh_cvUnWT6xsSrGsZKneIWzgLEwYes_GpRysjcyUAlYjXy0Rh0rZu7TtgPDQOhLBef3mBKzl4O0tB5Hi6cJ6ydwEvOXfBrme37PQfJjOrq_niqRV1DedQ24kegyCLAr__6oHisK9ujqSgtBgjuTjiHljLPNoHs94DekhBcB04hcWvpGQFIPhi9W0WJxokJ-N8gOpss4pk4T3HYM5XGtpm4QZJtSBWQXkqaG";
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
