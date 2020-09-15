import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;
import org.junit.Test;

public class qcloudVodTest {
    @Test
    public void test() {
        VodUploadClient client = new VodUploadClient("AKID3uWnAHGl7G9rMbCNsSpxsZ1D9pIYtIm0", "81gCxYzA6nEPN7bpnWrwlNnx50g6GAsM");
        VodUploadRequest request = new VodUploadRequest();
        request.setMediaFilePath("E:/11.wmv");
        try {
            VodUploadResponse response = client.upload("ap-chongqing", request);
            //logger.info("Upload FileId = {}", response.getFileId());
        } catch (Exception e) {
            // 业务方进行异常处理
            //logger.error("Upload Err", e);
        }
    }
}
