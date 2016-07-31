package np.com.sagardevkota.lighthttp.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Created by HP on 7/11/2016.
 */
public class Response {
    private int code;
    private InputStream data;

    public int getCode() {
        return code;
    }

    public Response setCode(int code) {
        this.code = code;
        return this;
    }

    public InputStream getData() {
        return data;
    }

    public Response setData(InputStream data) {
        this.data = data;
        return this;
    }

    /**
     * Reads an InputStream and converts it to a String.
     * @return String
     * @throws IOException
     */
    public String getDataAsString() throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(data, "UTF-8");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        if (data != null) {
            data.close();
        }
        return out.toString();

    }

    /**
     * Converts input Stream to bitmap
     * @return Bitmap
     */
    public Bitmap getAsBitmap() {
        Bitmap bitmap = BitmapFactory.decodeStream(this.data);
        if (data != null) {
            try {
                data.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}
