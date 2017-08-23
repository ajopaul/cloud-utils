package s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import util.SystemProperties;

/**
 * Created by ajopaul on 23/8/17.
 */
public class AWSS3Util {

	 private static AmazonS3 s3Client;

	 private static final String ACCESS_KEY_ID = SystemProperties.getPropValue("access_key_id");
	 private static final String SECRET_ACCESS_KEY = SystemProperties.getPropValue("secret_access_key");
	 private static final String BUCKET_NAME = SystemProperties.getPropValue("bucket_name");;
     static {
         s3Client = getClient();
     }
     public static AmazonS3 getClient() throws AmazonServiceException {
		 if (s3Client == null) {
			 BasicAWSCredentials awsCreds = new BasicAWSCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY);

			 s3Client = AmazonS3ClientBuilder.standard()
					 .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
					 .withRegion(Regions.AP_SOUTHEAST_2)
					 .build();
		 }
		 return s3Client;
	 }

	 public static String getUrl(String keyName) {
        String url = null;
        try {
                url = ((AmazonS3Client) s3Client).getResourceUrl(BUCKET_NAME, keyName);
        }catch(AmazonServiceException ase){
			ase.printStackTrace();
		}
        return url;
    }


}
