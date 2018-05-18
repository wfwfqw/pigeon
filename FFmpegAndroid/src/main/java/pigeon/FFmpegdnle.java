package pigeon;

public interface FFmpegdnle extends ResponseHandler {


     void onSuccess(String message);


     void onProgress(String message);
 void onFailure(String message);

}
