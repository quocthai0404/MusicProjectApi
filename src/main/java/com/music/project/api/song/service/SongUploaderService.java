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
    private static final String DROPBOX_ACCESS_TOKEN = "sl.u.AFmUD-m6cLgNBf4k1RE2hz0iDmeEQy480hAGiKDsHB27th2-kQb-cQCZJ6N65fmhgmpL2XJ3nw8KygE1OUAK9yM29JleptQZ95-IriITeGgA3ZuwNieYuluQcoA1LleFDv-SgBDeNPlnxETYjSuHI5XAIjhfmEo73E1OXCSd1FiunknFRE9ZrbylAD6BafQFQJDnfzk56HKgphdvxHCm8-6oLC3uYKpD6OMExre2Dk_jZJyPiAAfDh0fCJ3IS4PLPDrd6hQSnKXZn7XRC4LXBhXAIM_3G0lZBh39KMmSGmYUBdATe5oeuspUUwTPiHqwIhpXvpUo3F7fsoMiNT1160FdQhXxLkVVbDfssHgUB43AUY7R8mmrt4gFMfiKk5YaKXZ2gLwD_X6njLE5VuOwvBOCTxsZOQeI-Zmx7VlW4EJIDDnIpwXqLOXwrWFIV71xpUu00MJuH9DEnPnK6Vpdbc0duFkgMKc-l5UWMVvbuZC39HN2HouaIsgfWDKH_CGIaRqql6NBfj_qad2jgGJ1Slqd2Zghrg2Q5vLtYhuWhoGJHVx1tuSxhyGZz4DZTnLyD3qYWlb3UvJsaSDo8eNMN_UJ9h-fzTPv6THzkkq6-BVi0BvRztOmW-oYAPtc24X1JuU9r4PNdCVZCsCH8WatoHzMBMeqZW4muxNq4L0Fjt9x1wjwZsBTlcJ9GeGMvxyrJyoFIG4vPLzM7GnTEZjF_lc55X-TBVf8oJwrIYAOz_OFYY5tP-iy1Wa2Pv8YKPt6NsDVmXS7Ug1rE5qEEsXZR3SrqZmyqcgZ7Ebf3u55qUvf3v8jWde6GhNmYj7kpjMtzCGH37z1DOlm16Weic_gzhz2HgskT8XbftVOS1AZ0sNq4ObTqyVDXq8OmS9fWw1865J5l6p1W8YrnYvxgI_76dTfbxvvqchw-nViLjzElD6TXPMU5AuZtVlpIlX1sJ6bK9_YZkLhGjehK_fY4ur2bJR9XkcP0L6rEiV9ttUtrLS4hL-Ux7tA_hHYILiaTdHw_JiqPnycronauUAWaqx2uOzaR_pF9SSNtxiXSl9eA4cHh00Qk7q6LtOuFP8IJ2-sAvdxhOqln-_UwehV0jvklWN5grpvbqAPBJrBni0s2Au9oLTqh6LmsS1-SpeleDfPyvWm6t-rTTkKRgArnrNTBYKXiDdyDNdXJD5p3RaR1pGz2MdWSAU0abPvWTec90F8qeQxyczABl1uYrYMoXGD9sn5hJ3xBHEnTDLJmFZBdxLGjoQrHA2BzVcbcyWb5urTz15nrFzdIXPsPKCr2GoQPmaQaOQZFjnU_yskobcunGGhIuQvhs8eAplm7xw25FOIk3aDV503tpGPJLQEeAskpguuTzeMW6oeTYODX9_-x0N1f979d7CuGSTKX0kmb90aCGIoslrowtcdrRXcadyrbnP4";
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
