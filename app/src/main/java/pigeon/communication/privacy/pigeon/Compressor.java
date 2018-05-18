package pigeon.communication.privacy.pigeon;

import android.app.Activity;

import pigeon.Executecin;
import pigeon.FFmpeg;
import pigeon.Load;
import pigeon.exceptions.FFmpcmd;
import pigeon.exceptions.FFmpegudp;



public class Compressor {

    public Activity a;
    public FFmpeg ffmpeg;
    public Compressor(Activity activity){
        a = activity;
        ffmpeg = FFmpeg.getInstance(a);
    }

    public void loadBinary(final InitListener mListener) {
        try {
            ffmpeg.loadBinary(new Load() {
                @Override
                public void onStart() {}

                @Override
                public void onFailure() {
                    mListener.onLoadFail("incompatible with this device");
                }

                @Override
                public void onSuccess() {
                    mListener.onLoadSuccess();
                }
                @Override
                public void onFinish() {

                }
            });
        } catch (FFmpegudp e) {
            e.printStackTrace();
        }
    }

    public void execCommand(String cmd,final CompressListener mListener){
        try {
            String[] cmds = cmd.split(" ");
            ffmpeg.execute(cmds, new Executecin() {

                @Override
                public void onStart() {}

                @Override
                public void onProgress(String message) { mListener.onExecProgress(message);}

                @Override
                public void onFailure(String message) { mListener.onExecFail(message); }

                @Override
                public void onSuccess(String message) {
                    mListener.onExecSuccess(message);
                }

                @Override
                public void onFinish() {}
            });
        } catch (FFmpcmd e) {
            e.printStackTrace();
        }
    }




}
