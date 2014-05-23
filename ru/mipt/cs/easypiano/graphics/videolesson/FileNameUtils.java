package ru.mipt.cs.easypiano.graphics.videolesson;

/**
 * Created by Asus on 19.05.14.
 */
public class FileNameUtils {

    public static final char EXTENSION_SEPARATOR = '.';
    private static final char UNIX_SEPARATOR = '/';
    private static final char WINDOWS_SEPARATOR = '\\';

    public static String getExtension(final String filename) {
        if (filename == null) {
            return null;
        }
        final int index = indexOfExtension(filename);
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

    public static int indexOfExtension(final String filename) {
        if (filename == null) {
            return -1;
        }
        final int extensionPos = filename.lastIndexOf(EXTENSION_SEPARATOR);
        final int lastSeparator = indexOfLastSeparator(filename);
        return lastSeparator > extensionPos ? -1 : extensionPos;
    }

    public static int indexOfLastSeparator(final String filename) {
        if (filename == null) {
            return -1;
        }
        final int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
        final int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);
        return Math.max(lastUnixPos, lastWindowsPos);
    }
}
