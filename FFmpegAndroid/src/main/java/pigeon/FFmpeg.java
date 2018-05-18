package pigeon;

import android.content.Context;
import android.text.TextUtils;

import java.lang.reflect.Array;
import java.util.Map;

import pigeon.exceptions.FFmpcmd;
import pigeon.exceptions.FFmpegudp;

@SuppressWarnings("unused")
public class FFmpeg implements FFmpegInterface {

    private final Context context;
    private FFmpegask ffmpegExecuteAsyncTask;
    private FFmpegtext ffmpegLoadLibraryAsyncTask;

    private static final long MINIMUM_TIMEOUT = 10 * 1000;
    private long timeout = Long.MAX_VALUE;

    private static FFmpeg instance = null;

    private FFmpeg(Context context) {
        this.context = context.getApplicationContext();
        //Log.setDEBUG(Util.isDebug(this.context));
    }

    public static FFmpeg getInstance(Context context) {
        if (instance == null) {
            instance = new FFmpeg(context);
        }
        return instance;
    }

    @Override
    public void loadBinary(FFmpegdgle ffmpegLoadBinaryResponseHandler) throws FFmpegudp {
        String cpuArchNameFromAssets = null;
        switch (CpuArchHelper.getCpuArch()) {
            case x86:
                cpuArchNameFromAssets = "x86";
                break;
            case ARMv7:
                cpuArchNameFromAssets = "armeabi-v7a";
                break;
            case NONE:
                throw new FFmpegudp("Device not supported");
        }

        if (!TextUtils.isEmpty(cpuArchNameFromAssets)) {
            ffmpegLoadLibraryAsyncTask = new FFmpegtext(context, cpuArchNameFromAssets, ffmpegLoadBinaryResponseHandler);
            ffmpegLoadLibraryAsyncTask.execute();
        } else {
            throw new FFmpegudp("Device not supported");
        }
    }

    @Override
    public void execute(Map<String, String> environvenmentVars, String[] cmd, FFmpegdnle ffmpegExecuteResponseHandler) throws FFmpcmd {
        if (ffmpegExecuteAsyncTask != null && !ffmpegExecuteAsyncTask.isProcessCompleted()) {
            throw new FFmpcmd("FFmpeg command is already running, you are only allowed to run single command at a time");
        }
        if (cmd.length != 0) {
            String[] ffmpegBinary = new String[] { FileUtils.getFFmpeg(context, environvenmentVars) };
            String[] command = concatenate(ffmpegBinary, cmd);
            ffmpegExecuteAsyncTask = new FFmpegask(command , timeout, ffmpegExecuteResponseHandler);
            ffmpegExecuteAsyncTask.execute();
        } else {
            throw new IllegalArgumentException("shell command cannot be empty");
        }
    }

    public <T> T[] concatenate (T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    @Override
    public void execute(String[] cmd, FFmpegdnle ffmpegExecuteResponseHandler) throws FFmpcmd {
        execute(null, cmd, ffmpegExecuteResponseHandler);
    }

    @Override
    public String getDeviceFFmpegVersion() throws FFmpcmd {
        ShellCommand shellCommand = new ShellCommand();
        CommandResult commandResult = shellCommand.runWaitFor(new String[] { FileUtils.getFFmpeg(context), "-version" });
        if (commandResult.success) {
            return commandResult.output.split(" ")[2];
        }
        return "";
    }

    @Override
    public String getLibraryFFmpegVersion() {
        return context.getString(R.string.shipped_ffmpeg_version);
    }

    @Override
    public boolean isFFmpegCommandRunning() {
        return ffmpegExecuteAsyncTask != null && !ffmpegExecuteAsyncTask.isProcessCompleted();
    }

    @Override
    public boolean killRunningProcesses() {
        return Util.killAsync(ffmpegLoadLibraryAsyncTask) || Util.killAsync(ffmpegExecuteAsyncTask);
    }

    @Override
    public void setTimeout(long timeout) {
        if (timeout >= MINIMUM_TIMEOUT) {
            this.timeout = timeout;
        }
    }
}
