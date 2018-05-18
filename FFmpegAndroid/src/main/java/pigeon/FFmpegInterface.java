package pigeon;



import java.util.Map;

import pigeon.exceptions.FFmpcmd;
import pigeon.exceptions.FFmpegudp;

@SuppressWarnings("unused")
interface FFmpegInterface {


    public void loadBinary(FFmpegdgle ffmpegLoadBinaryResponseHandler) throws FFmpegudp;


    public void execute(Map<String, String> environvenmentVars, String[] cmd, FFmpegdnle ffmpegExecuteResponseHandler) throws FFmpcmd;


    public void execute(String[] cmd, FFmpegdnle ffmpegExecuteResponseHandler) throws FFmpcmd;


    public String getDeviceFFmpegVersion() throws FFmpcmd;


    public String getLibraryFFmpegVersion();


    public boolean isFFmpegCommandRunning();


    public boolean killRunningProcesses();


    public void setTimeout(long timeout);

}
